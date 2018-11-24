package com.danit.services;

import com.danit.models.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

  Page<Client> getAllClients(int page, int size);

  Page<Client> getAllClients(String filter, int page, int size);

  Client getClientById(long id);

  List<Client> saveClients(List<Client> clients);

  Client saveClient(Client client);

  List<Client> updateClients(List<Client> clients);

  void deleteClientById(long id);

  void deleteClients(List<Client> clients);

  int getNumberOfClients();
}
