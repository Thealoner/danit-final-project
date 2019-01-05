package com.danit.services;

import com.danit.dto.service.CardListRequestDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.repositories.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class CardService extends AbstractBaseEntityService<Card, CardListRequestDto> {

  private CardRepository cardRepository;

  public CardService(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  public Page<Card> findAllContractsForClientId(Long contractId, Pageable pageable) {
    return cardRepository.findAllCardsForContractId(contractId, pageable);
  }

  @Override
  @Transactional
  public void deleteEntityById(long id) {
    Card card = super.getEntityById(id);
    if(Objects.nonNull(card.getContract())) {
      card.getContract().getCards().remove(card);
    } else {
      cardRepository.deleteById(id);
    }
  }

  @Override
  @Transactional
  public void deleteEntities(List<Card> entityList) {
    List<Card> cards = super.reloadEntities(entityList);
    List<Card> cardsToDelete = new ArrayList<>();
    cards.forEach(card -> {
      if(Objects.nonNull(card.getContract())) {
        card.getContract().getCards().remove(card);
      } else {
        cardsToDelete.add(card);
      }
    });
    if(cardsToDelete.size() > 0) {
      super.deleteEntities(cardsToDelete);
    }
  }
}


