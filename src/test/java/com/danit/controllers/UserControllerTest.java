package com.danit.controllers;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private TestRestTemplate template;

  @Autowired
  private MockMvc mvc;

  private HttpHeaders headers = new HttpHeaders();

  private String adminAuthJson = "{\"username\": \"Admin\", \"password\": \"1234\"}";

  private String notAdminAuthJson = "{\"username\": \"Sarah\", \"password\": \"1234\"}";


  @Test
  public void accessForbiddenForNotAuthorizedUsers() throws Exception {
    this.mvc.perform(get("/users")).andExpect(status().isForbidden());
  }

  @Test
  public void adminAccessWithCorruptedTokenShouldBeForbidden() {
    headers.clear();
    headers.set("Authorization", "randomtoken");
    ResponseEntity<String> response = template.exchange("/users/1", HttpMethod.GET,
        new HttpEntity<Object>(headers), String.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  public void shouldReturn200WhenSendingRequestToControllerWithRoleAdmin() throws Exception {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(adminAuthJson, headers);
    ResponseEntity<String> resHeader = template.postForEntity("/auth", entity, String.class);
    List<String> tokens = resHeader.getHeaders().get("Authorization");
    Assert.assertTrue(Objects.nonNull(tokens));

    headers.clear();
    headers.set("Authorization", tokens.get(0));

    ResponseEntity<String> response = template.exchange("/users/1", HttpMethod.GET,
        new HttpEntity<Object>(headers), String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldReturn403WhenSendingRequestToControllerWithRoleNotAdmin() throws Exception {
    /*headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(notAdminAuthJson, headers);
    ResponseEntity<String> resHeader = template.postForEntity("/auth", entity, String.class);
    List<String> tokens = resHeader.getHeaders().get("Authorization");

    headers.clear();
    headers.set("Authorization", tokens.get(0));

    ResponseEntity<String> response = template.exchange("/users/1001", HttpMethod.GET,
        new HttpEntity<Object>(headers), String.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());*/
  }

}
