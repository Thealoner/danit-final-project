package com.danit.services;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.Card;
import com.danit.repositories.CardColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public List<Card> getAllCardColors() {
    return cardColorRepository.findAll();
  }

  @Override
  public Card getCardColorById(long id) {
    return cardColorRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find Card with id=" + id));
  }

  @Override
  public List<Card> saveCardColors(List<Card> cards) {
    return cardColorRepository.saveAll(cards);
  }

  @Override
  public void deleteCardColorById(long id) {
    cardColorRepository.deleteById(id);
  }

  @Override
  public void deleteCardColors(List<Card> cards) {
    Set<Long> cardsId = cardColorRepository.getAllCardColorsId();
    cards.forEach(card -> {
      if (!cardsId.contains(card.getId())) {
        throw new EntityNotFoundException("Card with id=" + card.getId() + " is not exist");
      }
    });
    cardColorRepository.deleteInBatch(cards);
  }
}
