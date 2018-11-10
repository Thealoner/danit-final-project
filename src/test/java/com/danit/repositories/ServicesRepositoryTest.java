package com.danit.repositories;

import com.danit.models.Services;
import com.danit.services.ServicesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class ServicesRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ServicesService servicesService;

  @Autowired
  private ServiceRepository serviceRepository;


  @Test
  public void whenFindByName_thenReturnEmployee() {

    // given
    Services workout = new Services();
    workout.setId(1009L);
    workout.setTitle("ABT");
    serviceRepository.save(workout);

    // when
    Services found = servicesService.getServiceById(1009L);


    // then
    assertThat(found.getTitle()).isEqualTo(workout.getTitle());
  }

}
