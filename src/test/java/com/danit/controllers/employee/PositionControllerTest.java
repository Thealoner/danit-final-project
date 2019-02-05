package com.danit.controllers.employee;

import com.danit.TestUtils;
import com.danit.models.UserRolesEnum;
import com.danit.models.employee.Position;
import com.danit.services.employee.PositionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PositionControllerTest {

  private static final String url = "/position";
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
    int currentQty = positionService.getPositionQty();
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
        .andExpect(jsonPath("$", hasSize(currentQty + 1)));
  }

  @Test
  public void createPosition() throws Exception {
    int currentQty = positionService.getPositionQty();
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
        .andExpect(jsonPath("$", hasSize(currentQty + 1)));
  }

  @Test
  public void updatePosition() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test position\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Position actualObj = mapper.readValue(responseJson, new TypeReference<Position>() {
    });
    long createdId = actualObj.getId();
    System.out.println(actualObj);

    responseJson = mockMvc.perform(put(url + "/" + createdId).headers(header)
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
  public void deletePositionById() throws Exception {
    int currentQty = positionService.getPositionQty();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test gym\",\n"
            + "    \"description\": \"Test gym\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Position actualObj = mapper.readValue(responseJson, new TypeReference<Position>() {
    });
    long createdId = actualObj.getId();

    assertEquals(currentQty + 1, positionService.getPositionQty());

    mockMvc.perform(delete(url + "/" + createdId).headers(header))
        .andExpect(status().isOk());

    assertEquals(currentQty, positionService.getAllPositions().size());

  }

  @Test
  public void expect404WhenNoDataFoundPosition() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(get(url + "/0").headers(header))
        .andExpect(status().isNotFound());
  }

  @Test
  public void expect500WhenDeleteNonexistentSPosition() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete(url + "/0").headers(header))
        .andExpect(status().is(500));
  }
}