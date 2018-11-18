package com.danit.facades;

import com.danit.dto.ClientDto;
import com.danit.models.Client;
import com.danit.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientFacadeImpl implements ClientFacade{
  @Autowired
  ClientService clientService;

  @Autowired
  ModelMapper modelMapper;


  @Override
  public ClientDto convertToDto(Client client) {
    return modelMapper.map(client, ClientDto.class);
  }

  @Override
  public List<ClientDto> convertToDtos(List<Client> clients) {
    List<ClientDto> dtoClients = new ArrayList<>();
    clients.forEach(client ->
        dtoClients.add(modelMapper.map(client, ClientDto.class)));
    return dtoClients;
  }

  @Override
  public List<ClientDto> getAllClients() {
    return convertToDtos(clientService.getAllClients());
  }

  @Override
  public List<ClientDto> saveClients(List<Client> clients) {
    return convertToDtos(clientService.saveClients(clients));
  }


}
