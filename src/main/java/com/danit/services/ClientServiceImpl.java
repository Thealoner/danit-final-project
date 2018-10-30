package com.danit.services;

import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

  private ClientRepository clientRepository;

  ClientServiceImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }


  @Override
  public Optional<Client> getClientById(long id) {
    return clientRepository.findById(id);
  }
}
