package com.danit.repositories;

import com.danit.models.Service;
import com.danit.models.auditor.AuditorAwareImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = {AuditorAwareImpl.class}))
public class ServiceRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ServiceRepository serviceRepository;


  @Test
  public void findById() {
    Service service = new Service();
    service.setTitle("Yoga");
    this.entityManager.persist(service);
    Optional<Service> optFound = this.serviceRepository.findById(service.getId());
    Service found = optFound.orElseThrow(RuntimeException::new);
    assertThat(found.getTitle()).isEqualTo("Yoga");
  }
}
