package com.danit.controllers;

import com.danit.Application;
import com.danit.TestUtils;
import com.danit.models.Contract;
import com.danit.models.Paket;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.PaketRepository;
import com.danit.services.ContractService;
import com.danit.services.PaketService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jayway.jsonpath.JsonPath;
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
import java.util.LinkedHashMap;
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
public class PaketControllerTest {

  private final static String url = "/pakets";
  private static boolean dbInit = false;
  @Autowired
  TestUtils testUtils;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  PaketRepository paketRepository;
  @Autowired
  PaketService paketService;
  @Autowired
  ContractService contractService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestRestTemplate template;
  private HttpHeaders headers;

  @Before
  public void createPakets() throws Exception {

    headers = testUtils.getHeader(template, UserRolesEnum.USER);

    if (dbInit) {
      return;
    }
    dbInit = true;

    //clientRepository.deleteAll();
    long numberOfEntities = paketRepository.count();

    List<Paket> pakets = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      Paket paket = new Paket();
      paket.setTitle("TestPaketTitle" + (i+1));
      pakets.add(paket);
    }


    for (int i = 1; i < 11; i++) {
      Paket paket = new Paket();
      paket.setTitle("SearchTestPaketTitle" + i);
      paket.setActive(true);
      pakets.add(paket);
    }

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(pakets);

    this.mockMvc.perform(post(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());

    assertEquals(paketRepository.count(), numberOfEntities + 20);

  }

  @Test
  public void createPaketAndAssignItToContract() throws Exception {

    Paket paket = new Paket();
    paket.setTitle("NewTestPaketTitle");

    long numberOfEntities = paketRepository.count();

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(paket);

    String responseJson = this.mockMvc.perform(post(url).headers(headers)
        .contentType("application/json")
        .content("[" + json + "]"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object = JsonPath.read(responseJson, "$.data[0]");
    Long id = new Long(String.valueOf(object.get("id")));

    assertEquals(paketRepository.count(), numberOfEntities + 1);

    List<Contract> contracts = new ArrayList<>(2);
    for (int i = 0; i < 2; i++) {
      Contract contract = new Contract();
      contracts.add(contract);
    }

    String json1 = ow.writeValueAsString(contracts);

    String responseJson1 = this.mockMvc.perform(post("/contracts").headers(headers)
        .contentType("application/json")
        .content(json1))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object1 = JsonPath.read(responseJson1, "$.data[0]");
    Long id1 = new Long(String.valueOf(object1.get("id")));

    LinkedHashMap object2 = JsonPath.read(responseJson1, "$.data[1]");
    Long id2 = new Long(String.valueOf(object2.get("id")));

    this.mockMvc.perform(put("/contracts/" + id1 + "/paket/" + id).headers(headers))
        .andExpect(status().isOk());

    this.mockMvc.perform(put("/contracts/" + id2 + "/paket/" + id).headers(headers))
        .andExpect(status().isOk());

    Paket paket1 = paketService.getEntityById(id);

    Contract contract1 = contractService.getEntityById(id1);
    Contract contract2 = contractService.getEntityById(id2);

    assertEquals("NewTestPaketTitle", paket.getTitle());

    assertEquals(2, paket1.getContracts().size());

    assertEquals(id, contract1.getPaket().getId());
    assertEquals(id, contract2.getPaket().getId());

  }

  @Test
  public void readPakets() throws Exception {

    long count = paketRepository.count();

    this.mockMvc.perform(get(url).headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(count));

    this.mockMvc.perform(get(url + "/short").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(count));

    this.mockMvc.perform(get(url + "/ids").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(count));

    this.mockMvc.perform(get(url + "/" + 1).headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.totalElements").value(1));

  }

  @Test
  public void updatePakets() throws Exception {

    this.mockMvc.perform(put(url).headers(headers)
        .contentType("application/json")
        .content("[" +
            "{" +
            "  \"id\":  1001," +
            "  \"title\": \"Updated title\" " +
            "}," +
            "{" +
            "  \"id\":  1002," +
            "  \"title\": \"Updated title\" " +
            "}]" ))
        .andExpect(status().isOk());


    List<Contract> contracts = new ArrayList<>(2);
    for (int i = 0; i < 2; i++) {
      Contract contract = new Contract();
      contracts.add(contract);
    }

    ObjectWriter ow = objectMapper.writer();
    String json1 = ow.writeValueAsString(contracts);

    String responseJson1 = this.mockMvc.perform(post("/contracts").headers(headers)
        .contentType("application/json")
        .content(json1))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    LinkedHashMap object1 = JsonPath.read(responseJson1, "$.data[0]");
    Long id1 = new Long(String.valueOf(object1.get("id")));

    LinkedHashMap object2 = JsonPath.read(responseJson1, "$.data[1]");
    Long id2 = new Long(String.valueOf(object2.get("id")));

    long id = 1001L;

    this.mockMvc.perform(put("/contracts/" + id1 + "/paket/" + id).headers(headers))
        .andExpect(status().isOk());

    this.mockMvc.perform(put("/contracts/" + id2 + "/paket/" + id).headers(headers))
        .andExpect(status().isOk());

    Paket paket1001 = paketService.getEntityById(1001);
    Paket paket1002 = paketService.getEntityById(1002);
    Contract contract1001 = contractService.getEntityById(id1);

    assertEquals("Updated title", paket1001.getTitle());
    assertEquals("Updated title", paket1002.getTitle());
    assertEquals(2, paket1001.getContracts().size());
    assertEquals(id, contract1001.getPaket().getId().longValue());

    this.mockMvc.perform(delete("/contracts/" + id1 + "/paket/" + id).headers(headers))
        .andExpect(status().isOk());

    Paket paket = paketService.getEntityById(1001);

    assertEquals(1, paket.getContracts().size());

  }

  @Test
  public void deletePakets() throws Exception {

    long count = paketRepository.count();

    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[" +
            "{" +
            "  \"id\":  1001" +
            "}," +
            "{" +
            "  \"id\":  1002" +
            "}]" ))
        .andExpect(status().isOk());

    this.mockMvc.perform(delete(url + "/1003").headers(headers))
        .andExpect(status().isOk());

    this.mockMvc.perform(get(url + "/1003").headers(headers))
        .andExpect(status().isNotFound());

    assertEquals(count-3L, paketRepository.count());

  }

  @Test
  public void testPageable() throws Exception {
    int elementsPerPage = 3;
    long count = paketRepository.count();
    int numberOfPages = (int) Math.ceil((double) count / (double) elementsPerPage);

    long currentPage = 1;
    long elementsLeft = count - (currentPage - 1) * elementsPerPage;

    while (elementsLeft > 0) {
      long currentElements = (elementsLeft > elementsPerPage) ? elementsPerPage : elementsLeft;
      String responseJson = mockMvc.perform(get(url + "?page=" + currentPage + "&size=" + elementsPerPage)
          .headers(headers))
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
      List<Paket> updatedPakets = objectMapper.readValue(pageDataJson, new TypeReference<List<Paket>>() {
      });
      Assert.assertEquals(currentElements, updatedPakets.size());
      currentPage++;
      elementsLeft = count - (currentPage - 1) * elementsPerPage;
    }
  }

  @Test
  public void testSearchAndFilterAndPagination() throws Exception {

    //Search by mask in all fields
    mockMvc.perform(get(url + "?search=TestPaketTitle&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(18));
    //Search by mask in particular field
    mockMvc.perform(get(url + "?title=SearchTestPaketTitle&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(10));
    //Search with equal
    mockMvc.perform(get(url + "?search=SearchTestPaketTitle&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));
    //Field search with equal
    mockMvc.perform(get(url + "?title=SearchTestPaketTitle1&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));

  }

}
