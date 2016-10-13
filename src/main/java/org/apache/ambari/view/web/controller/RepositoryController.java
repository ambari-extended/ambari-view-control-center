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
import org.apache.ambari.view.web.model.dto.RepositoryWrapper;
import org.apache.ambari.view.web.model.entity.Registry;
import org.apache.ambari.view.web.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/repositories")
public class RepositoryController {

  private final RepositoryService repositoryService;

  @Autowired
  public RepositoryController(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }

  @GetMapping
  public RepositoryWrapper.FindAllResponse getAll() {
    List<Registry> registries = repositoryService.findAll();
    return new RepositoryWrapper.FindAllResponse(registries);
  }

  @PostMapping
  public RepositoryWrapper.CreateResponse create(@RequestBody RepositoryWrapper.CreateRequest request) {
    Registry registry = repositoryService.create(request);
    return new RepositoryWrapper.CreateResponse(registry);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity destroy() {
    return null;
  }

  @PostMapping("/{id}/start")
  public ResponseEntity start() {
    return null;
  }

  @PostMapping("/{id}/stop")
  public ResponseEntity stop() {
    return null;
  }
}

