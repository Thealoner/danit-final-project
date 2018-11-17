package com.danit.controllers;

import com.danit.TestUtils;
import com.danit.models.Client;
import com.danit.models.UserRolesEnum;
import com.danit.services.ClientService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClientControllerTest {

  @Autowired
  TestUtils testUtils;
  @Autowired
  ClientService clientService;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void isOkWhenAdminAccess() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc
        .perform(get("/clients").headers(header))
        .andExpect(status().isOk());
  }

  @Test
  public void saveClientIfNotExists() throws Exception {
    int numberOfClients = clientService.getNumberOfClients();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post("/clients").headers(header)
        .contentType("application/json")
        .content("[{\n"
            + "    \"birthDate\": \"1978-12-22\",\n"
            + "    \"email\": \"alex2021@gmail.com\",\n"
            + "    \"firstName\": \"Alexey\",\n"
            + "    \"gender\": \"Female\",\n"
            + "    \"lastName\": \"Grinkov\",\n"
            + "    \"phoneNumber\": \"155-846-2959\",\n"
            + "    \"active\": true\n"
            + "  }]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/clients").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(numberOfClients + 1)));

  }

  @Test
  public void updateClientData() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post("/clients").headers(header)
        .contentType("application/json")
        .content("[{\n"
            + "    \"birthDate\": \"1978-12-22\",\n"
            + "    \"email\": \"alex2021@gmail.com\",\n"
            + "    \"firstName\": \"TestUser1\",\n"
            + "    \"gender\": \"Female\",\n"
            + "    \"lastName\": \"Grinkov\",\n"
            + "    \"phoneNumber\": \"155-846-2959\",\n"
            + "    \"active\": true\n"
            + "  }]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    List<Client> actualObj = mapper.readValue(responseJson, new TypeReference<List<Client>>() {
    });
    long createdId = actualObj.get(0).getId();

    responseJson = mockMvc.perform(put("/clients/").headers(header)
        .contentType("application/json")
        .content("[{\n"
            + "    \"id\": " + createdId + ", \n"
            + "    \"firstName\": \"TestUser2\"\n"
            + "  }]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    actualObj = mapper.readValue(responseJson, new TypeReference<List<Client>>() {
    });
    Assert.assertEquals("TestUser2", actualObj.get(0).getFirstName());

  }

  @Test
  public void deleteClientIfExists() throws Exception {
    int numberOfClients = clientService.getNumberOfClients();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete("/clients").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "      \"id\": 1005,\n" +
            "      \"firstName\": \"Ly\",\n" +
            "      \"lastName\": \"Lotherington\",\n" +
            "      \"gender\": \"Male\",\n" +
            "      \"birthDate\": \"16-11-1996\",\n" +
            "      \"phoneNumber\": \"647-775-6424\",\n" +
            "      \"email\": \"llotherington4@wikipedia.org\",\n" +
            "      \"active\": true,\n" +
            "      \"contracts\": []\n" +
            "   }]"));

    mockMvc.perform(get("/clients").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(numberOfClients - 1)));
  }

  @Test
  public void deleteClientById() throws Exception {
    int numberOfClients = clientService.getNumberOfClients();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post("/clients").headers(header)
        .contentType("application/json")
        .content("[{\n"
            + "    \"birthDate\": \"1978-12-22\",\n"
            + "    \"email\": \"alex2021@gmail.com\",\n"
            + "    \"firstName\": \"TestUser1\",\n"
            + "    \"gender\": \"Female\",\n"
            + "    \"lastName\": \"Grinkov\",\n"
            + "    \"phoneNumber\": \"155-846-2959\",\n"
            + "    \"active\": true\n"
            + "  }]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    List<Client> actualObj = mapper.readValue(responseJson, new TypeReference<List<Client>>() {
    });
    long createdId = actualObj.get(0).getId();

    mockMvc.perform(get("/clients").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(numberOfClients + 1)));

    mockMvc.perform(delete("/clients/" + createdId).headers(header))
        .andExpect(status().isOk());

    mockMvc.perform(get("/clients").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(numberOfClients)));


  }

}
