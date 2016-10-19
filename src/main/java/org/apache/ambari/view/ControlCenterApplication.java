package org.apache.ambari.view;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import lombok.extern.slf4j.Slf4j;
import org.apache.ambari.view.internal.actor.repository.ScanManager;
import org.apache.ambari.view.web.model.entity.SystemSetting;
import org.apache.ambari.view.web.model.repository.SystemSettingRepository;
import org.apache.ambari.view.web.service.PackageScannerService;
import org.apache.coyote.http2.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import javax.annotation.Priority;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class ControlCenterApplication {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(ControlCenterApplication.class);
    application.run(args);
  }


  @Bean
  public CommandLineRunner run(ActorRef repositoryScanManager, PackageScannerService packageScannerService) {
    return (args) -> {
      log.info("Initializing repository scan for packages");
      packageScannerService.initializeRepositoryScan(repositoryScanManager);
    };
  }

}
