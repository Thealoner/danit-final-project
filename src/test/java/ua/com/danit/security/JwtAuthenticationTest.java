package ua.com.danit.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthenticationTest {

  @Autowired
  private TestRestTemplate template;

  private HttpHeaders headers = new HttpHeaders();
  private String validAdminAuthJson = "{\"username\": \"Admin\", \"password\": \"1234\"}";
  private String nonValidAdminAuthJson = "{\"username\": \"Admin\", \"password\": \"1111\"}";


  @Test
  public void postMethodWithRightCredOnLoginShouldSucceedWith200() throws Exception {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(validAdminAuthJson, headers);
    ResponseEntity<String> resHeader = template.postForEntity("/login", entity, String.class);
    assertEquals(HttpStatus.OK, resHeader.getStatusCode());
  }

  @Test
  public void postMethodWithBadCredOnLoginShouldSucceedWith403() throws Exception {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(nonValidAdminAuthJson, headers);
    ResponseEntity<String> resHeader = template.postForEntity("/login", entity, String.class);
    assertEquals(HttpStatus.FORBIDDEN, resHeader.getStatusCode());
  }

  @Test
  public void responseHeaderShouldContainBearing() throws Exception {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(validAdminAuthJson, headers);
    ResponseEntity<String> resHeader = template.postForEntity("/login", entity, String.class);
    List<String> tokens = resHeader.getHeaders().get("Authorization");
    Assert.assertTrue(Objects.nonNull(tokens));
    tokens.forEach(s -> assertTrue(s.contains("Bearer")));
  }

  @Test
  public void responseTokenShouldBeValidForAuth() throws Exception {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(validAdminAuthJson, headers);
    ResponseEntity<String> resHeader = template.postForEntity("/login", entity, String.class);
    List<String> tokens = resHeader.getHeaders().get("Authorization");
    Assert.assertTrue(Objects.nonNull(tokens));

    headers.clear();
    headers.set("Authorization", tokens.get(0));

    ResponseEntity<String> response = template.exchange("/users/1001", HttpMethod.GET,
        new HttpEntity<Object>(headers), String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  public void nonValidTokenShouldResp403() throws Exception {
    headers.clear();
    headers.set("Authorization", "randomtoken");
    ResponseEntity<String> response = template.exchange("/users/1001", HttpMethod.GET,
        new HttpEntity<Object>(headers), String.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }
}
