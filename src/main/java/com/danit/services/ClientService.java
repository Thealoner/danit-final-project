package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

  Page<Client> getAllClients(Pageable pageable);

  Page<Client> getAllClients(ClientListRequestDto clientListRequestDto, Pageable pageable);

  Client getClientById(long id);

  List<Client> saveClients(List<Client> clients);

  Client saveClient(Client client);

  List<Client> updateClients(List<Client> clients);

  void deleteClientById(long id);

  void deleteClients(List<Client> clients);

  int getNumberOfClients();
}
