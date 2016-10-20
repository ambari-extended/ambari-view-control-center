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

package org.apache.ambari.view.web.model.dto.ws;

import lombok.Data;
import lombok.Getter;
import org.apache.ambari.view.web.model.entity.Host;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class HostWrapper {
  @Getter
  public static class FindAllResponse {
    private List<HostResponse> hosts = new LinkedList<>();

    public FindAllResponse(List<Host> hosts) {
      this.hosts = hosts.stream().map(HostResponse::new).collect(Collectors.toList());
    }
  }

  @Data
  public static class CreateRequest {
    private InnerRequest host;

    @Data
    public static class InnerRequest {
      private String name;
      private String keyfileContent;
    }
  }


  @Data
  public static class CreateResponse {
    private HostResponse host;

    public CreateResponse(Host host) {
      this.host = new HostResponse(host);
    }
  }

  @Data
  public static class HostResponse {
    private Long id;
    private String name;
    private String keyfileLocation;
    public HostResponse(Host host) {
      this.id = host.getId();
      this.name = host.getHostName();
      this.keyfileLocation = host.getKeyfileLocation();
    }
  }
}
