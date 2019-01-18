package com.danit.facades;

import com.danit.Application;
import com.danit.dto.ClientDto;
import com.danit.models.Client;
import com.danit.services.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ClientFacadeTest {

  @InjectMocks
  ClientFacade clientFacade;

  @Mock
  ClientService clientService;

  @Mock
  ModelMapper modelMapper;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void convertToDtoTest() {
    Client client = mock(Client.class);
    clientFacade.convertToDto(client);
    verify(modelMapper, times(1)).map(client, ClientDto.class);
  }

  @Test
  public void convertToDtosTest() {
    Client client1 = mock(Client.class);
    Client client2 = mock(Client.class);
    List<Client> clients = Arrays.asList(new Client[]{client1, client2});
    Pageable pageable = PageRequest.of(0, 4);
    Page<Client> page = new PageImpl<>(clients, pageable, 4);
    clientFacade.convertToDtos(page);
    verify(modelMapper, times(1)).map(clients.get(0), ClientDto.class);
    verify(modelMapper, times(1)).map(clients.get(1), ClientDto.class);
  }

  @Test
  public void convertDtoToEntityTest() {
    ClientDto clientDto = mock(ClientDto.class);
    clientFacade.convertDtoToEntity(clientDto);
    verify(modelMapper, times(1)).map(clientDto, Client.class);
  }

  @Test
  public void getAllEntitiesTest() {
    Client paket1 = mock(Client.class);
    Client paket2 = mock(Client.class);
    List<Client> clients = Arrays.asList(new Client[]{paket1, paket2});
    Pageable pageable = PageRequest.of(0, 4);
    Page<Client> page = new PageImpl<>(clients, pageable, 4);


    when(clientService.getAllEntities(pageable)).thenReturn(page);

    Page<ClientDto> allEntities = clientFacade.getAllEntities(pageable);

    assertEquals(2, allEntities.getNumberOfElements());

    verify(clientService, times(1)).getAllEntities(pageable);
    verify(modelMapper, times(1)).map(clients.get(0), ClientDto.class);
    verify(modelMapper, times(1)).map(clients.get(1), ClientDto.class);
  }

  @Test
  public void getEntityByIdTest() {
    Client client = mock(Client.class);

    when(clientService.getEntityById(1L)).thenReturn(client);

    clientFacade.getEntityById(1L);

    verify(clientService, times(1)).getEntityById(1L);
    verify(modelMapper, times(1)).map(client, ClientDto.class);
  }

  @Test
  public void saveEntitiesTest() {
    Client client1 = mock(Client.class);
    Client client2 = mock(Client.class);
    List<Client> clients = Arrays.asList(new Client[]{client1, client2});

    when(clientService.saveEntities(clients)).thenReturn(clients);

    clientFacade.saveEntities(clients);

    verify(clientService, times(1)).saveEntities(clients);
    verify(modelMapper, times(1)).map(clients.get(0), ClientDto.class);
    verify(modelMapper, times(1)).map(clients.get(1), ClientDto.class);
  }

  @Test
  public void updateEntitiesTest() {
    Client client1 = mock(Client.class);
    Client client2 = mock(Client.class);
    List<Client> clients = Arrays.asList(new Client[]{client1, client2});

    when(clientService.updateEntities(clients)).thenReturn(clients);

    clientFacade.updateEntities(clients);

    verify(clientService, times(1)).updateEntities(clients);
    verify(modelMapper, times(1)).map(clients.get(0), ClientDto.class);
    verify(modelMapper, times(1)).map(clients.get(1), ClientDto.class);
  }



}
