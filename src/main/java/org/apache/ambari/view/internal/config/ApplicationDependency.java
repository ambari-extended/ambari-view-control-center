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

import javax.validation.constraints.NotNull;

/**
 * Defines the application dependency
 */
@Data
public class ApplicationDependency {
  /**
   * Name of the dependent application
   */
  @NotNull
  private String name;

  /**
   * Minimum version required for this view
   */
  @NotNull
  private String minVersion;

  /**
   * If this is a hard dependency and the application will not be able to work
   * without this dependency
   */
  private boolean required = false;
}
