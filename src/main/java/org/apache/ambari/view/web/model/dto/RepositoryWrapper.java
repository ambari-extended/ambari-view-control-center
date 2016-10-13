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

package org.apache.ambari.view.web.model.dto;

import lombok.Data;
import org.apache.ambari.view.internal.RegistryScanStatus;
import org.apache.ambari.view.web.model.entity.Registry;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class RepositoryWrapper {
  @Data
  public static class FindAllResponse {
    private List<RepositoryResponse> repositories;


    public FindAllResponse(List<Registry> registries) {
      repositories = registries.stream().map(RepositoryResponse::new).collect(Collectors.toList());
    }
  }

  @Data
  public static class CreateRequest {
    private InnerRequest repository;

    @Data
    public static class InnerRequest {
      private String name;
      private String scanUrl;
    }
  }

  @Data
  public static class CreateResponse {
    private RepositoryResponse repository;

    public CreateResponse(Registry registry) {
      repository = new RepositoryResponse(registry);

    }

  }

  @Data
  public static class RepositoryResponse {
    private Long id;
    private String name;
    private String scanUrl;
    private boolean isRunning;
    private String scanStatus;
    private Date lastScannedAt;


    public RepositoryResponse(Registry registry) {
      this.setId(registry.getId());
      this.setName(registry.getName());
      this.setScanUrl(registry.getScanUrl());
      this.setScanStatus(registry.getScanStatus().name());
      this.setLastScannedAt(registry.getLastScannedAt());
      this.setRunning(isRunning(registry.getScanStatus()));
    }

    private boolean isRunning(RegistryScanStatus scanStatus) {
      return scanStatus == RegistryScanStatus.RUNNING;
    }
  }
}
