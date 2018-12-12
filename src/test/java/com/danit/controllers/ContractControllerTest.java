package com.danit.controllers;

import com.danit.TestUtils;
import com.danit.dto.ContractDto;
import com.danit.facades.ContractFacade;
import com.danit.models.Contract;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.ContractRepositoryBase;
import com.danit.services.ContractService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jayway.jsonpath.JsonPath;
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

import java.util.LinkedHashMap;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContractControllerTest {

  private final static String url = "/contracts";

  @Autowired
  TestUtils testUtils;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestRestTemplate template;

  @Autowired
  ContractFacade contractFacade;

  @Autowired
  ContractRepositoryBase contractRepositoryBase;

  @Autowired
  ContractService contractService;


  @Test
  public void createOneContractWithExistingClientAndExistingPaket() throws Exception{
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    Contract contract = new Contract();
    contract.setClientId(27L);
    contract.setPackageId(2L);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(contract);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("[" + json + "]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
    Long id = new Long(String.valueOf(object.get("id")));
    ContractDto createdContract = contractFacade.getEntityById(id);

    assertEquals(new Long(2), createdContract.getPackageId());
    assertEquals(new Long(27), createdContract.getClientId());

  }

  @Test
  public void getAllContracts() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    long contractQuantity = contractRepositoryBase.count();

    mockMvc.perform(get(url).headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
          .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(contractQuantity));

  }

  @Test
  public void updateExistingContract() throws Exception{
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    Contract contract = new Contract();
    contract.setClientId(27L);
    contract.setPackageId(2L);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(contract);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("[" + json + "]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
    Long id = new Long(String.valueOf(object.get("id")));
    ContractDto createdContract = contractFacade.getEntityById(id);

    assertEquals(new Long(2), createdContract.getPackageId());
    assertEquals(new Long(27), createdContract.getClientId());

    String responseJson1 = this.mockMvc.perform(put(url).headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\":" + id + ",\n" +
            "    \"startDate\": \"01-10-2018\",\n" +
            "    \"endDate\": \"01-10-2019\",\n" +
            "    \"credit\": 10000,\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

//    LinkedHashMap object1 = JsonPath.read(responseJson1, "$.data[0]");
    ContractDto updatedContract = contractFacade.getEntityById(id);

    assertEquals(new Long(2), updatedContract.getPackageId());
    assertEquals(new Long(27), updatedContract.getClientId());
    assertEquals(true, updatedContract.getActive());
    assertEquals(new Float(10000), updatedContract.getCredit());
  }

  @Test
  public void deleteContractById() {
  }

  @Test
  public void deleteContracts() {
  }

}