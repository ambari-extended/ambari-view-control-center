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
import org.apache.ambari.view.web.model.dto.ws.HostWrapper;
import org.apache.ambari.view.web.model.entity.Host;
import org.apache.ambari.view.web.model.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 */
@Service
@Slf4j
public class HostServiceImpl implements HostService {
  private final HostRepository hostRepository;

  @Value("${remote.keyfile.location}")
  private String keyfileLocation = "/tmp/cc-keys";

  @Autowired
  public HostServiceImpl(HostRepository hostRepository) {
    this.hostRepository = hostRepository;
  }

  @Override
  public List<Host> findAll() {
    return hostRepository.findAll();
  }

  @Override
  public void destroy(Long id) {
    Host host = hostRepository.getOne(id);
    if(host != null) {
      deleteKeyFile(host.getKeyfileLocation());
      hostRepository.delete(id);
    }
  }

  @Override
  @Transactional
  public Host create(HostWrapper.CreateRequest request) {
    Host host = new Host();
    host.setHostName(request.getHost().getName());

    String path = writeKeyFile(request.getHost().getName(), request.getHost().getKeyfileContent());
    host.setKeyfileLocation(path);
    return hostRepository.save(host);
  }

  private String writeKeyFile(String name, String keyfileContent) {
    File newFile = new File(keyfileLocation + File.separator + name + ".key");
    try(BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
      writer.write(keyfileContent);
    } catch (IOException ex) {
      log.error("Failed to write the key file to {}", newFile.getAbsolutePath());
      throw new RuntimeException(ex);
    }
    return newFile.getAbsolutePath();
  }

  private boolean deleteKeyFile(String path) {
    File file = new File(path);
    if(file.exists()) {
      return file.delete();
    }
    log.error("Key file '{}' does not exists", path);
    return false;
  }
}
