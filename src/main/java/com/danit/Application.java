package com.danit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.danit.repositories")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
