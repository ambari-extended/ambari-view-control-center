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

import lombok.extern.slf4j.Slf4j;
import org.apache.ambari.view.internal.config.ApplicationConfig;
import org.apache.ambari.view.web.model.entity.Deployment;
import org.apache.ambari.view.web.model.entity.Package;
import org.apache.ambari.view.web.model.entity.PackageVersion;
import org.apache.ambari.view.web.model.repository.DeploymentRepository;
import org.apache.ambari.view.web.model.repository.PackageRepository;
import org.apache.ambari.view.web.model.repository.PackageVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
@Slf4j
public class PackageServiceImpl implements PackageService {
  private final PackageRepository packageRepository;
  private final PackageVersionRepository packageVersionRepository;
  private final DeploymentRepository deploymentRepository;

  @Autowired
  public PackageServiceImpl(PackageRepository packageRepository,
                            PackageVersionRepository packageVersionRepository,
                            DeploymentRepository deploymentRepository) {
    this.packageRepository = packageRepository;
    this.packageVersionRepository = packageVersionRepository;
    this.deploymentRepository = deploymentRepository;
  }

  @Override
  public List<Package> getPackagesLike(String like) {
    return packageRepository.findByNameLike(like);
  }

  @Override
  public Optional<Package> getPackage(Long id) {
    return Optional.ofNullable(packageRepository.findOne(id));
  }

  @Override
  public Optional<PackageVersion> getVersion(Long versionId) {
    return Optional.ofNullable(packageVersionRepository.findOne(versionId));
  }

  @Override
  public Optional<ApplicationConfig> getApplicationConfig(Long versionId) {
    PackageVersion version = packageVersionRepository.findOne(versionId);
    if (version == null) return Optional.empty();
    return Optional.ofNullable(version.getConfig());
  }

  @Override
  @Transactional
  public Deployment deployPackageVersion(Long versionId) {
    PackageVersion version = packageVersionRepository.findOne(versionId);
    if (version == null) {
      log.error("No package version found to deploy with version id: {}", versionId);
      throw new RuntimeException("No package version found for version id: " + versionId);
    }

    Deployment deployment = deploymentRepository.findByPackageVersion(version);

    if(deployment != null) {
      log.error("Deployment with the same package version with id: '{}' already exists", versionId);
      throw new RuntimeException("Deployment with the same package version with id: '" + versionId + "' already exists.");
    }
    deployment = new Deployment();
    deployment.setPackageVersion(version);
    deployment.setName(version.getConfig().getLabel() + " # " + version.getVersion() );
    return deploymentRepository.save(deployment);
  }
}