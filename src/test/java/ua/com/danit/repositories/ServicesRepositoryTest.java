package ua.com.danit.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.danit.models.Services;
import ua.com.danit.models.auditor.AuditorAwareImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = {AuditorAwareImpl.class}))
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
