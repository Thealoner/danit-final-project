package com.danit.controllers.employee;

import com.danit.TestUtils;
import com.danit.services.ClientService;
import com.danit.services.employee.PositionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PositionControllerTest {

  @Autowired
  TestUtils testUtils;
  @Autowired
  PositionService positionService;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void retrieveAllPost() {
  }

  @Test
  public void retrievePost() {
  }

  @Test
  public void deletePost() {
  }

  @Test
  public void createPost() {
  }

  @Test
  public void updatePosition() {
  }
}