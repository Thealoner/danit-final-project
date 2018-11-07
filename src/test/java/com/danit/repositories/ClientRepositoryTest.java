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
    client.setId((long) 1009);
    client.setFirstName("Nadya");
    this.entityManager.persist(client);
    Optional<Client> optFound = this.clientRepository.findById((long) 1009);
    Client found = optFound.orElseThrow(RuntimeException::new);
    assertThat(found.getFirstName()).isEqualTo("Nadya");
  }

  @Test
  public void findAllClients(){
    for(int i = 1001; i < 1006; i++){
      Client client = new Client();
      client.setId((long) i);
      client.setFirstName("Client" + i);
      this.entityManager.persist(client);
    }

    List<Client> found = this.clientRepository.findAll();

    assertThat(found.get(3).getId()).isEqualTo((long)1004);
  }

}
