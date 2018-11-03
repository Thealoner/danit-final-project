package com.danit.services;

import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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
  public Client getClientById(long id) {
    return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant find client with id=" + id));
  }
}
