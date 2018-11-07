package com.danit.controllers;

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

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BidirectionalRelationshipCLientContractRestTest {

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
  public void ifClientIsDeletedAllHisContractsMustBeDeletedToo() throws Exception {

    HttpHeaders header = setHeader();

    template.exchange("/clients/1002",
        HttpMethod.DELETE,
        new HttpEntity<Object>(header),
        Void.class);

    ResponseEntity<String> result1 = template.exchange("/contracts/1003",
        HttpMethod.GET,
        new HttpEntity<Object>(header),
        String.class);

    assertEquals(result1.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Test
  public void ifContractIsDeletedClientShouldRemain() throws Exception {

    HttpHeaders header = setHeader();

    template.exchange("/contracts/1004",
        HttpMethod.DELETE,
        new HttpEntity<Object>(header),
        Void.class);

    ResponseEntity<String> result1 = template.exchange("/clients/1003",
        HttpMethod.GET,
        new HttpEntity<Object>(header),
        String.class);

    assertEquals(result1.getStatusCode(), HttpStatus.OK);

  }
}
