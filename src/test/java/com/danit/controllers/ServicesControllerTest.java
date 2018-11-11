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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
  public void saveServices() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc
        .perform(post("/services").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"title\": \"Yoga\",\n" +
            "    \"price\": \"200\",\n" +
            "    \"cost\": \"200\",\n" +
            "    \"unit\": \"min\",\n" +
            "    \"unitsNumber\": \"55\",\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isCreated());
  }

}
