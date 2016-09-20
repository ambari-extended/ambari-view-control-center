package org.apache.ambari.view;

import lombok.extern.slf4j.Slf4j;
import org.apache.ambari.view.web.model.entity.SystemSetting;
import org.apache.ambari.view.web.model.repository.SystemSettingRepository;
import org.apache.coyote.http2.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import javax.annotation.Priority;
import java.util.Optional;

@SpringBootApplication
@Slf4j
public class ControlCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlCenterApplication.class, args);
	}
}
