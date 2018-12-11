package com.danit.repositories;

import com.danit.models.Client;
import com.danit.models.auditor.AuditorAwareImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = {AuditorAwareImpl.class}))
public class ClientRepositoryTest {

  @Autowired
  private ClientRepositoryBase clientRepository;


  @Test
  public void findById() {
    Client client = new Client();
    client.setFirstName("Nadya");
    clientRepository.save(client);
    Optional<Client> optFound = clientRepository.findById(client.getId());
    Client found = optFound.orElseThrow(RuntimeException::new);
    assertThat(found.getFirstName()).isEqualTo("Nadya");
  }

  @Test
  public void findAllClients() {
    clientRepository.deleteAll();

    for (int i = 0; i < 10; i++) {
      Client client = new Client();
      client.setFirstName("Client" + i);
      clientRepository.save(client);
    }

    assertThat(clientRepository.count()).isEqualTo(10);

    List<Client> clients = (List<Client>) this.clientRepository.findAll();
    for (int i = 0; i < 10; i++) {
      assertThat(clients.get(i).getFirstName()).isEqualTo("Client" + i);
    }
  }

  @Test
  public void deleteAllClients() {
    for (int i = 0; i < 10; i++) {
      Client client = new Client();
      client.setFirstName("Client" + i);
      clientRepository.save(client);
    }
    clientRepository.deleteAll();
    assertThat(clientRepository.count()).isEqualTo(0);
  }

}
