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

package org.apache.ambari.view.web.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;

/**
 *
 */
@Data
@Entity
@Table(name = "instances")
public class Instance {
  @Id
  @GeneratedValue
  private Long id;

  private String name;
  private String displayName;
  private String description;

  private boolean visible;

  @ManyToOne
  private Deployment deployment;

  @OneToOne
  private AmbariCluster ambariCluster;

  @OneToOne
  private NonAmbariCluster nonAmbariCluster;

  @OneToMany(mappedBy = "instance")
  private Collection<InstanceConfig> configs;
}
