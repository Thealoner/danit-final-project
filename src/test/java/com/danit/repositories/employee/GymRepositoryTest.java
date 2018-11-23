package com.danit.repositories.employee;

import com.danit.models.employee.Gym;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GymRepositoryTest {

  @Autowired
  private GymRepository gymRepository;

  @Test
  public void createGymTest() {
    Gym gym = new Gym();
    gym.setName("Boxing gym");
    Assert.assertNotNull(gymRepository.save(gym));
  }

  @Test
  public void getGymByIdTest() {
    Assert.assertNotNull(gymRepository.findById(1L));
  }
}
