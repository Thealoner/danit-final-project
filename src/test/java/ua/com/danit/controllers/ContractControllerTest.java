package ua.com.danit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.danit.TestUtils;
import ua.com.danit.services.ClientService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContractControllerTest {

  @Autowired
  TestUtils testUtils;
  @MockBean
  ClientService clientServiceMock;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestRestTemplate template;

  @Test
  public void getContractById() {
  }

  @Test
  public void getAllContracts() {
  }

  @Test
  public void addContracts() {

  }

  @Test
  public void deleteContractById() {
  }

  @Test
  public void deleteContracts() {
  }

}