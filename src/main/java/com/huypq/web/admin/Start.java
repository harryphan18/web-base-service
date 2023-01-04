package com.huypq.web.admin;

import com.huypq.web.admin.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class Start {
    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        logger.info("============= START SPRING APP ==============");
        SpringApplication app = new SpringApplication(Start.class);
        app.run(args);
    }
}
