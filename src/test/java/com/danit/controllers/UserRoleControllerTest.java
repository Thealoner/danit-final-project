package com.danit.controllers;

import com.danit.TestUtils;
import com.danit.models.UserRolesEnum;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserRoleControllerTest {

  @Autowired
  TestUtils testUtils;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getAllRoles() throws Exception {
    /*HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    this.mockMvc.perform(get("/roles").headers(header))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[*].role", containsInAnyOrder("ADMIN", "USER")));*/
  }

  @Test
  public void saveAndDeleteRole() throws Exception {
    /*HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    this.mockMvc.perform(post("/roles").headers(header)
        .contentType("application/json")
        .content("[{\"role\": \"TEST\"}]"));

    this.mockMvc.perform(get("/roles").headers(header))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[*].role", containsInAnyOrder("ADMIN", "TEST", "USER")));

    this.mockMvc.perform(delete("/roles").headers(header)
        .contentType("application/json")
        .content("[{\"role\": \"TEST\"}]"));

    this.mockMvc.perform(get("/roles").headers(header))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[*].role", containsInAnyOrder("ADMIN", "USER")));*/
  }
}
