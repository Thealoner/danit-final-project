package com.danit.facades;

import com.danit.dto.ClientDto;
import com.danit.models.Client;

import java.util.List;

public interface ClientFacade {

  ClientDto convertToDto(Client client);

  List<ClientDto> convertToDtos (List<Client> clients);

  List<ClientDto> getAllClients();

  List<ClientDto> saveClients(List<Client> clients);

  ClientDto getClientById(Long id);

  List<ClientDto> updateClients(List<Client> clients);

  void deleteClientById(Long id);

  void deleteClients(List<Client> clients);

}
