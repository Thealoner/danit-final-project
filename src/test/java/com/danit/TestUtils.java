package com.danit;

import com.danit.models.UserRolesEnum;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Configuration
public class TestUtils {

  private String adminAuthJson = "{\"username\": \"Admin\", \"password\": \"1234\"}";

  private String notAdminAuthJson = "{\"username\": \"Sarah\", \"password\": \"1234\"}";

  public HttpHeaders getHeader(TestRestTemplate testRestTemplate, UserRolesEnum userRole) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String jsonAuth = userRole.name().equals("ADMIN") ? adminAuthJson : notAdminAuthJson;
    HttpEntity<String> entity = new HttpEntity<String>(jsonAuth, headers);
    ResponseEntity<String> resHeader = testRestTemplate.postForEntity("/auth", entity, String.class);
    List<String> tokens = resHeader.getHeaders().get("Authorization");
    headers.clear();
    headers.set("Authorization", tokens.get(0));
    return headers;
  }
}
