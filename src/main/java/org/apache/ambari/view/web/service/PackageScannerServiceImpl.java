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
import org.apache.ambari.view.internal.RegistryScanStatus;
import org.apache.ambari.view.internal.actor.repository.ScanManager;
import org.apache.ambari.view.web.model.dto.ws.Application;
import org.apache.ambari.view.web.model.entity.Package;
import org.apache.ambari.view.web.model.entity.PackageVersion;
import org.apache.ambari.view.web.model.entity.Registry;
import org.apache.ambari.view.web.model.repository.PackageRepository;
import org.apache.ambari.view.web.model.repository.PackageVersionRepository;
import org.apache.ambari.view.web.model.repository.RegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
public class PackageScannerServiceImpl implements PackageScannerService {

  private final RegistryRepository registryRepository;
  private final PackageRepository packageRepository;
  private final PackageVersionRepository versionRepository;

  @Autowired
  public PackageScannerServiceImpl(RegistryRepository registryRepository, PackageRepository packageRepository, PackageVersionRepository versionRepository) {
    this.registryRepository = registryRepository;
    this.packageRepository = packageRepository;
    this.versionRepository = versionRepository;
  }

  @Override
  public Optional<Date> getLastScannedTimestamp(String repoName) {
    Optional<Registry> registryOptional = registryRepository.findByName(repoName);
    return registryOptional.map(Registry::getLastScannedAt);
  }


  @Override
  @Transactional
  public void updateApplications(List<Application> applications, String repo) {
    Optional<Registry> registryOptional = registryRepository.findByName(repo);
    if (!registryOptional.isPresent()) {
      log.error("Cannot find registry name '{}', unable to update applications ", repo);
      return;
    }

    Registry registry = registryOptional.get();
    registry.setLastScannedAt(new Date());
    registry.setScanStatus(RegistryScanStatus.RUNNING);
    registryRepository.save(registry);
    applications.forEach(app -> this.updateApplication(app, registry));

  }

  @Override
  @Transactional
  public void scaningStopped(String repoName) {
    updateScanningStatus(repoName, RegistryScanStatus.STOPPED);
  }

  @Override
  @Transactional
  public void scaningError(String repoName) {
    updateScanningStatus(repoName, RegistryScanStatus.ERROR);
  }

  @Override
  public void initializeRepositoryScan(ActorRef repositoryScanManager) {
    List<Registry> registryList = registryRepository.findAll();
    registryList.forEach(registry -> {
      if (registry.getScanStatus() == RegistryScanStatus.RUNNING) {
        repositoryScanManager.tell(new ScanManager.Start(registry.getName(), registry.getScanUrl()), ActorRef.noSender());
      }
    });
  }

  private void updateScanningStatus(String repoName, RegistryScanStatus status) {
    Optional<Registry> registryOptional = registryRepository.findByName(repoName);
    if (!registryOptional.isPresent()) {
      log.error("Cannot find registry name '{}', unable to mark it as {}", repoName, status);
      return;
    }

    Registry registry = registryOptional.get();
    registry.setScanStatus(status);
    registryRepository.save(registry);
  }

  private void updateApplication(Application application, Registry registry) {
    Optional<Package> packageOptional = packageRepository.findByName(application.getName());


    Package pack = packageOptional.orElseGet(() -> {
      Package pac = new Package();
      pac.setName(application.getName());
      pac.setRegistry(registry);
      packageRepository.save(pac);
      return pac;
    });

    Map<String, Application.Version> versionMap = application.getVersions().stream()
        .collect(Collectors.toMap(Application.Version::getVersion, Function.identity()));

    Set<String> versionsFromRepo = pack.getVersions().stream().map(PackageVersion::getVersion).collect(Collectors.toSet());
    versionMap.keySet().forEach(x -> {
      if (!versionsFromRepo.contains(x)) {
        PackageVersion packageVersion = new PackageVersion();
        Application.Version version = versionMap.get(x);
        packageVersion.setVersion(version.getVersion());
        packageVersion.setDeploymentDefinition(version.getApplicationConfig());
        packageVersion.setViewPackage(pack);
        pack.getVersions().add(packageVersion);
        versionRepository.save(packageVersion);
      }
    });


  }
}
