package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.Card;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ContractService extends AbstractBaseEntityService<Contract, ContractListRequestDto> {

  private ClientService clientService;

  private PaketService paketService;

  private CardService cardService;

  private ContractRepository contractRepository;

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
  public void deAssignClientFromContract(Long contractId, Long clientId) {
    getEntityById(contractId)
        .setClient(null);
  }

  @Transactional
  public void assignPaketToContract(Long contractId, Long paketId) {
    getEntityById(contractId)
        .setPaket(paketService.getEntityById(paketId));
  }

  @Transactional
  public void deAssignPaketFromContract(Long contractId, Long paketId) {
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
  public void deAssignCardFromContract(Long contractId, Long cardId) {
    cardService.getEntityById(cardId)
        .setContract(null);
  }

  @Transactional
  public void deAssignCardsFromContract(Long contractId, List<Card> cards) {
    cardService.reloadEntities(cards).forEach(card -> card.setContract(null));
  }

  public Page<Contract> findAllContractsForClientId(Long clientId, Pageable pageable) {
    return contractRepository.findAllByClient_Id(clientId, pageable);
  }

  public Page<Contract> findAllContractsForPaketId(Long paketId, Pageable pageable) {
    return contractRepository.findAllByPaket_Id(paketId, pageable);
  }

  @Transactional
  public void createContractsForClient(Long clientId, List<Contract> contracts) {
    saveEntities(contracts);
    List<Contract> reloadedContracts = reloadEntities(contracts);
    reloadedContracts.forEach(contract -> assignClientToContract(contract.getId(), clientId));
    Client client = clientService.getEntityById(clientId);
    reloadedContracts.forEach(contract -> client.getContracts().add(contract));
  }

  @Override
  @Transactional
  public void deleteEntityById(long id) {
    Contract contract = super.getEntityById(id);
    //Delete relation with Card if exist
    if (Objects.nonNull(contract.getCards())) {
      deAssignCardsFromContract(id, contract.getCards());
    }
    //Delete relation with Client if exist
    if (Objects.nonNull(contract.getClient())) {
      contract.getClient().getContracts().remove(contract);
    }
    //Delete relation with Paket if exist
    if (Objects.nonNull(contract.getPaket())) {
      contract.getPaket().getContracts().remove(contract);
    }
    contractRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteEntities(List<Contract> entityList) {
    List<Contract> contracts = super.reloadEntities(entityList);
    entityList.forEach(e -> {
      if (!contracts.contains(e)) {
        throw new EntityNotFoundException(LOG_MSG1 + getEntityName() + LOG_MSG2 + e.getId());
      }
    });
    //Delete relation with Card if exist
    contracts.forEach(contract -> {
      if (Objects.nonNull(contract.getCards())) {
        deAssignCardsFromContract(contract.getId(), contract.getCards());
      }
    });
    contracts.forEach(contract -> {
      //Delete relation with Client if exist
      if (Objects.nonNull(contract.getClient())) {
        contract.getClient().getContracts().remove(contract);
      }
      //Delete relation with Paket if exist
      if (Objects.nonNull(contract.getPaket())) {
        contract.getPaket().getContracts().remove(contract);
      }
    });
    super.deleteEntities(contracts);

  }
}
