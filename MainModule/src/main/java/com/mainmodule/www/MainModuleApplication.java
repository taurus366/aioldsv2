package com.mainmodule.www;

import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@SpringBootApplication
@EnableScheduling
@EnableVaadin(value = {"com.profilemodule.www", "com.mainmodule.www", "com.aioldsweb.www"})
@ComponentScan(basePackages = {"com.profilemodule.www", "com.mainmodule.www", "com.aioldsweb.www"})
@EnableJpaRepositories(basePackages = {"com.profilemodule.www", "com.mainmodule.www", "com.aioldsweb.www"})
@EntityScan(basePackages = {"com.profilemodule.www", "com.mainmodule.www", "com.aioldsweb.www"})
@ConfigurationPropertiesScan(value = {"com.aioldsweb.www", "com.profilemodule.www", "com.mainmodule.www"})
public class MainModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainModuleApplication.class, args);
    }

}
