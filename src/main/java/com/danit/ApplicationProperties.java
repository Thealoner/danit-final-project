package com.danit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

  private String auth;

  private long authTokenExpTime;

  private String authTokenPrefix;

  private String authHeaderName;

  private String secretKey;

}
