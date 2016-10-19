-- Table creation DDLs
CREATE TABLE settings (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
  key_group                 VARCHAR(50) NOT NULL ,
  name                      VARCHAR(100) NOT NULL ,
  display_text              VARCHAR(512) ,
  help_text                 VARCHAR(2000) ,
  value                     VARCHAR(2000),
  type                      VARCHAR(50) NOT NULL,
  default_value             VARCHAR(2000),
  js_validation_script      VARCHAR(4000),
  groovy_validation_script  VARCHAR(4000),
  validation_error_message  VARCHAR(4000),
  required                  BOOLEAN,
  `read_only`               BOOLEAN,
  updated_at                DATE
);

CREATE TABLE registries (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
  name                      VARCHAR(100) NOT NULL ,
  scan_url                  VARCHAR(512) NOT NULL ,
  scan_status               VARCHAR(100) NOT NULL ,
  last_scanned_at           TIMESTAMP
);


CREATE TABLE packages (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
  name                      VARCHAR(50) NOT NULL ,
  registry_id               BIGINT NOT NULL ,
  CONSTRAINT fk_registry_id FOREIGN KEY (registry_id) REFERENCES registries(id)
);

CREATE TABLE package_versions (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  version                   VARCHAR(100),
  deployment_definition     TEXT,
  view_package_id           BIGINT,
  CONSTRAINT fk_view_package_id FOREIGN KEY (view_package_id) REFERENCES packages(id)
);

CREATE TABLE ambari_clusters (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  name                      VARCHAR(100),
  server_host               VARCHAR(255),
  server_port               INT,
  cluster                   VARCHAR(50),
  username                  VARCHAR(50),
  password                  VARCHAR(100),
  attached                  BOOLEAN
);

CREATE TABLE non_ambari_clusters (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  name                      VARCHAR(100)
);

CREATE TABLE services (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  name                      VARCHAR(100),
  cluster_id                BIGINT,
  CONSTRAINT fk_cluster_id FOREIGN KEY (cluster_id) REFERENCES non_ambari_clusters(id)
);

CREATE TABLE service_configurations (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  config_name               VARCHAR(256),
  type                      VARCHAR(50),
  value                     TEXT,
  service_id                BIGINT,
  CONSTRAINT fk_service_id FOREIGN KEY (service_id) REFERENCES services(id)
);

CREATE TABLE hosts (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  host_name                 VARCHAR(255),
  ipv4                      VARCHAR(100),
  ipv6                      VARCHAR(100)
);

CREATE TABLE deployments (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  deployment_name           VARCHAR(50),
  package_version_id        BIGINT,
  CONSTRAINT fk_package_Version_id FOREIGN KEY (package_version_id) REFERENCES packages(id)
);

CREATE TABLE nodes (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  port                      INT,
  status                    VARCHAR(50),
  deployment_id             BIGINT,
  host_id                   BIGINT,
  CONSTRAINT fk_deployment_id FOREIGN KEY (deployment_id) REFERENCES deployments(id),
  CONSTRAINT fk_host_id  FOREIGN KEY (host_id) REFERENCES hosts(id)
);

CREATE TABLE instances (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  name                      VARCHAR(100),
  display_name              VARCHAR(255),
  description               VARCHAR(512),
  visible                   BOOLEAN,
  deployment_id             BIGINT,
  ambari_cluster_id         BIGINT,
  non_ambari_cluster_id     BIGINT,
  CONSTRAINT fk_inst_deployment_id FOREIGN KEY (deployment_id) REFERENCES deployments(id),
  CONSTRAINT fk_ambari_cluster_id FOREIGN KEY (ambari_cluster_id) REFERENCES ambari_clusters(id),
  CONSTRAINT fk_non_ambari_cluster_id FOREIGN KEY (non_ambari_cluster_id) REFERENCES non_ambari_clusters(id)
);

CREATE TABLE instance_configs (
  id                        BIGINT AUTO_INCREMENT PRIMARY KEY ,
  name                      VARCHAR(255),
  value                     VARCHAR(512),
  instance_id               BIGINT,
  CONSTRAINT fk_instance_id FOREIGN KEY (instance_id) REFERENCES instances(id)
);




