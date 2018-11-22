package com.danit.configuration;

import com.danit.dto.CardColorDto;
import com.danit.dto.ContractDto;
import com.danit.models.CardColor;
import com.danit.models.Contract;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setAmbiguityIgnored(true);

    modelMapper.createTypeMap(Contract.class, ContractDto.class)
        .addMapping(Contract::getClientId, ContractDto::setClientId);

    modelMapper.createTypeMap(CardColor.class, CardColorDto.class)
        .addMapping(CardColor::getContractId, CardColorDto::setContractId);

    return modelMapper;
  }
}
