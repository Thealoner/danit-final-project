package com.danit.controllers;

import com.danit.models.CardColor;
import com.danit.services.CardColorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardColorController {

  Logger logger = LoggerFactory.getLogger(ContractController.class);

  private CardColorService cardColorService;

  @Autowired
  public CardColorController(CardColorService cardColorService) {
    this.cardColorService = cardColorService;
  }

  @PostMapping("/cards")
  private void createCards(@RequestBody List<CardColor> cards) {
    logger.info("Adding new card");
    cardColorService.saveCardColors(cards);
    logger.info("Card saved");
  }

  @GetMapping("/cards/{id}")
  CardColor getCardById(@PathVariable(name = "id") long id) {
    return cardColorService.getCardColorById(id);
  }

  @GetMapping("/cards")
  List<CardColor> getAllCards() {
    return cardColorService.getAllCardColors();
  }

  @PutMapping("/cards")
  public void addCards(@RequestBody List<CardColor> cards) {
    cardColorService.saveCardColors(cards);
  }

  @DeleteMapping("/cards/{id}")
  public void deleteCardById(@PathVariable(name = "id") long id) {
    cardColorService.deleteCardColorById(id);
  }

  @DeleteMapping("/cards")
  public void deleteCards(@RequestBody List<CardColor> cards) {
    cardColorService.deleteCardColors(cards);
  }

}
