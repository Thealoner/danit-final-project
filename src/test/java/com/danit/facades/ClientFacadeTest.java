package com.danit.facades;

import com.danit.Application;
import com.danit.dto.ClientDto;
import com.danit.models.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = Application.class)
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("com.danit")
public class ClientFacadeTest {

  @Autowired
  private ClientFacade clientFacade;

  @Test
  public void whenConvertClientEntityToClientDto_thenCorrect() {


    Client client = new Client();
    client.setId(1020L);
    client.setFirstName("Adams");
    client.setEmail("adolf@gmail.com");

    ClientDto clientDto = clientFacade.convertToDto(client);

    assertEquals(client.getFirstName(), clientDto.getFirstName());
    assertEquals(client.getEmail(), clientDto.getEmail());
  }

}
