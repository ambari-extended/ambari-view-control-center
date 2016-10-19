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

package org.apache.ambari.view.web.service;

import akka.actor.ActorRef;
import lombok.extern.slf4j.Slf4j;
import org.apache.ambari.view.internal.actor.repository.ScanManager;
import org.apache.ambari.view.web.model.dto.RepositoryWrapper;
import org.apache.ambari.view.web.model.entity.Registry;
import org.apache.ambari.view.web.model.repository.RegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.ambari.view.web.model.dto.RepositoryWrapper.ActionRequest.Actions;

/**
 *
 */
@Service
@Slf4j
public class RepositoryService {
  private final RegistryRepository registryRepository;
  private final ActorRef repositoryScanManager;

  @Autowired
  public RepositoryService(RegistryRepository registryRepository, ActorRef repositoryScanManager) {
    this.registryRepository = registryRepository;
    this.repositoryScanManager = repositoryScanManager;
  }

  public Registry create(RepositoryWrapper.CreateRequest request) {
    Registry registry = new Registry();
    RepositoryWrapper.CreateRequest.InnerRequest innerRequest = request.getRepository();
    registry.setName(innerRequest.getName());
    registry.setScanUrl(innerRequest.getScanUrl());

    return registryRepository.save(registry);
  }

  public List<Registry> findAll() {
    return registryRepository.findAll();
  }

  public void destroy(Long id) {
    registryRepository.delete(id);
  }

  public void start(Long id, RepositoryWrapper.ActionRequest request) {
    Registry registry = registryRepository.findOne(id);
    if(registry == null) {
      log.error("No registry found with id {}", id);
      //throw not found exception
      return;
    }

    // TODO
    if (request.getRepository().getAction() == Actions.START) {
      log.info("Starting sync for '{}' registry", registry.getName());
      repositoryScanManager.tell(new ScanManager.Start(registry.getName(), registry.getScanUrl()), ActorRef.noSender());
    } else {
      log.info("Stopping sync for '{}' registry", registry.getName());
      repositoryScanManager.tell(new ScanManager.Stop(registry.getName()), ActorRef.noSender());
    }
  }
}
