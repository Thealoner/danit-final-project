package com.danit.controllers;

import com.danit.TestUtils;
import com.danit.models.UserRolesEnum;
import com.danit.services.ServiceCategoryService;
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
public class ServiceEmployeeEmployeeCategoryControllerTest {
  @Autowired
  TestUtils testUtils;
  @Autowired
  ServiceCategoryService serviceCategoryService;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void saveServiceCategoryIfNotExists() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc.perform(post("/service_categories").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"title\": \"SPA\",\n" +
            "    \"services\": [],\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk());
  }

  @Test
  public void updateServiceIfExists() throws Exception {
    int numberOfServiceCategories = serviceCategoryService.getTotalQuantityOfServiceCategories();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(post("/service_categories").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"title\": \"BAR\",\n" +
            "    \"services\": [],\n" +
            "    \"active\": false\n" +
            "}]"))
        .andExpect(status().isOk());


    mockMvc.perform(put("/service_categories").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\": 1004,\n" +
            "    \"title\": \"BAR\",\n" +
            "    \"active\": true\n" +
            "}]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/service_categories").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[?(@.id == 1004)].active", hasItem(true)));

  }

  @Test
  public void deleteServicesIfExists() throws Exception {
    int numberOfServiceCategories = serviceCategoryService.getTotalQuantityOfServiceCategories();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete("/service_categories").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\": 1001,\n" +
            "    \"title\": \"SPA\",\n" +
            "    \"services\": [\n" +
            "      {\n" +
            "        \"id\": 1001,\n" +
            "        \"title\": \"Total Body Workout\",\n" +
            "        \"price\": \"200\",\n" +
            "        \"cost\": \"200\",\n" +
            "        \"unit\": \"min\",\n" +
            "        \"unitsNumber\": \"55\",\n" +
            "        \"active\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/service_categories").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(numberOfServiceCategories - 1)));

  }

}
