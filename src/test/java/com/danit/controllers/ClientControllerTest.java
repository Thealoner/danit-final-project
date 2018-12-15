//package com.danit.controllers;
//
//import com.danit.Application;
//import com.danit.TestUtils;
//import com.danit.configuration.DbTestProfileJpaConfig;
//import com.danit.facades.ClientFacade;
//import com.danit.models.Client;
//import com.danit.models.UserRolesEnum;
//import com.danit.repositories.ClientRepository;
//import com.danit.services.ClientService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.jayway.jsonpath.JsonPath;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpHeaders;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {
//    Application.class,
//    DbTestProfileJpaConfig.class},
//    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class ClientControllerTest {
//
//  private final static String url = "/clients";
//
//  @Autowired
//  TestUtils testUtils;
//  @Autowired
//  ObjectMapper objectMapper;
//  @Autowired
//  ClientFacade clientFacade;
//  @Autowired
//  ClientRepository clientRepository;
//  @Autowired
//  ClientService clientService;
//  @Autowired
//  private MockMvc mockMvc;
//  @Autowired
//  private TestRestTemplate template;
//
//  HttpHeaders header;
//
//  @Before
//  public void setHeader() {
//    header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
//  }
//
//  @Test
//  public void isOkWhenAdminAccess() throws Exception {
//    mockMvc
//        .perform(get("/clients?page=0&size=2").headers(header))
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  public void createOneClientWithExistingEntities() throws Exception {
//
//    Client client = new Client();
//
//    ObjectWriter ow = objectMapper.writer();
//    String json = ow.writeValueAsString(client);
//
//    String responseJson = this.mockMvc.perform(post(url).headers(header)
//        .contentType("application/json")
//        .content("[" + json + "]"))
//        .andExpect(status().isOk())
//        .andReturn().getResponse().getContentAsString();
//
//    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
//    Long id = new Long(String.valueOf(object.get("id")));
////
////    this.mockMvc.perform(put(url + "/" + id + "/client/20").headers(header))
////        .andExpect(status().isOk());
////    this.mockMvc.perform(put(url + "/" + id + "/paket/1").headers(header))
////        .andExpect(status().isOk());
//
//    Client createdClient = clientService.getEntityById(id);
//
//    assertEquals(1L, (long) createdContract.getPaket().getId());
//    assertEquals(20L, (long) createdContract.getClient().getId());
//
//  }
//
////
////  @Test
////  public void saveClientIfNotExists() throws Exception {
////    int numberOfClients = clientService.getNumberOfClients();
////    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
////    this.mockMvc.perform(post("/clients").headers(header)
////        .contentType("application/json")
////        .content("[{\n"
////            + "    \"birthDate\": \"1978-12-22\",\n"
////            + "    \"email\": \"alex2021@gmail.com\",\n"
////            + "    \"firstName\": \"Alexey\",\n"
////            + "    \"gender\": \"Female\",\n"
////            + "    \"lastName\": \"Grinkov\",\n"
////            + "    \"phoneNumber\": \"155-846-2959\",\n"
////            + "    \"active\": true\n"
////            + "  }]"))
////        .andExpect(status().isCreated());
////    assertEquals(numberOfClients + 1, clientService.getNumberOfClients());
////  }
////
////  @Test
////  public void updateClientData() throws Exception {
////    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
////
////    String responseJson = this.mockMvc.perform(post("/clients").headers(header)
////        .contentType("application/json")
////        .content("[{\n"
////            + "    \"birthDate\": \"1978-12-22\",\n"
////            + "    \"email\": \"alex2021@gmail.com\",\n"
////            + "    \"firstName\": \"TestUser1\",\n"
////            + "    \"gender\": \"Female\",\n"
////            + "    \"lastName\": \"Grinkov\",\n"
////            + "    \"phoneNumber\": \"155-846-2959\",\n"
////            + "    \"active\": true\n"
////            + "  }]"))
////        .andExpect(status().isCreated())
////        .andReturn().getResponse().getContentAsString();
////
////    ObjectMapper mapper = new ObjectMapper();
////    List<Client> actualObj = mapper.readValue(responseJson, new TypeReference<List<Client>>() {
////    });
////    long createdId = actualObj.get(0).getId();
////
////    responseJson = mockMvc.perform(put("/clients/").headers(header)
////        .contentType("application/json")
////        .content("[{\n"
////            + "    \"id\": " + createdId + ", \n"
////            + "    \"firstName\": \"TestUser2\"\n"
////            + "  }]"))
////        .andExpect(status().isOk())
////        .andReturn().getResponse().getContentAsString();
////
////    actualObj = mapper.readValue(responseJson, new TypeReference<List<Client>>() {
////    });
////    assertEquals("TestUser2", actualObj.get(0).getFirstName());
////
////  }
////
////  @Test
////  public void deleteClientIfExists() throws Exception {
////    int numberOfClients = clientService.getNumberOfClients();
////    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
////
////    mockMvc.perform(delete("/clients").headers(header)
////        .contentType("application/json")
////        .content("[{\n" +
////            "      \"id\": 1005,\n" +
////            "      \"firstName\": \"Ly\",\n" +
////            "      \"lastName\": \"Lotherington\",\n" +
////            "      \"gender\": \"Male\",\n" +
////            "      \"birthDate\": \"16-11-1996\",\n" +
////            "      \"phoneNumber\": \"647-775-6424\",\n" +
////            "      \"email\": \"llotherington4@wikipedia.org\",\n" +
////            "      \"active\": true,\n" +
////            "      \"contracts\": []\n" +
////            "   }]"))
////        .andExpect(status().isOk());
////
////    assertEquals(numberOfClients - 1, clientService.getNumberOfClients());
////
////  }
////
////  @Test
////  public void deleteClientById() throws Exception {
////    int numberOfClients = clientService.getNumberOfClients();
////    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
////
////    String responseJson = this.mockMvc.perform(post("/clients").headers(header)
////        .contentType("application/json")
////        .content("[{\n"
////            + "    \"birthDate\": \"1978-12-22\",\n"
////            + "    \"email\": \"alex2021@gmail.com\",\n"
////            + "    \"firstName\": \"TestUser1\",\n"
////            + "    \"gender\": \"Female\",\n"
////            + "    \"lastName\": \"Grinkov\",\n"
////            + "    \"phoneNumber\": \"155-846-2959\",\n"
////            + "    \"active\": true\n"
////            + "  }]"))
////        .andExpect(status().isCreated())
////        .andReturn().getResponse().getContentAsString();
////
////    ObjectMapper mapper = new ObjectMapper();
////    List<Client> actualObj = mapper.readValue(responseJson, new TypeReference<List<Client>>() {
////    });
////    long createdId = actualObj.get(0).getId();
////
////    assertEquals(numberOfClients + 1, clientService.getNumberOfClients());
////
////    mockMvc.perform(delete("/clients/" + createdId).headers(header))
////        .andExpect(status().isOk());
////
////    assertEquals(numberOfClients, clientService.getNumberOfClients());
////
////  }
//}
