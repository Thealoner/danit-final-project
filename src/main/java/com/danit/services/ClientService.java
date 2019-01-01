package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends AbstractBaseEntityService<Client, ClientListRequestDto> {

  private ClientRepository clientRepository;
  private PaketService paketService;

  public ClientService(ClientRepository clientRepository, PaketService paketService) {
    this.clientRepository = clientRepository;
    this.paketService = paketService;
  }
}
