package com.danit.services;

import com.danit.models.Client;

import java.util.List;

public interface ClientService {

  List<Client> getAllClients();

  Client getClientById(long id);

  List<Client> saveClients(List<Client> clients);

  void updateClients(List<Client> clients);

  void deleteClientById(long id);

  void deleteClients(List<Client> clients);

  int getNumberOfClients();
}
