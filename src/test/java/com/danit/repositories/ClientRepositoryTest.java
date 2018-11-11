package com.danit.repositories;

import com.danit.models.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ClientRepository clientRepository;


  @Test
  public void findById() {
    Client client = new Client();
    client.setFirstName("Nadya");
    this.entityManager.persist(client);
    Optional<Client> optFound = this.clientRepository.findById(client.getId());
    Client found = optFound.orElseThrow(RuntimeException::new);
    assertThat(found.getFirstName()).isEqualTo("Nadya");
  }

  @Test
  public void findAllClients() {
    clientRepository.deleteAll();

    for (int i = 0; i < 10; i++) {
      Client client = new Client();
      client.setFirstName("Client" + i);
      this.entityManager.persist(client);
    }

    assertThat(clientRepository.getNumberOfClients()).isEqualTo(10);

    List<Client> clients = this.clientRepository.findAll();
    for (int i = 0; i < 10; i++) {
      assertThat(clients.get(i).getFirstName()).isEqualTo("Client" + i);
    }
  }

  @Test
  public void deleteAllClients() {
    for (int i = 0; i < 10; i++) {
      Client client = new Client();
      client.setFirstName("Client" + i);
      this.entityManager.persist(client);
    }
    clientRepository.deleteAll();
    assertThat(clientRepository.getNumberOfClients()).isEqualTo(0);
  }

}
