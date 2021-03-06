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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DepartmentControllerTest {


  private static final String url = "/department";
  @Autowired
  TestUtils testUtils;
  @Autowired
  DepartmentService departmentService;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void isOkWhenAdminAccess() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc
        .perform(get("/department").headers(header))
        .andExpect(status().isOk());
  }

  @Test
  public void getAllDepartments() throws Exception {
    int currentQty = departmentService.getDepartmentQty();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test department\",\n"
            + "    \"shortName\": \"Test department\",\n"
            + "    \"dateFrom\": \"22-12-1978\",\n"
            + "    \"dateTo\": \"31-12-2200\",\n"
            + "    \"hierLevel\": 1,\n"
            + "    \"companyid\": 1,\n"
            + "    \"sortPosition\": \"0001\"\n"
            + "  }"))
        .andExpect(status().isOk());

    mockMvc.perform(get(url).headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(currentQty + 1)));
  }

  @Test
  public void updateDepartmentTest() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test department\",\n"
            + "    \"shortName\": \"Test department\",\n"
            + "    \"dateFrom\": \"22-12-1978\",\n"
            + "    \"dateTo\": \"31-12-2200\",\n"
            + "    \"hierLevel\": 1,\n"
            + "    \"companyid\": 1,\n"
            + "    \"sortPosition\": \"0001\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    Department actualObj = objectMapper.readValue(responseJson, new TypeReference<Department>() {
    });
    long createdId = actualObj.getId();
    System.out.println(actualObj);
    System.out.println(createdId);

    responseJson = mockMvc.perform(put(url + "/" + createdId).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"id\": " + createdId + ",\n"
            + "    \"name\": \"Test department2\",\n"
            + "    \"shortName\": \"Test department2\",\n"
            + "    \"dateFrom\": \"01-02-2000\",\n"
            + "    \"dateTo\": \"31-12-2200\",\n"
            + "    \"hierLevel\": 1\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    actualObj = objectMapper.readValue(responseJson, new TypeReference<Department>() {
    });
    System.out.println(actualObj);
    assertEquals("Test department2", actualObj.getName());

  }

  @Test
  public void createDepartmentTest() throws Exception {
    int currentQty = departmentService.getDepartmentQty();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test department\",\n"
            + "    \"shortName\": \"Test department\",\n"
            + "    \"dateFrom\": \"22-12-1978\",\n"
            + "    \"dateTo\": \"31-12-2200\",\n"
            + "    \"hierLevel\": 1,\n"
            + "    \"companyid\": 1,\n"
            + "    \"sortPosition\": \"0001\"\n"
            + "  }"))
        .andExpect(status().isOk());

    mockMvc.perform(get(url).headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(currentQty + 1)));
  }

  @Test
  public void deleteDepartmentById() throws Exception {
    int currentQty = departmentService.getDepartmentQty();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test department\",\n"
            + "    \"shortName\": \"Test department\",\n"
            + "    \"dateFrom\": \"22-12-1978\",\n"
            + "    \"dateTo\": \"31-12-2200\",\n"
            + "    \"hierLevel\": 1,\n"
            + "    \"companyid\": 1,\n"
            + "    \"sortPosition\": \"0001\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    Department actualObj = objectMapper.readValue(responseJson, new TypeReference<Department>() {
    });
    long createdId = actualObj.getId();

    assertEquals(currentQty + 1, departmentService.getDepartmentQty());

    mockMvc.perform(delete(url + "/" + createdId).headers(header))
        .andExpect(status().isOk());

    assertEquals(currentQty, departmentService.getAllDepartments().size());

  }

  @Test
  public void expect404WhenNoDataFoundDepartment() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(get(url + "/0").headers(header))
        .andExpect(status().isNotFound());
  }

  @Test
  public void expect500WhenDeleteNonexistentDepartment() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete(url + "/0").headers(header))
        .andExpect(status().is(500));
  }


}