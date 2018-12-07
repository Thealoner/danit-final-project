package com.danit.controllers;

import com.danit.dto.CardDto;
import com.danit.dto.Views;
import com.danit.dto.service.CardListRequestDto;
import com.danit.facades.CardFacade;
import com.danit.models.Card;
import com.danit.services.CardService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static com.danit.utils.ControllerUtils.convertPageToMap;

@Slf4j
@RestController
@RequestMapping("/cards")
public class CardController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all card data";
  private final CardService cardService;
  private final CardFacade cardFacade;

  @Autowired
  public CardController(CardFacade cardFacade, CardService cardService) {
    this.cardFacade = cardFacade;
    this.cardService = cardService;
  }

  //--------dto---------------------------------------------------------------------------------------------------------
  @JsonView(Views.Extended.class)
  @PostMapping("/extended")
  public ResponseEntity<List<CardDto>> createCardsDtoExtended(@RequestBody List<Card> cards, Principal principal) {
    log.info(principal.getName() + " is saving new cards: " + cards);
    return ResponseEntity.status(HttpStatus.CREATED).body(cardFacade.saveEntities(cards));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllCardsDtoIds(Pageable pageable,
                                                               Principal principal,
                                                               CardListRequestDto cardListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertPageToMap(cardFacade.getAllEntities(cardListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllCardsDtoShort(Pageable pageable,
                                                                 Principal principal,
                                                                 CardListRequestDto cardListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertPageToMap(cardFacade.getAllEntities(cardListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/extended")
  public ResponseEntity<Map<String, Object>> getAllCardsDtoExtended(Pageable pageable,
                                                                    Principal principal,
                                                                    CardListRequestDto cardListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertPageToMap(cardFacade.getAllEntities(cardListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}/extended")
  ResponseEntity<CardDto> getCardByIdDtoExtended(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got card data with id: " + id);
    return ResponseEntity.ok(cardFacade.getEntityById(id));
  }

  @JsonView(Views.Extended.class)
  @PutMapping("/extended")
  public ResponseEntity<List<CardDto>> updateCardsDto(@RequestBody List<Card> cards, Principal principal) {
    log.info(principal.getName() + " is updating cards data: " + cards);
    return ResponseEntity.ok(cardFacade.updateEntities(cards));
  }

  @DeleteMapping("/{id}/dto")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCardByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete card with id: " + id);
    cardFacade.deleteEntityById(id);
  }

  @DeleteMapping("/dto")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCardsDto(@RequestBody List<Card> cards, Principal principal) {
    log.info(principal.getName() + " is trying to delete cards: " + cards);
    cardFacade.deleteEntities(cards);
  }

  //------not dto-------------------------------------------------------------------------------------------------------

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllCards(Pageable pageable,
                                                         Principal principal,
                                                         CardListRequestDto cardListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    log.info("cardListRequestDto=" + cardListRequestDto);
    return ResponseEntity.ok(convertPageToMap(cardService.getAllEntities(cardListRequestDto, pageable)));
  }

  @GetMapping("/{id}")
  ResponseEntity<Card> getCardById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got card data with id: " + id);
    return ResponseEntity.ok(cardService.getEntityById(id));
  }

  @PostMapping
  public ResponseEntity<List<Card>> createClients(@RequestBody List<Card> cards, Principal principal) {
    log.info(principal.getName() + " is saving new cards: " + cards);
    return ResponseEntity.status(HttpStatus.CREATED).body(cardService.saveEntities(cards));
  }

  @PutMapping
  public ResponseEntity<List<Card>> addCards(@RequestBody List<Card> cards, Principal principal) {
    log.info(principal.getName() + " is updating cards data: " + cards);
    return ResponseEntity.ok(cardService.updateEntities(cards));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCardById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete card with id: " + id);
    cardService.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteCards(@RequestBody List<Card> cards, Principal principal) {
    log.info(principal.getName() + " is trying to delete cards: " + cards);
    cardService.deleteEntities(cards);
  }

}
