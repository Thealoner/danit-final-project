package com.danit.controllers;

import com.danit.Application;
import com.danit.TestUtils;
import com.danit.facades.ClientFacade;
import com.danit.models.Client;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.ClientRepository;
import com.danit.services.ClientService;
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
public class ClientControllerTest {

  private final static String url = "/clients";
  private static boolean dbInit = false;
  @Autowired
  TestUtils testUtils;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  ClientFacade clientFacade;
  @Autowired
  ClientRepository clientRepository;
  @Autowired
  ClientService clientService;
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
  public void createClients() throws Exception {
    headers = testUtils.getHeader(template, UserRolesEnum.USER);

    if (dbInit) {
      return;
    }
    dbInit = true;

    //clientRepository.deleteAll();
    long numberOfEntities = clientService.getNumberOfEntities();

    List<Client> clients = new ArrayList<>(20);
    for (int i = 0; i < 10; i++) {
      Client client = new Client();
      client.setFirstName("TestClientName" + i);
      clients.add(client);
    }

    for (int i = 1; i < 11; i++) {
      Client client = new Client();
      client.setFirstName("SearchTestClientFirstName" + i);
      client.setLastName("SearchTestClientLastName" + i);
      clients.add(client);
    }

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(clients);

    this.mockMvc.perform(post(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());
    Assert.assertEquals(clientService.getNumberOfEntities(), numberOfEntities + 20);
  }

  @Test
  public void deleteClientsBySeveralMethods() throws Exception {
    long numberOfEntities = clientService.getNumberOfEntities();
    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[{\"id\": 1001},{\"id\": 1002},{\"id\": 1003}]"))
        .andExpect(status().isOk());
    Assert.assertEquals(clientService.getNumberOfEntities(), numberOfEntities - 3);

    this.mockMvc.perform(delete(url + "/1004").headers(headers))
        .andExpect(status().isOk());

    Assert.assertEquals(clientService.getNumberOfEntities(), numberOfEntities - 4);
  }

  public void updateClientData() throws Exception {
    List<Client> clients = clientService.getAllEntities(PageRequest.of(1,
        6,
        new Sort(Sort.Direction.ASC, "id"))).getContent();
    clients.forEach(client -> {
      client.setFirstName("UpdatedFirstName");
      client.setLastName("UpdatedLastName");
      client.setGender("UpdatedGender");
      client.setActive(false);
    });

    String json = objectMapper.writer().writeValueAsString(clients);
    String responseJson = this.mockMvc.perform(put(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    List<Client> updatedClients = objectMapper.readValue(responseJson, new TypeReference<List<Client>>() {
    });
    updatedClients.forEach(client -> {
      Assert.assertEquals("UpdatedFirstName", client.getFirstName());
      Assert.assertEquals("UpdatedLastName", client.getLastName());
      Assert.assertEquals("UpdatedGender", client.getGender());
      Assert.assertEquals(false, client.getActive());
    });
  }

  @Test
  public void deleteNonExistingClient() throws Exception {

    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[{\"id\": 2001},{\"id\": 2002},{\"id\": 2003}]"))
        .andExpect(status().isNotFound());

    this.mockMvc.perform(delete(url + "/2001").headers(headers))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllClients() throws Exception {
    long clientQuantity = clientService.getNumberOfEntities();

    mockMvc.perform(get(url).headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(clientQuantity));
  }

  @Test
  public void getAllContractsShort() throws Exception {
    long clientQuantity = clientService.getNumberOfEntities();

    mockMvc.perform(get(url + "/short").headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(clientQuantity));
  }

  @Test
  public void getAllClientsIds() throws Exception {
    long clientQuantity = clientService.getNumberOfEntities();

    mockMvc.perform(get(url + "/ids").headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(clientQuantity));
  }

  @Test
  public void testPageable() throws Exception {
    int elementsPerPage = 3;
    long numberOfClients = clientService.getNumberOfEntities();
    int numberOfPages = (int) Math.ceil((double) numberOfClients / (double) elementsPerPage);

    long currentPage = 1;
    long elementsLeft = numberOfClients - (currentPage - 1) * elementsPerPage;

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
          .andExpect(jsonPath("$.meta.totalElements").value(numberOfClients))
          .andReturn().getResponse().getContentAsString();

      JSONObject obj = new JSONObject(responseJson);
      String pageDataJson = obj.getString("data");
      List<Client> updatedClients = objectMapper.readValue(pageDataJson, new TypeReference<List<Client>>() {
      });
      Assert.assertEquals(currentElements, updatedClients.size());
      currentPage++;
      elementsLeft = numberOfClients - (currentPage - 1) * elementsPerPage;
    }
  }

  @Test
  public void testSearchAndFilterAndPagination() throws Exception {

    //Search by mask in all fields
    mockMvc.perform(get(url + "?search=SearchTestClient&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(10));
    //Search by mask in particular field
    mockMvc.perform(get(url + "?firstName=SearchTestClient&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(10));
    //Search with equal
    mockMvc.perform(get(url + "?search=SearchTestClient&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));
    //Field search with equal
    mockMvc.perform(get(url + "?firstName=SearchTestClient&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));

    //search in all fields w/o equal
    mockMvc.perform(get(url + "?search=SearchTestClientFirstName1&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(2));
    //search by field w/o equal
    mockMvc.perform(get(url + "?search=SearchTestClientFirstName1&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(2));
    //search in all fields with equal
    mockMvc.perform(get(url + "?firstName=SearchTestClientFirstName1&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));
    //search by field with equal
    mockMvc.perform(get(url + "?firstName=SearchTestClientFirstName1&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));

    //search by several fields
    mockMvc.perform(get(url + "?firstName=SearchTestClientFirstName1&lastName=SearchTestClientLastName0&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));

    mockMvc.perform(get(url + "?firstName=SearchTestClientFirstName9&lastName=SearchTestClientLastName9&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));

    //search by several fields w/o equal
    mockMvc.perform(get(url + "?firstName=SearchTestClientFirstName1&lastName=SearchTestClientLastName1&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(2));
    //search by several fields with equal
    mockMvc.perform(get(url + "?firstName=SearchTestClientFirstName1&lastName=SearchTestClientLastName1&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));

    //pagination test
    mockMvc.perform(get(url + "?firstName=SearchTestClientFirstName&page=1&size=5&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentPage").value(1))
        .andExpect(jsonPath("$.meta.pagesTotal").value(2))
        .andExpect(jsonPath("$.meta.elementsPerPage").value(5))
        .andExpect(jsonPath("$.meta.currentElements").value(5));
  }

  @Test
  public void getClientByExistingId() throws Exception {
    String responseJson = mockMvc.perform(get(url + "/" + 1005).headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(1))
        .andReturn().getResponse().getContentAsString();

    JSONObject obj = new JSONObject(responseJson);
    String pageDataJson = obj.getString("data");
    Client receivedClient = objectMapper.readValue(pageDataJson, Client.class);

    Assert.assertEquals(new Long(1005), receivedClient.getId());
  }

  @Test
  public void getClientByNotExistingId() throws Exception {
    mockMvc.perform(get(url + "/" + 1021).headers(headers))
        .andExpect(status().isNotFound());
  }

}