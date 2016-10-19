/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.view.internal.actor.repository;

import akka.actor.AbstractLoggingActor;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.apache.ambari.view.web.model.dto.ws.ApplicationsWrapper;
import org.apache.ambari.view.web.service.PackageScannerService;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Scanner extends AbstractLoggingActor {
  private final String name;
  private final String url;
  private boolean stopped = false;

  private final RestTemplate ws;
  private final PackageScannerService packageService;
  private Cancellable currentScheduler;

  private Scanner(String name, String url, PackageScannerService packageScannerService, RestTemplate ws) {
    this.name = name;
    this.url = url;
    this.packageService = packageScannerService;
    this.ws = ws;

  }

  public static Props props(String name, String url, PackageScannerService packageScannerService, RestTemplate ws) {
    return Props.create(Scanner.class, name, url, packageScannerService, ws);
  }

  @Override
  public PartialFunction receive() {
    return ReceiveBuilder.match(Start.class, this::startHandler)
        .match(Stop.class, this::stopHandler)
        .match(Rescan.class, this::rescanHandler)
        .build();
  }

  private void startHandler(Start message) {
    log().info("Starting '{}' scanner for scanning of new packages.", name);
    this.stopped = false;
    scan();
  }

  private void stopHandler(Stop message) {
    log().info("Stopping '{}' scanner for scanning of new packages.", name);
    stopped = true;
    currentScheduler.cancel();
    packageService.scaningStopped(name);
  }

  private void rescanHandler(Rescan message) {
    if (!stopped)
      scan();
  }

  // TODO: Implement this.
  private void scan() {
    log().info("Scanning URL '{}' for new packages.", url);

    Optional<Date> lastScannedTs = packageService.getLastScannedTimestamp(name);
    Long lastScanned = lastScannedTs.map(Date::getTime).orElse(0L);

    URI uri = null;

    try {
      URIBuilder builder = new URIBuilder(url);
      builder.addParameter("after", String.valueOf(lastScanned));
      uri = builder.build();
    } catch (URISyntaxException e) {
      throw new RestClientException("URL Syntax error", e);
    }

    ApplicationsWrapper wrapper = ws.getForObject(uri, ApplicationsWrapper.class);
    packageService.updateApplications(wrapper.getApplications(), name);

    // Do Something
    scheduleRescanMessage();
  }

  private void scheduleRescanMessage() {
    currentScheduler = context().system().scheduler().scheduleOnce(Duration.create(120, TimeUnit.SECONDS),
        self(), new Rescan(), context().dispatcher(), self());
  }

  @Override
  public void postRestart(Throwable reason) throws Exception {
    log().info("Scanner '{}' restarted.", name);
    scheduleRescanMessage();
    super.postRestart(reason);
  }

  public static class Start {}

  public static class Stop {}

  private static class Rescan {}
}
