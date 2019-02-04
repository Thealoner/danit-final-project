package com.danit.configuration;

import com.danit.dto.CardDto;
import com.danit.dto.ContractDto;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.utils.deserializers.CustomDateDeserializer;
import com.danit.utils.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
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
        .setAmbiguityIgnored(true)
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);

    modelMapper.createTypeMap(Contract.class, ContractDto.class)
        .addMapping(contract -> contract.getPaket().getId(), ContractDto::setPackageId)
        .addMapping(contract -> contract.getClient().getId(), ContractDto::setClientId);

    modelMapper.createTypeMap(ContractDto.class, Contract.class)
        .addMapping(ContractDto::getPackageId, (contract, id) -> contract.getPaket().setId((Long) id))
        .addMapping(ContractDto::getClientId, (contract, id) -> contract.getClient().setId((Long) id));

    modelMapper.createTypeMap(Card.class, CardDto.class)
        .addMapping(card -> card.getContract().getId(), CardDto::setContractId);

    /*modelMapper.createTypeMap(UserDto.class, User.class).setConverter(getUserDtoConverter());*/

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

  /*@Bean
  public Converter<UserDto, User> getUserDtoConverter() {
    return context -> {
      UserDto userDto = context.getSource();
      User user = new User();

      Long[] rolesIncoming = userDto.getRolesIncoming();
      List<UserRole> userRoles = new ArrayList<>();

      for (Long roleIncoming : rolesIncoming) {
        UserRole role = new UserRole();
        role.setId(roleIncoming);
        userRoles.add(role);
      }
      user.setRoles(userRoles);
      user.setPassword(userDto.getPassword());
      user.setUsername(userDto.getUsername());
      user.setId(userDto.getId());
      user.setEmail(userDto.getEmail());
      return user;
    };
  }*/

}
