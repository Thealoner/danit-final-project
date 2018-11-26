package com.danit.controllers.employee;

import com.danit.TestUtils;
import com.danit.models.UserRolesEnum;
import com.danit.models.employee.Department;
import com.danit.services.employee.DepartmentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DepartmentControllerTest {


  @Autowired
  TestUtils testUtils;
  @Autowired
  DepartmentService departmentService;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;

  private static final String url = "/department";


  @Test
  public void isOkWhenAdminAccess() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc
        .perform(get(url).headers(header))
        .andExpect(status().isOk());
  }

  @Test
  public void getAllDepartments() throws Exception {
    int currentQuant = departmentService.getDepartmentQuant();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test department\",\n"
            + "    \"sname\": \"Test department\",\n"
            + "    \"dateFrom\": \"1978-12-22\",\n"
            + "    \"dateTo\": \"2200-12-31\",\n"
            + "    \"hierLevel\": 1,\n"
            + "    \"companyid\": 1,\n"
            + "    \"sortPosition\": \"0001\"\n"
            + "  }"))
        .andExpect(status().isOk());

    mockMvc.perform(get(url).headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(currentQuant + 1)));
  }

  @Test
  public void updateDepartmentTest() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test department\",\n"
            + "    \"sname\": \"Test department\",\n"
            + "    \"dateFrom\": \"1978-12-22\",\n"
            + "    \"dateTo\": \"2200-12-31\",\n"
            + "    \"hierLevel\": 1,\n"
            + "    \"companyid\": 1,\n"
            + "    \"sortPosition\": \"0001\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Department actualObj = mapper.readValue(responseJson, new TypeReference<Department>() {
    });
    long createdId = actualObj.getId();
    System.out.println(actualObj);
    System.out.println(createdId);

    responseJson = mockMvc.perform(put(url + "/" + createdId).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"id\": " + createdId + ",\n"
            + "    \"name\": \"Test department2\",\n"
            + "    \"sname\": \"Test department2\",\n"
            + "    \"dateFrom\": \"2000-02-01\",\n"
            + "    \"dateTo\": \"2200-12-31\",\n"
            + "    \"hierLevel\": 1\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    actualObj = mapper.readValue(responseJson, new TypeReference<Department>() {
    });
    System.out.println(actualObj);
    assertEquals("Test department2", actualObj.getName());

  }

  @Test
  public void createDepartmentTest() throws Exception {
    int currentQuant = departmentService.getDepartmentQuant();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test department\",\n"
            + "    \"sname\": \"Test department\",\n"
            + "    \"dateFrom\": \"1978-12-22\",\n"
            + "    \"dateTo\": \"2200-12-31\",\n"
            + "    \"hierLevel\": 1,\n"
            + "    \"companyid\": 1,\n"
            + "    \"sortPosition\": \"0001\"\n"
            + "  }"))
        .andExpect(status().isOk());

    mockMvc.perform(get(url).headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(currentQuant + 1)));
  }

  @Test
  public void deleteClientById() throws Exception {
    int currentQuant= departmentService.getDepartmentQuant();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test department\",\n"
            + "    \"sname\": \"Test department\",\n"
            + "    \"dateFrom\": \"1978-12-22\",\n"
            + "    \"dateTo\": \"2200-12-31\",\n"
            + "    \"hierLevel\": 1,\n"
            + "    \"companyid\": 1,\n"
            + "    \"sortPosition\": \"0001\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Department actualObj = mapper.readValue(responseJson, new TypeReference<Department>() {
    });
    long createdId = actualObj.getId();

    assertEquals(currentQuant + 1, departmentService.getDepartmentQuant());

    mockMvc.perform(delete(url+"/" + createdId).headers(header))
        .andExpect(status().isOk());

    assertEquals(currentQuant, departmentService.getAllDepartments().size());

  }

}