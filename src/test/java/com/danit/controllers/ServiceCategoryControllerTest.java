package com.danit.controllers;

import com.danit.Application;
import com.danit.TestUtils;
import com.danit.dto.ServiceCategoryDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.facades.ServiceCategoryFacade;
import com.danit.models.Service;
import com.danit.models.ServiceCategory;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.ServiceCategoryRepository;
import com.danit.repositories.ServiceRepository;
import com.danit.services.ServiceCategoryService;
import com.danit.services.ServicesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jayway.jsonpath.JsonPath;
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

import java.util.LinkedHashMap;

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
public class ServiceCategoryControllerTest {

  private final static String url = "/service_categories";

  @Autowired
  TestUtils testUtils;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  ServiceCategoryService serviceCategoryService;
  @Autowired
  ServiceCategoryFacade serviceCategoryFacade;
  @Autowired
  ServiceCategoryRepository serviceCategoryRepository;
  @Autowired
  ServiceRepository serviceRepository;
  @Autowired
  ServicesService servicesService;
  HttpHeaders header;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestRestTemplate template;

  @Before
  public void setHeader() {
    header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
  }

  @Test
  public void createOneServiceCategoryWithExistingServices() throws Exception {


    ServiceCategory serviceCategory = new ServiceCategory();
    serviceCategory.setTitle("New Service Category");

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(serviceCategory);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("[" + json + "]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
    Long id = new Long(String.valueOf(object.get("id")));

    mockMvc.perform(put(url + "/" + id + "/services").headers(header)
        .contentType("application/json")
        .content("[\n" +
            "    {\n" +
            "        \"id\": 17\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 18\n" +
            "    }\n" +
            "]"))
        .andExpect(status().isOk());

    ServiceCategory createdServiceCategory = serviceCategoryService.getEntityById(id);
    System.out.println(createdServiceCategory.getServices());


    assertEquals(17L, (long) createdServiceCategory.getServices().get(0).getId());
    assertEquals("New Service Category", createdServiceCategory.getTitle());

  }

  @Test
  public void getAllServiceCategories() throws Exception {
    long serviceCategoriesQty = serviceCategoryRepository.count();

    mockMvc.perform(get(url).headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(serviceCategoriesQty));
  }

  @Test
  public void getAllServiceCategoriesShort() throws Exception {
    long serviceCategoriesQty = serviceCategoryRepository.count();

    mockMvc.perform(get(url + "/short").headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(serviceCategoriesQty));
  }

  @Test
  public void getAllServiceCategoriesIds() throws Exception {
    long serviceCategoriesQty = serviceCategoryRepository.count();

    mockMvc.perform(get(url + "/ids").headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(serviceCategoriesQty));
  }


  @Test
  public void getServiceCategoryByExistingId() throws Exception {

    Long existingId = 1L;
    String responseJson = mockMvc.perform(get(url + "/" + existingId).headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(1))
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data");
    Long id = new Long(String.valueOf(object.get("id")));

    assertEquals((long) existingId, (long) id);
  }


  @Test
  public void getContractByNotExistingId() throws Exception {
    long notExistingId = serviceCategoryRepository.count() + 1;

    mockMvc.perform(get(url + "/" + notExistingId).headers(header))
        .andExpect(status().isNotFound());
  }

  @Test
  public void updateExistingServiceCategoriesByPartialFields() throws Exception {
    long id = 1L;
    ServiceCategory entityById = serviceCategoryService.getEntityById(id);
    int size = entityById.getServices().size();

    this.mockMvc.perform(put(url).headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\":" + id + ",\n" +
            "    \"title\": \"New\"\n" +
            "  }]"))
        .andExpect(status().isOk());

    this.mockMvc.perform(put(url + "/1/services").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\":" + 15 + "\n" +
            "  }]"))
        .andExpect(status().isOk());

    ServiceCategoryDto serviceCategoryDto = serviceCategoryFacade.getEntityById(id);


    assertEquals("New", serviceCategoryDto.getTitle());
    assertEquals(size + 1, serviceCategoryDto.getServices().size());
  }


  @Test(expected = EntityNotFoundException.class)
  public void deleteServiceCategory() throws Exception {
    long id1 = 1L;
    long id2 = 2L;

    Service service = serviceCategoryService.getEntityById(id1).getServices().get(0);

    mockMvc.perform(delete(url).headers(header)
        .contentType("application/json")
        .content("[\n" +
            "    {\n" +
            "        \"id\": " + id1 + "\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": " + id2 + "\n" +
            "    }\n" +
            "]"))
        .andExpect(status().isOk());


    mockMvc.perform(get(url + "/" + id1).headers(header)).andExpect(status().isNotFound());
    mockMvc.perform(get(url + "/" + id2).headers(header)).andExpect(status().isNotFound());
    mockMvc.perform(put(url + "/" + id1 + "/service/" + service.getId()).headers(header))
        .andExpect(status().isNotFound());

    mockMvc.perform(get("/services/" + service.getId()).headers(header)).andExpect(status().isOk());


    serviceCategoryService.getEntityById(id1);
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteServiceCategoryById() throws Exception {
    long id1 = 1L;

    mockMvc.perform(delete(url + "/" + id1).headers(header))
        .andExpect(status().isOk());

    mockMvc.perform(get(url + "/" + id1).headers(header)).andExpect(status().isNotFound());

    serviceCategoryService.getEntityById(id1);
  }

  @Test
  public void assignExistingServiceToServiceCategory() throws Exception {
    long scId = 5L;
    long sId = 7L;

    int size = serviceCategoryService.getEntityById(scId).getServices().size();

    mockMvc.perform(put(url + "/" + scId + "/service/" + sId).headers(header))
        .andExpect(status().isOk());

    mockMvc.perform(get(url + "/" + scId + "/services").headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(size + 1));

  }

  @Test
  public void deleteExistingServiceFromServiceCategory() throws Exception {
    long scId = 5L;
    long sId = serviceCategoryService.getEntityById(scId).getServices().get(0).getId();

    int size = serviceCategoryService.getEntityById(scId).getServices().size();

    mockMvc.perform(delete(url + "/" + scId + "/service/" + sId).headers(header))
        .andExpect(status().isOk());

    mockMvc.perform(get(url + "/" + scId + "/services").headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(size - 1));

    mockMvc.perform(get("/services/" + sId).headers(header))
        .andExpect(status().isOk());
  }

}
