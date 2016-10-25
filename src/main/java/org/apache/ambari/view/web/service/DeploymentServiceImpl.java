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

import org.apache.ambari.view.web.model.entity.Deployment;
import org.apache.ambari.view.web.model.repository.DeploymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class DeploymentServiceImpl implements DeploymentService {
  private final DeploymentRepository repository;

  public DeploymentServiceImpl(DeploymentRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Deployment> getAll() {
    return repository.findAll();
  }
}
