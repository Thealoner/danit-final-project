package com.danit.facades;

import com.danit.dto.ClientDto;
import com.danit.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientFacade {

  ClientDto convertToDto(Client client);

  Page<ClientDto> getAllClients(Pageable pageable);

  List<ClientDto> saveClients(List<Client> clients);

  ClientDto getClientById(Long id);

  List<ClientDto> updateClients(List<Client> clients);

  void deleteClientById(Long id);

  void deleteClients(List<Client> clients);

}
