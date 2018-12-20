package com.danit.configuration;

import com.danit.dto.CardDto;
import com.danit.dto.ContractDto;
import com.danit.dto.ServiceDto;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.models.Service;
import com.danit.models.ServiceCategory;
import com.danit.utils.CustomDateDeserializer;
import com.danit.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        .addMapping(Contract::getActive, ContractDto::setActive)
        .addMapping(contract -> contract.getPaket().getId(), ContractDto::setPackageId)
        .addMapping(contract -> contract.getClient().getId(), ContractDto::setClientId);

    modelMapper.createTypeMap(Card.class, CardDto.class)
        .addMapping(card -> card.getContract().getId(), CardDto::setContractId);

//    Converter<Service, List<Long>> collectId =
//        ctx -> ctx.getSource().getServiceCategories()
//            .stream().map(ServiceCategory::getId).collect(Collectors.toList());
//
//
//    modelMapper.createTypeMap(Service.class, ServiceDto.class)
//        .addMappings(mapper -> mapper.using(collectId).map(Service::getServiceCategories, ServiceDto::setServiceCategoriesIds));
//    modelMapper.addMappings(new PropertyMap<Service, ServiceDto>() {
//      @Override
//      protected void configure() {
//        using(ctx -> (((Service) ctx.getSource()).getServiceCategories().stream().map(ServiceCategory::getId)
//                .collect(Collectors.toList()))).map().getServiceCategoriesIds();
//      }
//    });

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
