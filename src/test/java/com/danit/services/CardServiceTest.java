package com.danit.services;

import com.danit.Application;
import com.danit.dto.service.CardListRequestDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.Card;
import com.danit.repositories.CardRepository;
import com.danit.repositories.UserRepository;
import com.danit.repositories.specifications.CardListSpecification;
import com.danit.utils.ServiceUtils;
import com.danit.utils.WebSocketUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class CardServiceTest {

  @InjectMocks
  CardService cardService;

  @Mock
  CardRepository cardRepository;

  @Mock
  WebSocketUtils webSocketUtils;

  @Mock
  CardListSpecification cardSpecification;

  @Mock
  ServiceUtils serviceUtils;

  @Mock
  SimpMessageSendingOperations messagingTemplate;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  Card getMockCard(String code, Boolean active) {
    Card card = new Card();
    card.setCode(code);
    card.setActive(active);
    return card;
  }

  @Test
  public void getCardByIdTest() {
    Card card = getMockCard("1234567", true);
    when(cardRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(card));
    Card cardById = cardService.getEntityById(1L);
    assertEquals("1234567", cardById.getCode());
    assertEquals(true, cardById.getActive());
  }

  @Test
  public void getAllCardsPageableTest() {
    List<Card> cards = new ArrayList<Card>();
    cards.add(getMockCard("1234567", true));
    cards.add(getMockCard("1234568", false));
    cards.add(getMockCard("1234569", true));
    Page<Card> page = new PageImpl<Card>(cards);
    Pageable pageable = PageRequest.of(0, 4);

    when(cardRepository.findAll(pageable)).thenReturn(page);

    Page<Card> getAllEntities = cardService.getAllEntities(pageable);

    assertEquals(3, getAllEntities.getNumberOfElements());
    verify(cardRepository, times(1)).findAll(pageable);
  }

  @Test
  public void getAllCardsPageableWithSpecificationTest() {
    List<Card> cards = new ArrayList<Card>();
    cards.add(getMockCard("1234567", true));
    cards.add(getMockCard("1234569", true));
    Page<Card> page = new PageImpl<Card>(cards);
    Pageable pageable = PageRequest.of(0, 4);
    CardListRequestDto cardListRequestDto = new CardListRequestDto();
    cardListRequestDto.setActive("true");

    when(cardRepository.findAll(cardSpecification.getFilter(cardListRequestDto), pageable)).thenReturn(page);

    Page<Card> getAllEntities = cardService.getAllEntities(cardListRequestDto, pageable);

    assertEquals(2, getAllEntities.getNumberOfElements());
    verify(cardRepository, times(1))
        .findAll(cardSpecification.getFilter(cardListRequestDto), pageable);
  }

  /*@Test
  public void saveCardsTest() {
    List<Card> cards = new ArrayList<Card>();

    for (int i = 1; i < 4; i++) {
      cards.add(getMockCard("1234567" + i, true));
    }

    when(cardRepository.saveAll(cards)).thenReturn(cards);

    List<Card> savedCards = cardService.saveEntities(cards);

    assertEquals(3, savedCards.size());
    verify(cardRepository, times(1)).saveAll(cards);
  }*/


  /*@Test
  public void saveCardTest() {
    Card card = getMockCard("1234567", true);
    cardService.saveEntity(card);
    verify(cardRepository, times(1)).save(card);
  }*/

  @Test
  public void updateCardsTests() {
    List<Card> cards = new ArrayList<Card>();

    for (int i = 1; i < 4; i++) {
      cards.add(getMockCard("1234567" + i, true));
    }

    when(cardRepository.saveAll(cards)).thenReturn(cards);

    List<Card> updatedCards = cardService.updateEntities(cards);

    assertEquals(0, updatedCards.size());
    verify(cardRepository, times(1)).saveAll(cards);
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteCardTest() {
    Card card = getMockCard("1234567", true);
    card.setId(1L);
    cardService.deleteEntityById(1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteCardsTest() {
    List<Card> cards = new ArrayList<Card>();
    for (int i = 1; i < 4; i++) {
      Card crd = getMockCard("12345" + i, true);
      crd.setId(new Long(i));
      cards.add(crd);
    }
    cardService.deleteEntities(cards);
  }
}
