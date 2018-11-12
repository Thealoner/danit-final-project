package com.danit.repositories;

import com.danit.models.Services;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ServicesRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ServiceRepository serviceRepository;


  @Test
  public void findById() {
    Services service = new Services();
    service.setTitle("Yoga");
    this.entityManager.persist(service);
    Optional<Services> optFound = this.serviceRepository.findById(service.getId());
    Services found = optFound.orElseThrow(RuntimeException::new);
    assertThat(found.getTitle()).isEqualTo("Yoga");
  }
}
