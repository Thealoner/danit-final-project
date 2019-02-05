package com.danit;

import com.danit.models.User;
import com.danit.models.UserRolesEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@Configuration
public class TestUtils {

  @Autowired
  ObjectMapper objectMapper;

  private String adminAuthJson = "{\"username\": \"Admin\", \"password\": \"1234\"}";

  private String notAdminAuthJson = "{\"username\": \"Sarah\", \"password\": \"1234\"}";

  public HttpHeaders getHeader(TestRestTemplate testRestTemplate, UserRolesEnum userRole) {
    String jsonAuth = userRole.name().equals("ADMIN") ? adminAuthJson : notAdminAuthJson;
    return performAuth(testRestTemplate, jsonAuth);
  }

  public HttpHeaders getHeader(TestRestTemplate testRestTemplate, User user) throws JsonProcessingException {
    String jsonAuth = objectMapper.writer().writeValueAsString(user);
    return performAuth(testRestTemplate, jsonAuth);
  }

  private HttpHeaders performAuth(TestRestTemplate testRestTemplate, String jsonAuth) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(jsonAuth, headers);
    ResponseEntity<String> resHeader = testRestTemplate.postForEntity("/auth", entity, String.class);
    List<String> tokens = resHeader.getHeaders().get("Authorization");
    headers.clear();
    headers.set("Authorization", tokens.get(0));
    return headers;
  }
}