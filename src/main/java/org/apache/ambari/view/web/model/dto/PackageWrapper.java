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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.ambari.view.internal.config.ApplicationConfig;
import org.apache.ambari.view.web.model.entity.Deployment;
import org.apache.ambari.view.web.model.entity.Package;
import org.apache.ambari.view.web.model.entity.PackageVersion;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PackageWrapper {
  @Data
  public static class SearchResponse {
    private List<Package> packages = new ArrayList<>();
    public SearchResponse(List<Package> packages) {
      this.packages = packages;
    }
  }

  @Data
  public static class FindOneResponse {

    @JsonProperty("package")
    private Package asPackage;

    public FindOneResponse(Package aPackage) {
      this.asPackage = aPackage;
    }
  }


  @Data
  public static class FindOneVersionResponse {
    private PackageVersion version;
    public FindOneVersionResponse(PackageVersion packageVersion) {
      this.version = packageVersion;
    }
  }

  @Data
  public static class ApplicationConfigResponse {
    private ApplicationConfig config;
    public ApplicationConfigResponse(ApplicationConfig config) {
      this.config = config;
    }
  }

  @Data
  public static class VersionDeployResponse {
    private Deployment deployment;
    public VersionDeployResponse(Deployment deployment) {
      this.deployment = deployment;
    }
  }

}
