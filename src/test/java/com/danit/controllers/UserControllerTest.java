package com.danit.controllers;

import com.danit.Application;
import com.danit.TestUtils;
import com.danit.facades.UserFacade;
import com.danit.models.Client;
import com.danit.models.User;
import com.danit.models.UserRole;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.UserRepository;
import com.danit.services.ClientService;
import com.danit.services.ContractService;
import com.danit.services.PaketService;
import com.danit.services.UserService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
public class UserControllerTest {

  private final static String url = "/users";

  @Autowired
  TestUtils testUtils;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserFacade userFacade;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserService userService;

  @Autowired
  PaketService paketService;

  @Autowired
  ContractService contractService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestRestTemplate template;

  private HttpHeaders headers;

  private UserRole userRole = new UserRole();

  private static boolean dbInit = false;

  @Before
  public void createUsers() throws Exception {
    headers = testUtils.getHeader(template, UserRolesEnum.ADMIN);

    if(dbInit) {
      return;
    }
    dbInit = true;

    //userRepository.deleteAll();
    long numberOfEntities = userService.getNumberOfEntities();

    List<User> users = new ArrayList<>(20);
    userRole.setRole(UserRolesEnum.USER);
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setUsername("TestUserName" + i);
      user.setPassword("password" + i);
      user.setRoles(Arrays.asList(userRole));
      users.add(user);
    }

    for (int i = 1; i < 11; i++) {
      User user = new User();
      user.setUsername("SearchTestUserName" + i);
      user.setPassword("password" + i);
      user.setRoles(Arrays.asList(userRole));
      users.add(user);
    }

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(users);

    this.mockMvc.perform(post(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());
    Assert.assertEquals(userService.getNumberOfEntities(), numberOfEntities + 20);
  }

  @Test
  public void deleteUsersBySeveralMethods() throws Exception {
    long numberOfEntities = userService.getNumberOfEntities();
    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[{\"id\": 1001},{\"id\": 1002},{\"id\": 1003}]"))
        .andExpect(status().isOk());
    Assert.assertEquals(userService.getNumberOfEntities(), numberOfEntities - 3);

    this.mockMvc.perform(delete(url + "/1004").headers(headers))
        .andExpect(status().isOk());

    Assert.assertEquals(userService.getNumberOfEntities(), numberOfEntities - 4);
  }

  public void updateClientData() throws Exception {
    List<User> users = userService.getAllEntities(PageRequest.of(1,
        6,
        new Sort(Sort.Direction.ASC, "id"))).getContent();
    users.forEach(user -> {
      user.setUsername("UpdatedUserName");
      userRole.setRole(UserRolesEnum.ADMIN);
      user.setRoles(Arrays.asList(userRole));
    });

    String json = objectMapper.writer().writeValueAsString(users);
    String responseJson = this.mockMvc.perform(put(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    List<User> updatedUsers = objectMapper.readValue(responseJson, new TypeReference<List<User>>() {
    });
    updatedUsers.forEach(user -> {
      Assert.assertEquals("UpdatedUserName", user.getUsername());
      Assert.assertEquals("ADMIN", user.getRoles().get(0).getRole().name());
    });
  }

  @Test
  public void deleteNonExistingUser() throws Exception {

    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[{\"id\": 1021},{\"id\": 1022},{\"id\": 1023}]"))
        .andExpect(status().isNotFound());

    this.mockMvc.perform(delete(url + "/1021").headers(headers))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllUsers() throws Exception {
    long userQuantity = userService.getNumberOfEntities();

    mockMvc.perform(get(url).headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(userQuantity));
  }

  @Test
  public void getAllUsersShort() throws Exception {
    long userQuantity = userService.getNumberOfEntities();

    mockMvc.perform(get(url + "/short").headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(userQuantity));
  }

  @Test
  public void getAllUsersIds() throws Exception {
    long userQuantity = userService.getNumberOfEntities();

    mockMvc.perform(get(url + "/ids").headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(userQuantity));
  }

  @Test
  public void testPageable() throws Exception {
    int elementsPerPage = 3;
    long numberOfUsers = userService.getNumberOfEntities();
    int numberOfPages = (int) Math.ceil((double) numberOfUsers / (double) elementsPerPage);

    long currentPage = 1;
    long elementsLeft = numberOfUsers - (currentPage - 1) * elementsPerPage;

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
          .andExpect(jsonPath("$.meta.totalElements").value(numberOfUsers))
          .andReturn().getResponse().getContentAsString();

      JSONObject obj = new JSONObject(responseJson);
      String pageDataJson = obj.getString("data");
      List<User> updatedUsers = objectMapper.readValue(pageDataJson, new TypeReference<List<User>>() {
      });
      Assert.assertEquals(currentElements, updatedUsers.size());
      currentPage++;
      elementsLeft = numberOfUsers - (currentPage - 1) * elementsPerPage;
    }
  }

  @Test
  public void testSearchAndFilterAndPagination() throws Exception {

    //Search by mask in all fields
    mockMvc.perform(get(url + "?search=SearchTestUser&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(10));
    //Search by mask in particular field
    mockMvc.perform(get(url + "?firstName=SearchTestUser&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(10));
    //Search with equal
    mockMvc.perform(get(url + "?search=SearchTestUser&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));
    //Field search with equal
    mockMvc.perform(get(url + "?firstName=SearchTestUser&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));

    //search in all fields w/o equal
    mockMvc.perform(get(url + "?search=SearchTestUserName1&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(2));
    //search by field w/o equal
    mockMvc.perform(get(url + "?search=SearchTestUserName1&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(2));
    //search in all fields with equal
    mockMvc.perform(get(url + "?firstName=SearchTestUserName1&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));
    //search by field with equal
    mockMvc.perform(get(url + "?firstName=SearchTestUserName1&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));


    //pagination test
    mockMvc.perform(get(url + "?firstName=SearchTestUserName&page=1&size=5&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentPage").value(1))
        .andExpect(jsonPath("$.meta.pagesTotal").value(2))
        .andExpect(jsonPath("$.meta.elementsPerPage").value(5))
        .andExpect(jsonPath("$.meta.currentElements").value(5));
  }

  @Test
  public void getUserByExistingId() throws Exception {
    String responseJson = mockMvc.perform(get(url + "/" + 1005).headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(1))
        .andReturn().getResponse().getContentAsString();

    JSONObject obj = new JSONObject(responseJson);
    String pageDataJson = obj.getString("data");
    User receivedUser = objectMapper.readValue(pageDataJson, User.class);

    Assert.assertEquals(new Long(1005), receivedUser.getId());
  }

  @Test
  public void getUserByNotExistingId() throws Exception {
    mockMvc.perform(get(url + "/" + 1021).headers(headers))
        .andExpect(status().isNotFound());
  }

}