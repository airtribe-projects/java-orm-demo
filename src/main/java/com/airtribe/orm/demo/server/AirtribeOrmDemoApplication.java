package com.airtribe.orm.demo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.airtribe.orm"})
@EnableJpaRepositories(basePackages = {"com.airtribe.orm"})
@EntityScan(basePackages = {"com.airtribe.orm"})
@EnableJpaAuditing
public class AirtribeOrmDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirtribeOrmDemoApplication.class, args);
    }
}
