package com.danit.controllers;

import com.danit.Application;
import com.danit.TestUtils;
import com.danit.dto.ContractDto;
import com.danit.dto.ServiceCategoryDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.facades.ContractFacade;
import com.danit.facades.ServiceCategoryFacade;
import com.danit.facades.ServiceFacade;
import com.danit.models.Contract;
import com.danit.models.Service;
import com.danit.models.ServiceCategory;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.ContractRepository;
import com.danit.repositories.ServiceCategoryRepository;
import com.danit.repositories.ServiceRepository;
import com.danit.services.ClientService;
import com.danit.services.ContractService;
import com.danit.services.PaketService;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
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
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestRestTemplate template;

  HttpHeaders header;

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

    mockMvc.perform(put(url + "/"+id+"/services").headers(header)
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

    this.mockMvc.perform(put(url+"/1/services").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\":" + 15 + "\n" +
            "  }]"))
        .andExpect(status().isOk());

    ServiceCategoryDto serviceCategoryDto = serviceCategoryFacade.getEntityById(id);


    assertEquals("New", serviceCategoryDto.getTitle());
    assertEquals(size+1, serviceCategoryDto.getServices().size());
  }


//  @Test(expected = EntityNotFoundException.class)
//  public void deleteContractById() throws Exception {
//    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
//
//    Contract contract = new Contract();
//    contract.setClientId(27L);
//    contract.setPackageId(2L);
//
//    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//    String json = ow.writeValueAsString(contract);
//
//    String responseJson = this.mockMvc.perform(post(url).headers(header)
//        .contentType("application/json")
//        .content("[" + json + "]"))
//        .andExpect(status().isOk())
//        .andReturn().getResponse().getContentAsString();
//
//    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
//    Long id = new Long(String.valueOf(object.get("id")));
//
//    mockMvc.perform(delete(url + "/" + id.toString()).headers(header))
//        .andExpect(status().isOk());
//
//    mockMvc.perform(get(url + "/" + id.toString()).headers(header));
//  }
/*
  @Test(expected = EntityNotFoundException.class)
  public void deleteContracts() throws Exception {
    Contract contract1 = new Contract();
    Contract contract2 = new Contract();

    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json1 = ow.writeValueAsString(contract1);
    String json2 = ow.writeValueAsString(contract2);

    String responseJson = mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("[" + json1 + "," + json2 + "]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object1 = JsonPath.read(responseJson, "$.data[0]");
    LinkedHashMap object2 = JsonPath.read(responseJson, "$.data[1]");

    String json3 = ow.writeValueAsString(object1);
    String json4 = ow.writeValueAsString(object2);

    Long id1 = new Long(String.valueOf(object1.get("id")));
    Long id2 = new Long(String.valueOf(object2.get("id")));

    mockMvc.perform(delete(url).headers(header)
        .contentType("application/json")
        .content("[" + json3 + "," + json4 + "]"))
        .andExpect(status().isOk());


    mockMvc.perform(get(url + "/" + id1).headers(header));
    mockMvc.perform(get(url + "/" + id2).headers(header));

  }
  */
  @Test
  public void saveServiceCategoryIfNotExists() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.ADMIN);
    mockMvc.perform(post("/service_categories").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"title\": \"SPA\",\n" +
            "    \"services\": [],\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk());
  }

  @Test
  public void updateServiceIfExists() throws Exception {
    long numberOfServiceCategories = serviceRepository.count();
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(post("/service_categories").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"title\": \"BAR\",\n" +
            "    \"services\": [],\n" +
            "    \"active\": false\n" +
            "}]"))
        .andExpect(status().isOk());


    mockMvc.perform(put("/service_categories").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\": 1004,\n" +
            "    \"title\": \"BAR\",\n" +
            "    \"active\": true\n" +
            "}]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/service_categories").headers(header))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[?(@.id == 1004)].active", hasItem(true)));

  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteServicesIfExists() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    mockMvc.perform(delete("/service_categories").headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\": 1001,\n" +
            "    \"title\": \"SPA\",\n" +
            "    \"services\": [\n" +
            "      {\n" +
            "        \"id\": 1001,\n" +
            "        \"title\": \"Total Body Workout\",\n" +
            "        \"price\": \"200\",\n" +
            "        \"cost\": \"200\",\n" +
            "        \"unit\": \"min\",\n" +
            "        \"unitsNumber\": \"55\",\n" +
            "        \"active\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/service_categories/1001").headers(header));

  }

}
