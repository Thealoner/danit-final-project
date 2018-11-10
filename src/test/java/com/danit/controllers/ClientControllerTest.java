package com.danit.controllers;

import com.danit.TestUtils;
import com.danit.models.UserRolesEnum;
import com.danit.services.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClientControllerTest {

  @Autowired
  private TestRestTemplate template;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  ClientService clientServiceMock;

  @Autowired
  TestUtils testUtils;

  @Test
  public void isOkWhenAdminAccess() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc
        .perform(get("/clients").headers(header))
        .andExpect(status().isOk());

    ResponseEntity<String> resHeader = template.exchange("/clients", HttpMethod.GET, new HttpEntity<>(header), String.class);
    System.out.println(resHeader.getBody());
  }

  @Test
  public void saveAndDeleteClient() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post("/clients").headers(header)
        .contentType("application/json")
        .content("[{\n"
            + "    \"birthDate\": \"1978-12-22\",\n"
            + "    \"email\": \"alex2021@gmail.com\",\n"
            + "    \"firstName\": \"Alexey\",\n"
            + "    \"gender\": \"Female\",\n"
            + "    \"lastName\": \"Grinkov\",\n"
            + "    \"phoneNumber\": \"155-846-2959\",\n"
            + "    \"active\": true\n"
            + "  }]"))
        .andExpect(status().isCreated());

    MvcResult result = mockMvc.perform(get("/clients").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andReturn();
    System.out.println(result.getResponse().getContentAsString());
    /*this.mockMvc.perform(delete("/clients").headers(header)
        .contentType("application/json")
        .content("[{\n"
            + "    \"birthDate\": \"1978-12-22\",\n"
            + "    \"email\": \"alex2021@gmail.com\",\n"
            + "    \"firstName\": \"Alexey\",\n"
            + "    \"gender\": \"Female\",\n"
            + "    \"lastName\": \"Grinkov\",\n"
            + "    \"phoneNumber\": \"155-846-2959\",\n"
            + "    \"active\": true\n"
            + "  }]"));*/

  }

}
