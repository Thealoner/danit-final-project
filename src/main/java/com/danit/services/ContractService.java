package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.repositories.ContractRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class ContractService extends AbstractBaseEntityService<Contract, ContractListRequestDto> {

  private ClientService clientService;

  private PaketService paketService;

  private CardService cardService;

  private ContractRepository contractRepository;

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Autowired
  public ContractService(ClientService clientService, PaketService paketService,
                         CardService cardService, ContractRepository contractRepository) {
    this.clientService = clientService;
    this.paketService = paketService;
    this.cardService = cardService;
    this.contractRepository = contractRepository;
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
    Contract contract = getEntityById(contractId);
    Card card = cardService.getEntityById(cardId);
    contract.getCards().add(card);
    card.setContract(contract);
  }

  @Transactional
  public void assignCardsToContract(Long contractId, List<Card> cards) {
    Contract contract = getEntityById(contractId);
    List<Card> reloadedCards = cardService.reloadEntities(cards);
    reloadedCards.forEach(card -> card.setContract(contract));
    contract.getCards().addAll(reloadedCards);
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

  public Page<Contract> findAllContractsForClientId(Long clientId, Pageable pageable) {
    return contractRepository.findAllContractsForClientId(clientId, pageable);
  }

  public Page<Contract> findAllContractsForPaketId(Long paketId, Pageable pageable) {
    return contractRepository.findAllContractsForPaketId(paketId, pageable);
  }

}
