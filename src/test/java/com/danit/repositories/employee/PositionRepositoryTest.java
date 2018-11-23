package com.danit.repositories.employee;

import com.danit.models.employee.Position;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PositionRepositoryTest {

  @Autowired
  private PositionRepository positionRepository;

  @Test
  public void createPositionTest() {
    Position position = new Position();
    position.setName("Big Boss");
    Assert.assertNotNull(positionRepository.save(position));
  }

  @Test
  public void getPositionByIdTest() {
    Assert.assertNotNull(positionRepository.findById(1L));
  }

  @Test
  public void deletePositionTest() {
    int positionQuant = (int) positionRepository.count();
    Position position = new Position();
    position.setName("Big Boss");
    long savedId =  positionRepository.save(position).getId();
    positionRepository.deleteById(savedId);
    Assert.assertEquals(positionRepository.findById(savedId), Optional.empty());
  }

  @Test
  public void updatePositionTest(){
    Position positionToSave = new Position();
    positionToSave.setName("Test position");
    long savedPositionId = positionRepository.save(positionToSave).getId();
    Assert.assertEquals(positionToSave.getName(), positionRepository.findById(savedPositionId).orElse(null).getName());
  }
}
