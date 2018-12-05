package com.danit.configuration;

import com.danit.dto.CardColorDto;
import com.danit.dto.ContractDto;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class BeanConfiguration {

  @Autowired
  CustomDateSerializer customDateSerializer;

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
    module.addSerializer(Date.class, customDateSerializer);
    return module;
  }

  @Bean
  public SimpleDateFormat simpleDateFormat() {
    return new SimpleDateFormat();
  }

}
