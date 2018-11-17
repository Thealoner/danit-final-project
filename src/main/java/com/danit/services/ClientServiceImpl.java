package com.danit.services;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.danit.utils.ServiceUtils.updateNonEqualFields;

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
  public List<Client> saveClients(List<Client> clients) {
    return clientRepository.saveAll(clients);
  }

  @Override
  public Client saveClient(Client client) {
    return clientRepository.save(client);
  }

  @Override
  public List<Client> updateClients(List<Client> clients) {
    List<Client> savedClients = new ArrayList<>();
    clients.forEach(sourceClient -> {
      Long id = sourceClient.getId();
      if (Objects.nonNull(id)) {
        Client targetClient = clientRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Cant find Client with id=" + id));
        if (updateNonEqualFields(sourceClient, targetClient)) {
          savedClients.add(clientRepository.save(targetClient));
        }
      } else {
        throw new EntityParticularDataException("id field is empty");
      }
    });
    return savedClients;
  }

  @Override
  public void deleteClientById(long id) {
    Client client = clientRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find client with id=" + id));
    clientRepository.delete(client);
  }

  @Override
  public void deleteClients(List<Client> clients) {
    Set<Long> clientsId = clientRepository.getAllClientsId();
    clients.forEach(client -> {
      if (!clientsId.contains(client.getId())) {
        throw new EntityNotFoundException("Client with id=" + client.getId() + " is not exist");
      }
    });
    clientRepository.deleteInBatch(clients);
  }

  @Override
  public int getNumberOfClients() {
    return clientRepository.getNumberOfClients();
  }
}
