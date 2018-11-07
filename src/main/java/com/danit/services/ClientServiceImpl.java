package com.danit.services;

import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

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

  @Override
  public void saveClients(List<Client> clients) {
    clientRepository.saveAll(clients);
  }

  @Override
  public void updateClients(List<Client> clients) {
    Set<Long> clientsId = clientRepository.getAllClientsId();
    clients.forEach(client -> {
      if(!clientsId.contains(client.getId())) {
        throw new EntityNotFoundException("Client with id=" + client.getId() + " is not exist");
      }
    });
    clientRepository.saveAll(clients);
  }

  @Override
  public void deleteClientById(long id) {
    clientRepository.deleteById(id);
  }

  @Override
  public void saveAllClients(List<Client> clients) {
    clientRepository.saveAll(clients);
  }
}
