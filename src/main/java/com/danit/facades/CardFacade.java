package com.danit.facades;

import com.danit.dto.CardDto;
import com.danit.dto.service.CardListRequestDto;
import com.danit.models.Card;
import com.danit.services.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CardFacade extends AbstractDtoFacade<CardDto, Card, CardListRequestDto> {

  private CardService cardService;

  public CardFacade(CardService cardService) {
    this.cardService = cardService;
  }

  public Page<CardDto> findAllCardsForContractId(Long contractId, Pageable pageable) {
    return convertToDtos(cardService.findAllContractsForClientId(contractId, pageable));
  }

}
