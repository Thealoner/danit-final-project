package com.danit.services;

import com.danit.Application;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.Client;
import com.danit.repositories.ClientRepository;
import com.danit.repositories.PaketRepository;
import com.danit.repositories.specifications.ClientListSpecification;
import com.danit.repositories.specifications.PaketListSpecification;
import com.danit.utils.WebSocketUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ClientServiceTest {
  @InjectMocks
  ClientService clientService;

  @Mock
  ClientRepository clientRepository;

  @Mock
  WebSocketUtils webSocketUtils;

  @Mock
  ClientListSpecification clientListSpecification;

  @Mock
  SimpMessageSendingOperations messagingTemplate;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  Client getMockClient(String name) {
    Client client = new Client();
    client.setEmail("client@email.com");
    client.setFirstName("Firstname" + name);
    client.setLastName("LastName");
    client.setGender("female");
    client.setActive(true);
    return client;
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteClientTest() {
    Client client = getMockClient("name");
    client.setId(1L);
    doNothing().when(clientRepository).delete(client);
    clientService.deleteEntityById(1L);
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteCardsTest() {
    List<Client> clients = new ArrayList<Client>();
    for (int i = 1; i < 4; i++) {
      Client client = getMockClient("" + i);
      client.setId(new Long(i));
      clients.add(client);
    }
    doNothing().when(clientRepository).deleteAll(clients);
    clientService.deleteEntities(clients);
  }
}
