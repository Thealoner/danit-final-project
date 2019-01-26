package com.danit.controllers;

import com.danit.Application;
import com.danit.TestUtils;
import com.danit.facades.CardFacade;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.CardRepository;
import com.danit.services.CardService;
import com.danit.services.ContractService;
import com.danit.services.PaketService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
    Application.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CardControllerTest {

  private final static String url = "/cards";
  private static boolean dbInit = false;
  @Autowired
  TestUtils testUtils;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  CardFacade cardFacade;
  @Autowired
  CardRepository cardRepository;
  @Autowired
  CardService cardService;
  @Autowired
  PaketService paketService;
  @Autowired
  ContractService contractService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestRestTemplate template;
  private HttpHeaders headers;

  @Before
  public void createCards() throws Exception {
    headers = testUtils.getHeader(template, UserRolesEnum.USER);

    if (dbInit) {
      return;
    }
    dbInit = true;

    long numberOfEntities = cardService.getNumberOfEntities();

    List<Card> cards = new ArrayList<>(20);
    for (int i = 0; i < 10; i++) {
      Card Card = new Card();
      Card.setCode("TestCardCode" + i);
      Card.setActive(false);
      cards.add(Card);
    }

    for (int i = 1; i < 11; i++) {
      Card Card = new Card();
      Card.setCode("SearchTestCardCode" + i);
      Card.setActive(false);
      cards.add(Card);
    }

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(cards);

    this.mockMvc.perform(post(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());
    Assert.assertEquals(cardService.getNumberOfEntities(), numberOfEntities + 20);
  }

  @Test
  public void deleteCardsBySeveralMethods() throws Exception {
    long numberOfEntities = cardService.getNumberOfEntities();
    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[{\"id\": 11},{\"id\": 12},{\"id\": 13}]"))
        .andExpect(status().isOk());
    Assert.assertEquals(cardService.getNumberOfEntities(), numberOfEntities - 3);

    this.mockMvc.perform(delete(url + "/14").headers(headers))
        .andExpect(status().isOk());

    Assert.assertEquals(cardService.getNumberOfEntities(), numberOfEntities - 4);
  }

  public void updateCardData() throws Exception {
    List<Card> Cards = cardService.getAllEntities(PageRequest.of(1,
        6,
        new Sort(Sort.Direction.ASC, "id"))).getContent();

    final int[] cardCodeIncr = {1};
    Cards.forEach(Card -> {
      Card.setCode("UpdatedCardCode" + cardCodeIncr[0]++);
      Card.setActive(true);
    });

    String json = objectMapper.writer().writeValueAsString(Cards);
    String responseJson = this.mockMvc.perform(put(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    List<Card> updatedCards = objectMapper.readValue(responseJson, new TypeReference<List<Card>>() {
    });

    cardCodeIncr[0] = 1;
    updatedCards.forEach(card -> {
      Assert.assertEquals("UpdatedCardCode" + cardCodeIncr[0]++, card.getCode());
      Assert.assertEquals(true, card.getActive());
    });
  }

  @Test
  public void deleteNonExistingCard() throws Exception {

    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[{\"id\": 2001},{\"id\": 2002},{\"id\": 2003}]"))
        .andExpect(status().isNotFound());

    this.mockMvc.perform(delete(url + "/2001").headers(headers))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllCards() throws Exception {
    long CardQuantity = cardService.getNumberOfEntities();

    mockMvc.perform(get(url).headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(CardQuantity));
  }

  @Test
  public void getAllContractsShort() throws Exception {
    long CardQuantity = cardService.getNumberOfEntities();

    mockMvc.perform(get(url + "/short").headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(CardQuantity));
  }

  @Test
  public void getAllCardsIds() throws Exception {
    long CardQuantity = cardService.getNumberOfEntities();

    mockMvc.perform(get(url + "/ids").headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(CardQuantity));
  }

  @Test
  public void testPageable() throws Exception {
    int elementsPerPage = 3;
    long numberOfCards = cardService.getNumberOfEntities();
    int numberOfPages = (int) Math.ceil((double) numberOfCards / (double) elementsPerPage);

    long currentPage = 1;
    long elementsLeft = numberOfCards - (currentPage - 1) * elementsPerPage;

    while (elementsLeft > 0) {
      long currentElements = (elementsLeft > elementsPerPage) ? elementsPerPage : elementsLeft;
      String responseJson = mockMvc.perform(get(url + "?page=" + currentPage + "&size=" + elementsPerPage).headers(headers))
          .andExpect(status().isOk())
          .andExpect(content()
              .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.meta.currentPage").value(currentPage))
          .andExpect(jsonPath("$.meta.pagesTotal").value(numberOfPages))
          .andExpect(jsonPath("$.meta.elementsPerPage").value(elementsPerPage))
          .andExpect(jsonPath("$.meta.currentElements").value(currentElements))
          .andExpect(jsonPath("$.meta.totalElements").value(numberOfCards))
          .andReturn().getResponse().getContentAsString();

      JSONObject obj = new JSONObject(responseJson);
      String pageDataJson = obj.getString("data");
      List<Card> updatedCards = objectMapper.readValue(pageDataJson, new TypeReference<List<Card>>() {
      });
      Assert.assertEquals(currentElements, updatedCards.size());
      currentPage++;
      elementsLeft = numberOfCards - (currentPage - 1) * elementsPerPage;
    }
  }

  @Test
  public void testSearchAndFilterAndPagination() throws Exception {

    //Search by mask in all fields
    mockMvc.perform(get(url + "?search=SearchTestCardCode&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(10));
    //Search by mask in particular field
    mockMvc.perform(get(url + "?code=SearchTestCardCode&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(10));
    //Search with equal
    mockMvc.perform(get(url + "?search=SearchTestCardCode&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));
    //Field search with equal
    mockMvc.perform(get(url + "?code=SearchTestCardCode&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));

    //search in all fields w/o equal
    mockMvc.perform(get(url + "?search=SearchTestCardCode1&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(2));
    //search by field w/o equal
    mockMvc.perform(get(url + "?code=SearchTestCardCode1&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(2));
    //search in all fields with equal
    mockMvc.perform(get(url + "?search=SearchTestCardCode1&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));
    //search by field with equal
    mockMvc.perform(get(url + "?code=SearchTestCardCode1&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));

    //search by several fields with equal
    mockMvc.perform(get(url + "?code=SearchTestCardCode1&active=true&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));

    mockMvc.perform(get(url + "?code=SearchTestCardCode1&active=false&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));

    //search by several fields w/o equal
    mockMvc.perform(get(url + "?code=SearchTestCardCode1&active=false&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(2));
    //search by several fields with equal
    mockMvc.perform(get(url + "?code=SearchTestCardCode1&active=false&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));

    //pagination test
    mockMvc.perform(get(url + "?code=SearchTestCardCode&page=1&size=5&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentPage").value(1))
        .andExpect(jsonPath("$.meta.pagesTotal").value(2))
        .andExpect(jsonPath("$.meta.elementsPerPage").value(5))
        .andExpect(jsonPath("$.meta.currentElements").value(5));
  }

  @Test
  public void getCardByExistingId() throws Exception {
    String responseJson = mockMvc.perform(get(url + "/" + 1).headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(1))
        .andReturn().getResponse().getContentAsString();

    JSONObject obj = new JSONObject(responseJson);
    String pageDataJson = obj.getString("data");
    Card receivedCard = objectMapper.readValue(pageDataJson, Card.class);

    Assert.assertEquals(new Long(1), receivedCard.getId());
  }

  @Test
  public void getCardByNotExistingId() throws Exception {
    mockMvc.perform(get(url + "/" + 2001).headers(headers))
        .andExpect(status().isNotFound());
  }

  @Test
  public void assignCardsToExistingContract() throws Exception {
    Contract contract = new Contract();
    long createdContractId = contractService.saveEntity(contract).getId();
    List<Card> cards = new ArrayList<>(2);
    for (int i = 0; i < 2; i++) {
      Card card = new Card();
      card.setCode("CardCodeForAssigningToContractTest" + i);
      cards.add(card);
    }
    List<Card> savedCards = cardService.saveEntities(cards);
    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(savedCards);

    mockMvc.perform(put("/contracts/" + createdContractId + "/cards").headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());

    mockMvc.perform(get("/contracts/" + createdContractId + "/cards").headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(savedCards.size()));

    mockMvc.perform(delete("/contracts/" + createdContractId + "/cards").headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());

    mockMvc.perform(get("/contracts/" + createdContractId + "/cards").headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(0));

    //Assign by id
    mockMvc.perform(put("/contracts/" + createdContractId + "/card/" + savedCards.get(0).getId()).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());

    mockMvc.perform(get("/contracts/" + createdContractId + "/cards").headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(1));

    mockMvc.perform(delete("/contracts/" + createdContractId + "/card/" + savedCards.get(0).getId()).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());

    mockMvc.perform(get("/contracts/" + createdContractId + "/cards").headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(0));

  }

}