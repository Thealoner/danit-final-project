package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Card;
import com.danit.models.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractService extends AbstractBaseEntityService<Contract, ContractListRequestDto> {

  private ClientService clientService;

  private PaketService paketService;

  private CardService cardService;

  @Autowired
  public ContractService(ClientService clientService, PaketService paketService,
                         CardService cardService) {
    this.clientService = clientService;
    this.paketService = paketService;
    this.cardService = cardService;
  }

  @Transactional
  public void assignClientToContract(Long contractId, Long clientId) {
    getEntityById(contractId)
        .setClient(clientService.getEntityById(clientId));
  }

  @Transactional
  public void deleteClientFromContract(Long contractId, Long clientId) {
    getEntityById(contractId)
        .setClient(null);
  }

  @Transactional
  public void assignPaketToContract(Long contractId, Long paketId) {
    getEntityById(contractId)
        .setPaket(paketService.getEntityById(paketId));
  }

  @Transactional
  public void deletePaketFromContract(Long contractId, Long paketId) {
    getEntityById(contractId)
        .setPaket(null);
  }

  @Transactional
  public void assignCardToContract(Long contractId, Long cardId) {
    cardService.getEntityById(cardId)
        .setContract(getEntityById(contractId));
  }

  @Transactional
  public void assignCardsToContract(Long contractId, List<Card> cards) {
    Contract contract = getEntityById(contractId);
    cardService.reloadEntities(cards).forEach(card -> card.setContract(contract));
  }

  @Transactional
  public void deleteCardFromContract(Long contractId, Long cardId) {
    cardService.getEntityById(cardId)
        .setContract(null);
  }

  @Transactional
  public void deleteCardsFromContract(Long contractId, List<Card> cards) {
    cardService.reloadEntities(cards).forEach(card -> card.setContract(null));
  }

}
