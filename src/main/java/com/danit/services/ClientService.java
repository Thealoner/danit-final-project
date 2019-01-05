package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class ClientService extends AbstractBaseEntityService<Client, ClientListRequestDto> {

  private ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  @Transactional
  public void deleteEntityById(long id) {
    Client client = getEntityById(id);
    if(Objects.nonNull(client.getContracts())) {
      client.getContracts().forEach(contract -> contract.setClient(null));
    }
    clientRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteEntities(List<Client> entityList) {
    List<Client> clients = reloadEntities(entityList);
    clients.forEach(client -> {
      if(Objects.nonNull(client.getContracts())) {
        client.getContracts().forEach(contract -> contract.setClient(null));
      }
    });
    clientRepository.deleteAll(clients);
  }
}
