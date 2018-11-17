package com.danit.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
