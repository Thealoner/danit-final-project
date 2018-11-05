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
public class ClientControllerTest {

  @Autowired
  private TestRestTemplate template;

  private HttpHeaders headers = new HttpHeaders();

  private String adminAuthJson = "{\"username\": \"Admin\", \"password\": \"1234\"}";


  @Test()
  public void ifClientIsDeletedAllHisContractsMustBeDeletedToo() throws Exception {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(adminAuthJson, headers);
    ResponseEntity<HttpHeaders> resHeader = template.postForEntity("/login", entity, HttpHeaders.class);
    List<String> tokens = resHeader.getHeaders().get("Authorization");

    headers.clear();
    headers.set("Authorization", tokens.get(0));


    template.exchange("/clients/1001",
        HttpMethod.DELETE,
        new HttpEntity<Object>(headers),
        Void.class);

    ResponseEntity<String> result = template.exchange("/contracts/1001",
        HttpMethod.GET,
        new HttpEntity<Object>(headers),
        String.class);

    assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

  }

}
