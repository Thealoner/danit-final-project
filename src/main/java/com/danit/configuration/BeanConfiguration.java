package com.danit.configuration;

import com.danit.dto.CardColorDto;
import com.danit.dto.ContractDto;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.utils.CustomDateDeserializer;
import com.danit.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Configuration
public class BeanConfiguration {

  @Value("${global.date.pattern}")
  private String datePattern;

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setAmbiguityIgnored(true);

    modelMapper.createTypeMap(Contract.class, ContractDto.class)
        .addMapping(Contract::getClientId, ContractDto::setClientId)
        .addMapping(Contract::isActive, ContractDto::setActive);

    modelMapper.createTypeMap(Card.class, CardColorDto.class)
        .addMapping(Card::getContractId, CardColorDto::setContractId);

    return modelMapper;
  }

  @Bean
  public SimpleModule dateModule() {
    SimpleModule module = new SimpleModule();
    SimpleDateFormat df = new SimpleDateFormat(datePattern);
    module.addSerializer(Date.class,
        new CustomDateSerializer(df));
    module.addDeserializer(Date.class, new CustomDateDeserializer());
    return module;
  }

  /*@Bean
  public SimpleDateFormat getDateFormat() {
    return new SimpleDateFormat(datePattern);
  }*/

}
