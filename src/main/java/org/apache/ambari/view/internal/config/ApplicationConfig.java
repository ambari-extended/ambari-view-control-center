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

package org.apache.ambari.view.internal.config;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for View Applications
 */
@Data
public class ApplicationConfig {
  /**
   * Should be unique across a registry
   */
  @NotNull
  private String name;

  /**
   * UI Label
   */
  @NotNull
  private String label;

  /**
   * Description of the application
   */
  private String description;

  /**
   * Version of the application
   */
  @NotNull
  private String version;

  @NotNull
  private String author;

  @NotNull
  private String organization;

  /**
   * Link where users can raise issues
   */
  private String issuesLink;

  /**
   * Code repository link
   */
  private String codeRepositoryLink;

  @NotNull
  private String license;

  @NotNull
  private String minAmbariVersion;

  @NotNull
  private String artifactLocation;

  /**
   * An application can depend on other applications for its working.
   */
  @Valid
  private List<ApplicationDependency> appDependencies = new ArrayList<>();

  /**
   * An application also requires services like HDFS, HIVE for its working.
   */
  @Valid
  private List<ServiceDependency> serviceDependencies = new ArrayList<>();


  @Valid
  private ApplicationParameterConfig parameterConfig;

}
