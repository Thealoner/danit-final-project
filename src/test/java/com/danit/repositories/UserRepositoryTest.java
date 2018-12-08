package com.danit.repositories;

import com.danit.models.User;
import com.danit.models.UserRoles;
import com.danit.models.UserRolesEnum;
import com.danit.models.auditor.AuditorAwareImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = {AuditorAwareImpl.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserRolesRepository userRolesRepository;

  private List<User> users = new ArrayList<>();

  @Before
  public void setUp() {
    for (int i = 0; i < 30; i++) {
      users.add(new User("testUser" + String.valueOf(i), "123", Arrays.asList(new UserRoles(UserRolesEnum.USER))));
    }
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void findByUsername() {
    userRepository.save(users.get(0));
    User user = userRepository.findByUsername(users.get(0).getUsername());
    assertEquals(users.get(0).getUsername(), user.getUsername());
  }

  @Test
  public void findAllUsers() {
    userRepository.saveAll(users);
    long oldUserQnt = userRepository.count();
    List<User> newUsers = userRepository.findAll();
    assertEquals(oldUserQnt, newUsers.size());
  }

  @Test
  public void removeOneUsers() {
    userRepository.saveAll(users);
    long oldUserQnt = userRepository.count();
    userRepository.delete(users.get(0));

    List<User> newUsers = userRepository.findAll();
    assertEquals(oldUserQnt - 1, newUsers.size());
  }

  @Test
  public void removeAllUsers() {
    userRepository.deleteAll();
    List<User> newUsers = userRepository.findAll();
    assertEquals(0, newUsers.size());
  }
}