package com.danit.services;

import com.danit.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
  List<Client> getAllClients();

  Optional<Client> getClientById(long id);
}
