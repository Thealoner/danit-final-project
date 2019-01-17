package com.danit.services;

import com.danit.dto.service.CardListRequestDto;
import com.danit.models.Card;
import com.danit.repositories.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CardService extends AbstractBaseEntityService<Card, CardListRequestDto> {

  private CardRepository cardRepository;

  public CardService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  public Page<Card> findAllContractsForClientId(Long contractId, Pageable pageable) {
    return cardRepository.findAllByContract_Id(contractId, pageable);
  }

}


