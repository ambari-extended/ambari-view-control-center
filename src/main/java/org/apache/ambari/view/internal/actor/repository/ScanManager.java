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
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import lombok.Value;
import org.apache.ambari.view.web.service.PackageScannerService;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ScanManager extends AbstractLoggingActor {

  private final Map<String, ActorRef> workers = new HashMap<>();
  private final PackageScannerService packageScannerService;
  private final RestTemplate ws;

  public static Props props(PackageScannerService packageScannerService, RestTemplate ws) {
    return Props.create(ScanManager.class, packageScannerService, ws);
  }


  private ScanManager(PackageScannerService packageScannerService, RestTemplate ws) {
    this.packageScannerService = packageScannerService;
    this.ws = ws;
  }


  @Override
  public PartialFunction receive() {
    return ReceiveBuilder
        .match(Start.class, this::startHandler)
        .match(Stop.class, this::stopHandler)
        .matchAny(this::unhandledHandler).build();
  }

  private void startHandler(Start message) {
    ActorRef actor = getOrCreate(message.getName(), message.getUrl());
    actor.tell(new Scanner.Start(), self());
  }

  private void stopHandler(Stop message) {
    ActorRef actor = workers.get(message.getName());
    if(actor == null || actor.isTerminated()) {
      log().error("Repository scanning workers with the name '{}' is not running.", message.getName());
      return;
    }
    actor.tell(new Scanner.Stop(), self());
  }

  private void unhandledHandler(Object message) {

  }

  private ActorRef getOrCreate(String name, String url) {
    ActorRef ref =  workers.computeIfAbsent(name, key -> createScanner(key, url));
    if(ref.isTerminated()) {
      ref = createScanner(name, url);
      workers.put(name, ref);
    }
    return ref;
  }

  private ActorRef createScanner(String name, String url ) {
    return this.getContext().actorOf(Scanner.props(name, url, packageScannerService, ws), name);
  }

  @Override
  public SupervisorStrategy supervisorStrategy() {
    return new OneForOneStrategy(5, Duration.create(1, TimeUnit.SECONDS),
        DeciderBuilder.match(RestClientException.class, e -> SupervisorStrategy.restart()).build());
  }

  @Value
  public static class Start {
    private String name;
    private String url;
  }

  @Value
  public static class Stop {
    private String name;
  }
}
