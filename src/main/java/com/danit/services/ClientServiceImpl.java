package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import com.danit.repositories.specifications.ClientListSpecification;
import com.danit.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ClientServiceImpl extends AbstractEntityService<Client, ClientListRequestDto> {

  private final ClientRepository clientRepository;

  private final ClientListSpecification clientListSpecification;

  @Autowired
  ClientServiceImpl(ClientRepository clientRepository, ClientListSpecification clientListSpecification) {
    this.clientRepository = clientRepository;
    this.clientListSpecification = clientListSpecification;
  }

  public Page<Client> getAllClients(ClientListRequestDto clientListRequestDto, Pageable pageable) {
    return clientRepository.findAll(clientListSpecification.getFilter(clientListRequestDto), pageable);
  }

  public Page<Client> getAllClients(Pageable pageable) {
    return clientRepository.findAll(pageable);
  }

  /*public Client getClientById(long id) {
    return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant find client with id=" + id));
  }*/

  public List<Client> saveClients(List<Client> clients) {
    return (List<Client>) clientRepository.saveAll(clients);
  }

  public Client saveClient(Client client) {
    return clientRepository.save(client);
  }

  public List<Client> updateClients(List<Client> clients) {
    List<Client> savedClients = new ArrayList<>();
    clients.forEach(sourceClient -> {
      Long id = sourceClient.getId();
      if (Objects.nonNull(id)) {
        Client targetClient = clientRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Cant find Client with id=" + id));
        if (ServiceUtils.updateNonEqualFields(sourceClient, targetClient)) {
          savedClients.add(clientRepository.save(targetClient));
        }
      } else {
        throw new EntityParticularDataException("id field is empty");
      }
    });
    return savedClients;
  }

  public void deleteClientById(long id) {
    Client client = clientRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find client with id=" + id));
    clientRepository.delete(client);
  }

  public void deleteClients(List<Client> clients) {
    Set<Long> clientsId = clientRepository.getAllClientsId();
    clients.forEach(client -> {
      if (!clientsId.contains(client.getId())) {
        throw new EntityNotFoundException("Client with id=" + client.getId() + " is not exist");
      }
    });
    clientRepository.deleteAll(clients);
  }

  public int getNumberOfClients() {
    return clientRepository.getNumberOfClients();
  }
}
