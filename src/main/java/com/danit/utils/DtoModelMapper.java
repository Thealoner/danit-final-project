package com.danit.utils;

import com.danit.dto.clientdto.ClientDto;
import com.danit.models.Client;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoModelMapper {

  @Autowired
  ModelMapper modelMapper;

  private ClientDto convertToDto(Client client) {
    return modelMapper.map(client, ClientDto.class);
  }
//
//  private List<ClientDto> convertToDtos(List<Client> clients) {
//    List<ClientDto> dtoClients = new ArrayList<ClientDto>();
//    clients.forEach(client -> {
//      dtoClients.add(modelMapper.map(client, ClientDto.class));
//    });
//    return dtoClients;
//  }
//
//  private Client convertToEntity(ClientDto clientDto) throws ParseException {
//    return modelMapper.map(clientDto, Client.class);
//  }

}
