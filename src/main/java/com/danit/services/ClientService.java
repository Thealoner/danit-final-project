package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ClientService extends AbstractBaseEntityService<Client, ClientListRequestDto> {

  private ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  @Transactional
  public void deleteEntityById(long id) {
    Client client = super.getEntityById(id);
    if (Objects.nonNull(client.getContracts())) {
      client.getContracts().forEach(contract -> contract.setClient(null));
    }
    clientRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteEntities(List<Client> entityList) {
    List<Client> clients = super.reloadEntities(entityList);
    entityList.forEach(e -> {
      if (!clients.contains(e)) {
        throw new EntityNotFoundException(LOG_MSG1 + getEntityName() + LOG_MSG2 + e.getId());
      }
    });
    clients.forEach(client -> {
      if (Objects.nonNull(client.getContracts())) {
        client.getContracts().forEach(contract -> contract.setClient(null));
      }
    });
    super.deleteEntities(clients);
  }
}
