package ua.com.danit.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.danit.models.User;
import ua.com.danit.models.UserRoles;
import ua.com.danit.models.UserRolesEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserRolesRepository userRolesRepository;

  private List<User> users = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    Set<UserRoles> userRolesSet = new HashSet<>();
    userRolesSet.add(new UserRoles(UserRolesEnum.USER));
    userRolesSet.add(new UserRoles(UserRolesEnum.ADMIN));
    userRolesSet.add(new UserRoles(UserRolesEnum.TEST));
    userRolesRepository.saveAll(userRolesSet);
    for (int i = 0; i < 100; i++) {
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
    List<User> newUsers = userRepository.findAll();
    assertEquals(users.size(), newUsers.size());
  }

  @Test
  public void removeOneUsers() {
    userRepository.saveAll(users);
    userRepository.delete(users.get(0));
    List<User> newUsers = userRepository.findAll();
    assertEquals(users.size() - 1, newUsers.size());
  }

  @Test
  public void removeAllUsers() {
    userRepository.deleteAll();
    List<User> newUsers = userRepository.findAll();
    assertEquals(0, newUsers.size());
  }
}