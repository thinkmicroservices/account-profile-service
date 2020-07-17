package com.thinkmicroservices.ri.spring.account.profile;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author cwoodward
 */
@SpringBootApplication
@Slf4j
public class ProfileApplication {

    @Value("${configuration.source:DEFAULT}")
    String configSource;
    @Value("${spring.application.name:NOT-SET}")
    private String serviceName;

    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }

    /**
     * 
     */
    @PostConstruct
    private void displayInfo() {
        log.info("Service-Name:{}, configuration.source={}", serviceName, configSource);
    }
}
