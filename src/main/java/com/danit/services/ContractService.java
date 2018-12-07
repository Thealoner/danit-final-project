package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Card;
import com.danit.models.Contract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ContractService extends AbstractEntityService<Contract, ContractListRequestDto> {
  @Override
  @Transactional
  public void deleteEntities(List<Contract> entityList) {
    List<Contract> contracts = reloadEntities(entityList);
    List<Card> cards = contracts.get(0).getCards();
    cards.forEach(card -> card.setContractId(null));
    contracts.remove(0);
  }
}
