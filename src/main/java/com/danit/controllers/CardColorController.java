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

import static com.danit.utils.SpringSecurityUtils.getCurrentPrincipalName;

@RestController
public class CardColorController {

  private Logger logger = LoggerFactory.getLogger(CardColorController.class);

  private CardColorService cardColorService;

  @Autowired
  public CardColorController(CardColorService cardColorService) {
    this.cardColorService = cardColorService;
  }

  @PostMapping("/cards")
  List<CardColor> createCards(@RequestBody List<CardColor> cards) {
    logger.info("User " + getCurrentPrincipalName() + " is saving new cards: " + cards);
    return cardColorService.saveCardColors(cards);
  }

  @GetMapping("/cards")
  List<CardColor> getAllCards() {
    logger.info("User " + getCurrentPrincipalName() + " got all cards data");
    return cardColorService.getAllCardColors();
  }

  @GetMapping("/cards/{id}")
  CardColor getCardById(@PathVariable(name = "id") long id) {
    logger.info("User " + getCurrentPrincipalName() + " got card data with id: " + id);
    return cardColorService.getCardColorById(id);
  }

  @PutMapping("/cards")
  public void addCards(@RequestBody List<CardColor> cards) {
    logger.info("User " + getCurrentPrincipalName() + " is updating cards data: " + cards);
    cardColorService.saveCardColors(cards);
  }

  @DeleteMapping("/cards/{id}")
  public void deleteCardById(@PathVariable(name = "id") long id) {
    logger.info("User " + getCurrentPrincipalName() + " try to delete card with id: " + id);
    cardColorService.deleteCardColorById(id);
  }

  @DeleteMapping("/cards")
  public void deleteCards(@RequestBody List<CardColor> cards) {
    logger.info("User " + getCurrentPrincipalName() + " is trying to delete cards: " + cards);
    cardColorService.deleteCardColors(cards);
  }

}