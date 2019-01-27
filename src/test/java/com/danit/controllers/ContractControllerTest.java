package com.danit.controllers;

import com.danit.Application;
import com.danit.TestUtils;
import com.danit.dto.ContractDto;
import com.danit.facades.ContractFacade;
import com.danit.models.Contract;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.ContractRepository;
import com.danit.services.ClientService;
import com.danit.services.ContractService;
import com.danit.services.PaketService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
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
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContractControllerTest {

  private final static String url = "/contracts";

  @Autowired
  TestUtils testUtils;

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  ContractFacade contractFacade;
  @Autowired
  ContractRepository contractRepository;
  @Autowired
  ClientService clientService;
  @Autowired
  PaketService paketService;
  @Autowired
  ContractService contractService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestRestTemplate template;

  @Test
  public void createOneContractWithExistingClientAndExistingPaketAndWithoutCard() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    Contract contract = new Contract();

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(contract);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("[" + json + "]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
    Long id = new Long(String.valueOf(object.get("id")));

    this.mockMvc.perform(put(url + "/" + id + "/clients/20").headers(header))
        .andExpect(status().isOk());
    this.mockMvc.perform(put(url + "/" + id + "/pakets/1").headers(header))
        .andExpect(status().isOk());

    Contract createdContract = contractService.getEntityById(id);

    assertEquals(1L, (long) createdContract.getPaket().getId());
    assertEquals(20L, (long) createdContract.getClient().getId());

  }

  @Test
  public void createOneContractWithNotExistingClientAndNotExistingPaketAndNotExistingCard() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    Contract contract = new Contract();

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(contract);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("[" + json + "]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
    Long id = new Long(String.valueOf(object.get("id")));

    this.mockMvc.perform(put(url + "/" + id + "/clients/2000").headers(header))
        .andExpect(status().isNotFound());
    this.mockMvc.perform(put(url + "/" + id + "/pakets/2000").headers(header))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllContracts() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    long contractQuantity = contractRepository.count();

    mockMvc.perform(get(url).headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(contractQuantity));
  }

  @Test
  public void getAllContractsShort() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    long contractQuantity = contractRepository.count();

    mockMvc.perform(get(url + "/short").headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(contractQuantity));
  }

  @Test
  public void getAllContractsIds() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    long contractQuantity = contractRepository.count();

    mockMvc.perform(get(url + "/ids").headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(contractQuantity));
  }

  @Test
  public void getContractByExistingId() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    long existingId = 1;

    String responseJson = mockMvc.perform(get(url + "/" + existingId).headers(header))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(1))
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data");
    Long id = new Long(String.valueOf(object.get("id")));

    assertEquals(existingId, (long) id);
  }

  @Test
  public void getContractByNotExistingId() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);
    long notExistingId = contractRepository.count() + 1;

    mockMvc.perform(get(url + "/" + notExistingId).headers(header))
        .andExpect(status().isNotFound());
  }

  @Test
  public void updateExistingContractByPartialFields() throws Exception {
    HttpHeaders header = testUtils.getHeader(template, UserRolesEnum.USER);

    Contract contract = new Contract();

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(contract);

    String responseJson = this.mockMvc.perform(post(url).headers(header)
        .contentType("application/json")
        .content("[" + json + "]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
    Long id = new Long(String.valueOf(object.get("id")));

    this.mockMvc.perform(put(url + "/" + id + "/clients/20").headers(header))
        .andExpect(status().isOk());
    this.mockMvc.perform(put(url + "/" + id + "/pakets/1").headers(header))
        .andExpect(status().isOk());

    ContractDto createdContract = contractFacade.getEntityById(id);

    assertEquals(new Long(1L), createdContract.getPackageId());
    assertEquals(new Long(20L), createdContract.getClientId());

    this.mockMvc.perform(put(url + "/" + id + "/clients/28").headers(header))
        .andExpect(status().isOk());
    this.mockMvc.perform(put(url + "/" + id + "/pakets/3").headers(header))
        .andExpect(status().isOk());

    ContractDto updatedContract = contractFacade.getEntityById(id);

    assertEquals(new Long(3L), updatedContract.getPackageId());
    assertEquals(new Long(28L), updatedContract.getClientId());

    this.mockMvc.perform(put(url).headers(header)
        .contentType("application/json")
        .content("[{\n" +
            "    \"id\":" + id + ",\n" +
            "    \"startDate\": \"01-10-2018\",\n" +
            "    \"endDate\": \"01-10-2019\",\n" +
            "    \"credit\": 10000,\n" +
            "    \"active\": true\n" +
            "  }]"))
        .andExpect(status().isOk());

    updatedContract = contractFacade.getEntityById(id);

    assertEquals(new Long(3L), updatedContract.getPackageId());
    assertEquals(new Long(28L), updatedContract.getClientId());
    assertEquals(true, updatedContract.getActive());
    assertEquals(new Float(10000), updatedContract.getCredit());
  }

  /*
  @Test(expected = EntityNotFoundException.class)
  public void deleteContractById() throws Exception {
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

    mockMvc.perform(delete(url + "/" + id.toString()).headers(header))
        .andExpect(status().isOk());

    mockMvc.perform(get(url + "/" + id.toString()).headers(header))
  }

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

}