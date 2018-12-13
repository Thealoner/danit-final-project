package com.danit.configuration;

import com.danit.dto.CardDto;
import com.danit.dto.ContractDto;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.utils.CustomDateDeserializer;
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

  @Autowired
  CustomDateDeserializer customDateDeserializer;

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setAmbiguityIgnored(true);

    modelMapper.createTypeMap(Contract.class, ContractDto.class)
        .addMapping(Contract::isActive, ContractDto::setActive)
        .addMapping(contract -> contract.getPaket().getId(), ContractDto::setPackageId)
        .addMapping(contract -> contract.getClient().getId(), ContractDto::setClientId);

    modelMapper.createTypeMap(Card.class, CardDto.class);

    return modelMapper;
  }

  @Bean
  public SimpleModule dateModule() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(Date.class, customDateSerializer);
    module.addDeserializer(Date.class, customDateDeserializer);
    return module;
  }

  @Bean
  public SimpleDateFormat simpleDateFormat() {
    return new SimpleDateFormat();
  }

}
