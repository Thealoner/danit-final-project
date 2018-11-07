package com.danit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RoleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestRestTemplate template;

  private HttpHeaders headers = new HttpHeaders();



  private String adminAuthJson = "{\"username\": \"Admin\", \"password\": \"1234\"}";


  public HttpHeaders setHeader(){
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(adminAuthJson, headers);
    ResponseEntity<String> resHeader = template.postForEntity("/login", entity, String.class);
    List<String> tokens = resHeader.getHeaders().get("Authorization");
    headers.clear();
    headers.set("Authorization", tokens.get(0));
    return headers;
  }

  @Test
  public void getAllRoles() throws Exception {
    HttpHeaders header = setHeader();
    this.mockMvc.perform(get("/roles").headers(header))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].role", is("ADMIN")));

  }
}
