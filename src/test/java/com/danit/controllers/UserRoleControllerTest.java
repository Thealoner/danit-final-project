package com.danit.controllers;

import com.danit.Application;
import com.danit.TestUtils;
import com.danit.facades.UserRoleFacade;
import com.danit.models.User;
import com.danit.models.UserRole;
import com.danit.models.UserRolesEnum;
import com.danit.repositories.UserRoleRepository;
import com.danit.services.UserRoleService;
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
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.danit.models.UserRolesEnum.ADMIN;
import static com.danit.models.UserRolesEnum.TEST;
import static com.danit.models.UserRolesEnum.USER;
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
public class UserRoleControllerTest {

  private final static String url = "/roles";
  private static boolean dbInit = false;
  @Autowired
  TestUtils testUtils;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  UserRoleFacade userRoleFacade;
  @Autowired
  UserRoleService userRoleService;
  @Autowired
  UserService userService;
  @Autowired
  private UserRoleRepository userRoleRepository;
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestRestTemplate template;
  private HttpHeaders headers;

  @Before
  public void createTestUserRole() throws Exception {
    headers = testUtils.getHeader(template, ADMIN);

    if (dbInit) {
      return;
    }
    dbInit = true;

    long numberOfEntities = userRoleService.getNumberOfEntities();

    List<UserRole> userRoles = new ArrayList<>();

    for(UserRolesEnum role : UserRolesEnum.values()) {
      if(role.equals(ADMIN) || role.equals(USER) || role.equals(TEST)) {
        continue;
      }
      UserRole userRole = new UserRole();
      userRole.setRole(role);
      userRoles.add(userRole);
    }

    ObjectWriter ow = objectMapper.writer();
    String json = ow.writeValueAsString(userRoles);

    this.mockMvc.perform(post(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk());
    Assert.assertEquals(numberOfEntities + UserRolesEnum.values().length - 3, userRoleService.getNumberOfEntities());
  }

  @Test
  public void deleteUserRoleBySeveralMethods() throws Exception {
    long numberOfEntities = userRoleService.getNumberOfEntities();
    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[{\"id\": 1001}]"))
        .andExpect(status().isOk());
    Assert.assertEquals(userRoleService.getNumberOfEntities(), numberOfEntities - 1);

    this.mockMvc.perform(delete(url + "/1002").headers(headers))
        .andExpect(status().isOk());

    Assert.assertEquals(userRoleService.getNumberOfEntities(), numberOfEntities - 2);
  }

  @Test
  public void deleteNonExistingUserRole() throws Exception {

    this.mockMvc.perform(delete(url).headers(headers)
        .contentType("application/json")
        .content("[{\"id\": \"2001\"},{\"id\": \"2002\"}]"))
        .andExpect(status().isNotFound());

    this.mockMvc.perform(delete(url + "/2003").headers(headers))
        .andExpect(status().isNotFound());
  }

  public void saveAndUpdateUserRoleData() throws Exception {
    UserRole userRole = userRoleService.getEntityById(1003L);
    String userRoleName = userRole.getRole().name();
    userRole.setRole(TEST);

    List<UserRole> userRoles = new ArrayList<>();
    userRoles.add(userRole);
    String json = objectMapper.writer().writeValueAsString(userRoles);
    String responseJson = this.mockMvc.perform(put(url).headers(headers)
        .contentType("application/json")
        .content(json))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    List<UserRole> updatedUserRoles = objectMapper.readValue(responseJson, new TypeReference<List<UserRole>>() {
    });
    UserRole userRoleUpdated = updatedUserRoles.get(0);
    String updatedUserRoleName = userRoleUpdated.getRole().name();
    Assert.assertTrue(!userRoleName.equals(updatedUserRoleName));
    Assert.assertTrue(!updatedUserRoleName.equals("TEST"));
  }

  @Test
  public void getAllUserRoles() throws Exception {
    long userQuantity = userRoleService.getNumberOfEntities();

    mockMvc.perform(get(url).headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(userQuantity));
  }

  @Test
  public void getAllUserRolesShort() throws Exception {
    long userQuantity = userRoleService.getNumberOfEntities();

    mockMvc.perform(get(url + "/short").headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(userQuantity));
  }

  @Test
  public void getAllUserRolesIds() throws Exception {
    long userQuantity = userRoleService.getNumberOfEntities();

    mockMvc.perform(get(url + "/ids").headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(userQuantity));
  }

  @Test
  public void testPageable() throws Exception {
    int elementsPerPage = 2;
    long numberOfUsers = userRoleService.getNumberOfEntities();
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
    mockMvc.perform(get(url + "?search=admin&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));
    //Search by mask in particular field
    mockMvc.perform(get(url + "?role=admin&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));
    //Search with equal
    mockMvc.perform(get(url + "?search=admin&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));
    //Field search with equal
    mockMvc.perform(get(url + "?role=admin&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));

    //Search with equal by particular value
    mockMvc.perform(get(url + "?search=adm&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));
    //Field search with equal by particular value
    mockMvc.perform(get(url + "?role=adm&equal=true&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(0));

    //Search w/o equal by particular value
    mockMvc.perform(get(url + "?search=adm&equal=false&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));
    //Field search w/o equal by particular value
    mockMvc.perform(get(url + "?role=adm&equal=false&page=1&size=20").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentElements").value(1));



    //pagination test
    mockMvc.perform(get(url + "?role=admin&page=1&size=5").headers(headers))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.meta.currentPage").value(1))
        .andExpect(jsonPath("$.meta.elementsPerPage").value(5))
        .andExpect(jsonPath("$.meta.currentElements").value(1));
  }

  @Test
  public void getUserRoleByExistingId() throws Exception {
    String responseJson = mockMvc.perform(get(url + "/" + 1).headers(headers))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.meta.totalElements").value(1))
        .andReturn().getResponse().getContentAsString();

    JSONObject obj = new JSONObject(responseJson);
    String pageDataJson = obj.getString("data");
    UserRole receivedUserRole = objectMapper.readValue(pageDataJson, UserRole.class);

    Assert.assertEquals(new Long(1), receivedUserRole.getId());
  }

  @Test
  public void getUserRoleByNotExistingId() throws Exception {
    long userId = 2000;
    mockMvc.perform(get(url + "/" + userId).headers(headers))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testUserRoleMethods() throws Exception {

    mockMvc.perform(get(url + "/" + 1 + "/users").headers(headers))
        .andExpect(status().isOk());

    mockMvc.perform(get(url + "/" + 1 + "/users/short").headers(headers))
        .andExpect(status().isOk());

    mockMvc.perform(get(url + "/" + 1 + "/users/ids").headers(headers))
        .andExpect(status().isOk());
  }

}