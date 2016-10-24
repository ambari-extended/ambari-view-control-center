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
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Data
public class ApplicationParameterConfig {

  /**
   * If the application allow configuration of custom properties;
   */
  private boolean allowCustom = true;

  /**
   * If the application allows manual override of the parameters;
   */
  private boolean allowInstanceOverride = false;

  /**
   * Parameters required for the application to work.
   */
  private List<ApplicationParameter> parameters = new ArrayList<>();

  @Data
  public static class ApplicationParameter {
    /**
     * Name or key for the parameter
     */
    @NotNull
    private String name;

    /**
     * Description or Help to be shown to the user.
     */
    private String description;

    /**
     * Label to be shown in UI
     */
    @NotNull
    private String label;

    /**
     * Placeholder text in the UI
     */
    private String placeHolder;

    /**
     * Default value if not configured.
     */
    private String defaultValue;

    /**
     * If this configuration is required.
     */
    private boolean required = true;

    /**
     * Cluster config. format: '<site-file>/<key>'
     */
    private String clusterConfig;

    /**
     * If the value is persisted as masked
     */
    private boolean masked = false;

    public boolean isClusterConfig() {
      return !StringUtils.isEmpty(clusterConfig);
    }
  }
}
