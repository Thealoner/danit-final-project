package com.danit.services;

import com.danit.models.Client;

import java.util.List;

public interface ClientService {
  List<Client> getAllClients();

  Client getClientById(long id);
}
