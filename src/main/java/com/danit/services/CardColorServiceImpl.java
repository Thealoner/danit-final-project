package com.danit.services;

import com.danit.models.CardColor;
import com.danit.repositories.CardColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class CardColorServiceImpl implements CardColorService {

  private CardColorRepository cardColorRepository;

  @Autowired
  public CardColorServiceImpl(CardColorRepository cardColorRepository) {
    this.cardColorRepository = cardColorRepository;
  }


  @Override
  public List<CardColor> getAllCardColors() {
    return cardColorRepository.findAll();
  }

  @Override
  public CardColor getCardColorById(long id) {
    return cardColorRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find CardColor with id=" + id));
  }

  @Override
  public void saveCardColors(List<CardColor> cards) {
    cardColorRepository.saveAll(cards);
  }

  @Override
  public void deleteCardColorById(long id) {
    cardColorRepository.deleteById(id);
  }

  @Override
  public void deleteCardColors(List<CardColor> cards) {
    Set<Long> cardsId = cardColorRepository.getAllCardColorsId();
    cards.forEach(card -> {
      if (!cardsId.contains(card.getId())) {
        throw new EntityNotFoundException("CardColor with id=" + card.getId() + " is not exist");
      }
    });
    cardColorRepository.deleteInBatch(cards);
  }
}
