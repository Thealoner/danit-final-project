package ua.com.danit.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.danit.dto.CardColorDto;
import ua.com.danit.dto.ContractDto;
import ua.com.danit.models.Card;
import ua.com.danit.models.Contract;

@Configuration
public class BeanConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setAmbiguityIgnored(true);

    modelMapper.createTypeMap(Contract.class, ContractDto.class)
        .addMapping(Contract::getClientId, ContractDto::setClientId);

    modelMapper.createTypeMap(Card.class, CardColorDto.class)
        .addMapping(Card::getContractId, CardColorDto::setContractId);

    return modelMapper;
  }
}
