package com.danit.controllers;

import com.danit.models.Card;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardColorController {

  private Logger logger = LoggerFactory.getLogger(CardColorController.class);

  private CardColorService cardColorService;

  @Autowired
  public CardColorController(CardColorService cardColorService) {
    this.cardColorService = cardColorService;
  }

  @PostMapping
  List<Card> createCards(@RequestBody List<Card> cards, Principal principal) {
    logger.info(principal.getName() + " is saving new cards: " + cards);
    return cardColorService.saveCardColors(cards);
  }

  @GetMapping
  List<Card> getAllCards(Principal principal) {
    logger.info(principal.getName() + " got all cards data");
    return cardColorService.getAllCardColors();
  }

  @GetMapping("/{id}")
  Card getCardById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got card data with id: " + id);
    return cardColorService.getCardColorById(id);
  }

  @PutMapping
  List<Card> addCards(@RequestBody List<Card> cards, Principal principal) {
    logger.info(principal.getName() + " is updating cards data: " + cards);
    return cardColorService.saveCardColors(cards);
  }

  @DeleteMapping("/{id}")
  public void deleteCardById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete card with id: " + id);
    cardColorService.deleteCardColorById(id);
  }

  @DeleteMapping
  public void deleteCards(@RequestBody List<Card> cards, Principal principal) {
    logger.info(principal.getName() + " is trying to delete cards: " + cards);
    cardColorService.deleteCardColors(cards);
  }

}
