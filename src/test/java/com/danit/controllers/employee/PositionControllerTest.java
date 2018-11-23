package com.danit.controllers.employee;

import com.danit.TestUtils;
import com.danit.models.UserRolesEnum;
import com.danit.models.employee.Position;
import com.danit.services.ClientService;
import com.danit.services.employee.PositionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsCollectionWithSize;
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

import java.sql.SQLOutput;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PositionControllerTest {

  @Autowired
  TestUtils testUtils;
  @Autowired
  PositionService positionService;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;


  @Test
  public void isOkWhenAdminAccess() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc
        .perform(get("/position").headers(header))
        .andExpect(status().isOk());
  }

  @Test
  public void getAllPositions() throws Exception {
    int currentQuant = positionService.getPositionQuant();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post("/position").headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Boss\",\n"
            + "    \"description\": \"Big BOSS\"\n"
            + "  }"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/position").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(currentQuant + 1)));
  }

  @Test
  public void getPositionByIdTest() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post("/position").headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Boss\",\n"
            + "    \"description\": \"Big BOSS\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Position actualObj = mapper.readValue(responseJson, new TypeReference<Position>() {
    });
    assertEquals(actualObj, positionService.getPositionById(actualObj.getId()));
  }


  @Test
  public void updatePositionTest() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post("/position").headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Boss\",\n"
            + "    \"description\": \"Big BOSS\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Position actualObj = mapper.readValue(responseJson, new TypeReference<Position>() {
    });
    long createdId = actualObj.getId();
    System.out.println(actualObj);

    responseJson = mockMvc.perform(put("/position/" + createdId).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"id\": " + createdId + ", \n"
            + "    \"name\": \"TestPosition\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    actualObj = mapper.readValue(responseJson, new TypeReference<Position>() {
    });
    System.out.println(actualObj);
    assertEquals("TestPosition", actualObj.getName());

  }

  @Test
  public void createPositionTest() throws Exception {
    int currentQuant = positionService.getPositionQuant();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post("/position").headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Boss\",\n"
            + "    \"description\": \"Big BOSS\"\n"
            + "  }"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/position").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(currentQuant + 1)));
  }

  @Test
  public void expect500WhenNoDataFoundService() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(get("/position/3000000").headers(header))
        .andExpect(status().isNotFound());
  }

  @Test
  public void expect500WhenDeleteNonexistentService() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete("/position/3000000").headers(header))
        .andExpect(status().is(500));
  }
}


