package com.danit.controllers;

import com.danit.TestUtils;
import com.danit.models.UserRolesEnum;
import com.danit.services.ServicesService;
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
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
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
public class ServicesControllerTest {

  @Autowired
  TestUtils testUtils;
  @Autowired
  ServicesService servicesService;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void isOkWhenAdminAccess() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc
        .perform(get("/services").headers(header))
        .andExpect(status().isOk());
  }

  @Test
  public void saveServicesIfNotExist() throws Exception {
    int numberOfServices = servicesService.getTotalQuantityOfServices();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post("/services").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"title\": \"Wellness Consultation\",\n" +
            "    \"price\": \"200\",\n" +
            "    \"cost\": \"200\",\n" +
            "    \"unit\": \"min\",\n" +
            "    \"unitsNumber\": \"30\",\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/services").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(numberOfServices + 1)));

  }

  @Test
  public void updateServiceIfExists() throws Exception {
    int numberOfServices = servicesService.getTotalQuantityOfServices();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(put("/services").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\": 1001,\n" +
            "    \"title\": \"Total Body Workout\",\n" +
            "    \"price\": \"200\",\n" +
            "    \"cost\": \"200\",\n" +
            "    \"unit\": \"min\",\n" +
            "    \"unitsNumber\": \"55\",\n" +
            "    \"active\": false\n" +
            "  }]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/services").headers(header))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(numberOfServices)))
        .andExpect(jsonPath("$[?(@.id == 1001)].active", hasItem(false)));

  }

  @Test
  public void deleteServicesIfExists() throws Exception {
    int numberOfServices = servicesService.getTotalQuantityOfServices();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete("/services").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\": 1001,\n" +
            "    \"title\": \"Total Body Workout\",\n" +
            "    \"price\": \"200\",\n" +
            "    \"cost\": \"200\",\n" +
            "    \"unit\": \"min\",\n" +
            "    \"unitsNumber\": \"55\",\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/services").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(numberOfServices - 1)));

  }

  @Test(expected = NestedServletException.class)
  public void expect500WhenGetDeletedService() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete("/services").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\": 1001,\n" +
            "    \"title\": \"Total Body Workout\",\n" +
            "    \"price\": \"200\",\n" +
            "    \"cost\": \"200\",\n" +
            "    \"unit\": \"min\",\n" +
            "    \"unitsNumber\": \"55\",\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/services/1001").headers(header))
        .andExpect(status().isInternalServerError());
  }

}
