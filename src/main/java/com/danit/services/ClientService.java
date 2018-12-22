package com.danit.services;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import com.danit.models.Paket;
import com.danit.repositories.ClientRepository;
import com.danit.repositories.specifications.ClientListSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends AbstractBaseEntityService<Client, ClientListRequestDto> {

  private ClientRepository clientRepository;
  private PaketService paketService;

  public ClientService(ClientRepository clientRepository, PaketService paketService) {
    this.clientRepository = clientRepository;
    this.paketService = paketService;
  }
//
//  public Page<Client> findAllClientsWithPaket(Long paketId, Pageable pageable) {
//    Paket paket = paketService.getEntityById(paketId);
//    return clientRepository.findAll(ClientListSpecification.getClientsByPaketSpec(paket), pageable);
//  }
}
