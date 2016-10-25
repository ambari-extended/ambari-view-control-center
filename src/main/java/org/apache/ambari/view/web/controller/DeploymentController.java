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

package org.apache.ambari.view.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ambari.view.web.model.dto.DeploymentWrapper;
import org.apache.ambari.view.web.model.entity.Deployment;
import org.apache.ambari.view.web.service.DeploymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/deployments")
public class DeploymentController {

  private final DeploymentService service;

  public DeploymentController(DeploymentService service) {
    this.service = service;
  }

  @GetMapping
  public DeploymentWrapper.GetAllResponse getAll() {
    List<Deployment> deployments = service.getAll();
    return new DeploymentWrapper.GetAllResponse(deployments);

  }
}
