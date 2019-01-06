package com.danit.controllers;


import com.danit.Application;
import com.danit.TestUtils;
import com.danit.models.Service;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.ServiceCategoryRepository;
import com.danit.repositories.ServiceRepository;
import com.danit.services.ServicesService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
    Application.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ServicesControllerTest {

  private final static String url = "/services";
  private static boolean dbInit = false;
  @Autowired
  TestUtils testUtils;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  ServicesService servicesService;
  @Autowired
  ServiceRepository serviceRepository;
  @Autowired
  ServiceCategoryRepository serviceCategoryRepository;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestRestTemplate template;
  private HttpHeaders headers;
  private ObjectWriter ow;

  @Before
  public void setHeaders() throws Exception {
    headers = testUtils.getHeader(template, UserRolesEnum.USER);
    ow = objectMapper.writer();

    long count = serviceRepository.count();

    List<Service> services = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      Service service = new Service();
      service.setTitle("TestServiceTitle" + (i+1));
      services.add(service);
    }


    String json = ow.writeValueAsString(services);

    this.mockMvc.perform(post(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());

    assertEquals(serviceRepository.count(), count + 10);
  }

  @Test
  public void createServices() throws Exception {
    long count = serviceRepository.count();

    List<Service> services = new ArrayList<>(10);
    for (int i = 10; i < 20; i++) {
      Service service = new Service();
      service.setTitle("TestServiceTitle " + (i+1));
      services.add(service);
    }


    String json = ow.writeValueAsString(services);

    this.mockMvc.perform(post(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());

    assertEquals(serviceRepository.count(), count + 10);

  }

  @Test
  public void  readServices() throws Exception {
    long count = serviceRepository.count();

    this.mockMvc.perform(get(url).headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(count));

    this.mockMvc.perform(get(url + "/short").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(count));

    this.mockMvc.perform(get(url + "/ids").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(count));

    this.mockMvc.perform(get(url + "/1").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(1));

  }

  @Test
  public void updateServices() throws Exception {

    this.mockMvc.perform(put(url).headers(headers)
        .contentType("application/json")
        .content("[" +
            "{" +
            "  \"id\":  2," +
            "  \"title\": \"Updated title\" " +
            "}]" ))
        .andExpect(status().isOk());

    Service service = servicesService.getEntityById(2);

    assertEquals(service.getTitle(), "Updated title");

  }

  @Test
  public void deleteServices() throws Exception {

    long count = serviceRepository.count();

    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[" +
            "{" +
            "  \"id\":  4" +
            "}]" ))
        .andExpect(status().isOk());


    this.mockMvc.perform(delete(url + "/5").headers(headers))
        .andExpect(status().isOk());

    this.mockMvc.perform(get(url + "/5").headers(headers))
        .andExpect(status().isNotFound());


    assertEquals(count - 2, serviceRepository.count());
  }

  @Test
  public void readServicesServiceCategories() throws Exception {

    this.mockMvc.perform(get(url+"/1/service_category").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(2));

  }

  @Test
  public void testPageable() throws Exception {
    int elementsPerPage = 3;
    long count = serviceRepository.count();
    int numberOfPages = (int) Math.ceil((double) count / (double) elementsPerPage);

    long currentPage = 1;
    long elementsLeft = count - (currentPage - 1) * elementsPerPage;

    while (elementsLeft > 0) {
      long currentElements = (elementsLeft > elementsPerPage) ? elementsPerPage : elementsLeft;
      String responseJson = mockMvc.perform(get(url + "?page=" + currentPage + "&size=" + elementsPerPage).headers(headers))
          .andExpect(status().isOk())
          .andExpect(content()
              .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.meta.currentPage").value(currentPage))
          .andExpect(jsonPath("$.meta.pagesTotal").value(numberOfPages))
          .andExpect(jsonPath("$.meta.elementsPerPage").value(elementsPerPage))
          .andExpect(jsonPath("$.meta.currentElements").value(currentElements))
          .andExpect(jsonPath("$.meta.totalElements").value(count))
          .andReturn().getResponse().getContentAsString();

      JSONObject obj = new JSONObject(responseJson);
      String pageDataJson = obj.getString("data");
      List<Service> updatedServices = objectMapper.readValue(pageDataJson, new TypeReference<List<Service>>() {
      });
      Assert.assertEquals(currentElements, updatedServices.size());
      currentPage++;
      elementsLeft = count - (currentPage - 1) * elementsPerPage;
    }
  }

  @Test
  public void testSearchAndFilterAndPagination() throws Exception {

    //Search by mask in all fields
    mockMvc.perform(get(url + "?search=TestServiceTitle&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(20));
    //Search by mask in particular field
    mockMvc.perform(get(url + "?title=TestServiceTitle&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(20));
    //Search with equal
    mockMvc.perform(get(url + "?search=TestServiceTitle&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));
    //Field search with equal
    mockMvc.perform(get(url + "?title=TestServiceTitle&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));

    //search in all fields w/o equal
    mockMvc.perform(get(url + "?search=TestServiceTitle1&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(6));

    //pagination test
    mockMvc.perform(get(url + "?title=TestServiceTitle&page=1&size=5&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentPage").value(1))
        .andExpect(jsonPath("$.meta.elementsPerPage").value(5))
        .andExpect(jsonPath("$.meta.currentElements").value(5));
  }

}
