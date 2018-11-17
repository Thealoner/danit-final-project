package com.danit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.danit.repositories")
@Slf4j
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
