package ua.com.danit.facades;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ua.com.danit.dto.ClientDto;
import ua.com.danit.models.Client;
import ua.com.danit.services.ClientService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientFacadeImpl implements ClientFacade {

  private final ClientService clientService;

  private final ModelMapper modelMapper;

  @Autowired
  public ClientFacadeImpl(ClientService clientService, ModelMapper modelMapper) {
    this.clientService = clientService;
    this.modelMapper = modelMapper;
  }


  @Override
  public ClientDto convertToDto(Client client) {
    return modelMapper.map(client, ClientDto.class);
  }

  private List<ClientDto> convertToDtos(List<Client> clients) {
    List<ClientDto> dtoClients = new ArrayList<>();
    clients.forEach(client ->
        dtoClients.add(modelMapper.map(client, ClientDto.class)));
    return dtoClients;
  }

  private Page<ClientDto> convertToDtos(Page<Client> clients) {
    return clients.map(this::convertToDto);
  }


  @Override
  public Page<ClientDto> getAllClients(int page, int size) {
    return convertToDtos(clientService.getAllClients(page, size));
  }

  @Override
  public List<ClientDto> saveClients(List<Client> clients) {
    return convertToDtos(clientService.saveClients(clients));
  }

  @Override
  public ClientDto getClientById(Long id) {
    return convertToDto(clientService.getClientById(id));
  }

  @Override
  public List<ClientDto> updateClients(List<Client> clients) {
    return convertToDtos(clientService.updateClients(clients));
  }

  @Override
  public void deleteClientById(Long id) {
    clientService.deleteClientById(id);
  }

  @Override
  public void deleteClients(List<Client> clients) {
    clientService.deleteClients(clients);
  }


}
