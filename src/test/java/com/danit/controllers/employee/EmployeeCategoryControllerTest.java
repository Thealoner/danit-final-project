package com.danit.controllers.employee;

import com.danit.TestUtils;
import com.danit.models.UserRolesEnum;
import com.danit.models.employee.EmployeeCategory;
import com.danit.services.employee.EmployeeCategoryService;
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
public class EmployeeCategoryControllerTest {


  private static final String url = "/employee_category";
  @Autowired
  TestUtils testUtils;
  @Autowired
  EmployeeCategoryService employeeCategoryService;
  @Autowired
  private TestRestTemplate template;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void isOkWhenAdminAccess() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc
        .perform(get(url).headers(header))
        .andExpect(status().isOk());
  }

  @Test
  public void getAllEmployeeCategorys() throws Exception {
    int currentQty = employeeCategoryService.getEmployeeCategoryQty();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test employeeCategory\"\n"
            + "  }"))
        .andExpect(status().isOk());

    mockMvc.perform(get(url).headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(currentQty + 1)));
  }

  @Test
  public void updateEmployeeCategoryTest() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test employeeCategory\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    EmployeeCategory actualObj = mapper.readValue(responseJson, new TypeReference<EmployeeCategory>() {
    });
    long createdId = actualObj.getId();
    System.out.println(actualObj);
    System.out.println(createdId);

    responseJson = mockMvc.perform(put(url + "/" + createdId).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"id\": " + createdId + ",\n"
            + "    \"name\": \"Test employeeCategory2\"\n"

            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    actualObj = mapper.readValue(responseJson, new TypeReference<EmployeeCategory>() {
    });
    System.out.println(actualObj);
    assertEquals("Test employeeCategory2", actualObj.getName());

  }

  @Test
  public void createEmployeeCategoryTest() throws Exception {
    int currentQty = employeeCategoryService.getEmployeeCategoryQty();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test employeeCategory\"\n"
            + "  }"))
        .andExpect(status().isOk());

    mockMvc.perform(get(url).headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(currentQty + 1)));
  }

  @Test
  public void deleteEmployeeCategoryById() throws Exception {
    int currentQty = employeeCategoryService.getEmployeeCategoryQty();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("{\n"
            + "    \"name\": \"Test employeeCategory\"\n"
            + "  }"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    EmployeeCategory actualObj = mapper.readValue(responseJson, new TypeReference<EmployeeCategory>() {
    });
    long createdId = actualObj.getId();

    assertEquals(currentQty + 1, employeeCategoryService.getEmployeeCategoryQty());

    mockMvc.perform(delete(url + "/" + createdId).headers(header))
        .andExpect(status().isOk());

    assertEquals(currentQty, employeeCategoryService.getAllEmployeeCategories().size());

  }

  @Test
  public void expect404WhenNoDataFoundEmployeeCategory() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(get(url + "/0").headers(header))
        .andExpect(status().isNotFound());
  }

  @Test
  public void expect500WhenDeleteNonexistentEmployeeCategory() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete(url + "/0").headers(header))
        .andExpect(status().is(500));
  }


}