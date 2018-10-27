package com.danit.repositories;

import com.danit.models.Role;
import com.danit.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    private List<User> users = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 100; i++) {
            users.add(new User("testUser" + String.valueOf(i), "123", Arrays.asList(new Role("test_role"))));
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
        /*userRepository.saveAll(users);
        List<User> newUsers = userRepository.findAll();
        assertEquals(users.size(), newUsers.size());*/
    }

}