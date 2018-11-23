package com.danit.repositories.employee;

import com.danit.models.employee.Gym;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

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

  @Test
  public void deleteGymTest() {
    int gymQuant = (int) gymRepository.count();
    Gym gym = new Gym();
    gym.setName("Boxing gym");
    long savedId =  gymRepository.save(gym).getId();
    gymRepository.deleteById(savedId);
    Assert.assertEquals(gymRepository.findById(savedId),Optional.empty());
  }

  @Test
  public void updateGymTest(){
    Gym gymToSave = new Gym();
    gymToSave.setName("Test gym");
    long savedGymId = gymRepository.save(gymToSave).getId();
    Assert.assertEquals(gymToSave.getName(), gymRepository.findById(savedGymId).get().getName());
  }
}
