package com.danit.facades;

import com.danit.dto.ClientDto;
import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import com.danit.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ClientFacade extends AbstractDtoFacade<ClientDto, Client, ClientListRequestDto> {

  private ClientService clientService;

  @Autowired
  public ClientFacade(ClientService clientService) {
    this.clientService = clientService;
  }

//  public Page<ClientDto> findAllClientsWithPaket(Long paketId, Pageable pageable) {
//    return convertToDtos(clientService.findAllClientsWithPaket(paketId, pageable));
//  }
}
