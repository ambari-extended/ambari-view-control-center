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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.ambari.view.web.model.dto.PackageWrapper;
import org.apache.ambari.view.web.model.entity.Package;
import org.apache.ambari.view.web.model.entity.PackageVersion;
import org.apache.ambari.view.web.service.PackageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@RestController
@RequestMapping("/api/v1/packages")
@Slf4j
public class PackageController {
  private final PackageService service;

  public PackageController(PackageService service) {
    this.service = service;
  }

  @GetMapping
  public PackageWrapper.SearchResponse getAll(@RequestParam("like") Optional<String> like) {
    if(!like.isPresent()) {
      log.error("Cannot search packages without a search string");
      throw new RuntimeException("No packages found");
    }

    String query = "%" + like.get() + "%";
    List<Package> packages = service.getPackagesLike(query);
    return new PackageWrapper.SearchResponse(packages);
  }

  @GetMapping("/{id}")
  public PackageWrapper.FindOneResponse getOne(@PathVariable("id") Long packageId) {
    Optional<Package> packageOptional = service.getPackage(packageId);
    if(!packageOptional.isPresent()) {
      log.error("Package id: {} not found", packageId);
      throw new RuntimeException("Package not found");
    }
    return new PackageWrapper.FindOneResponse(packageOptional.get());
  }

  @GetMapping("/versions/{versionId}")
  public PackageWrapper.FindOneVersionResponse getPackageVersion(@PathVariable("versionId") Long versionId) {
    Optional<PackageVersion> versionOptional = service.getVersion(versionId);
    if(!versionOptional.isPresent()) {
      log.error("Package version id: {} not found", versionId);
      throw new RuntimeException("Package Version not found");
    }
    return new PackageWrapper.FindOneVersionResponse(versionOptional.get());
  }


  @GetMapping("/versions/{versionId}/config")
  public PackageWrapper.ApplicationConfigResponse getApplicationConfig(@PathVariable("versionId") Long versionId) {
    Optional<JsonNode> applicationConfigOptional = service.getApplicationConfig(versionId);
    if(!applicationConfigOptional.isPresent()) {
      log.error("Configuration for package version id: {} not found", versionId);
      throw new RuntimeException("Package Version not found. Could not retrieve configuration.");
    }
    return new PackageWrapper.ApplicationConfigResponse(applicationConfigOptional.get());
  }
}
