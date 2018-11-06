package com.danit.controllers;

import com.danit.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestRestTemplate template;

  private HttpHeaders headers = new HttpHeaders();

  @MockBean
  ClientService clientServiceMock;

  @Autowired
  ObjectMapper objectMapper;


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
  public void isOkWhenAdminAccess() throws Exception {
    HttpHeaders header = setHeader();
    mockMvc
        .perform(get("/clients").headers(header))
        .andExpect(status().isOk());
  }

  private byte[] toJson(Object object) throws Exception {
    return this.objectMapper
        .writeValueAsString(object).getBytes();
  }

}
