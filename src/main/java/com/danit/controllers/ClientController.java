package com.danit.controllers;


import com.danit.dto.clientdto.ClientDto;
import com.danit.dto.clientdto.Views;
import com.danit.dto.contractdto.ContractDto;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.services.ClientService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ClientController {

  private ClientService clientService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping("/clients")
  public List<Client> createClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return clientService.saveClients(clients);
  }

//  @PostMapping(path = "/clients",
//        consumes = MediaType.APPLICATION_JSON_VALUE,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//  public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto dto) throws ParseException {
//    return new ResponseEntity<>(convertToDto(
//        clientService.saveClient(convertToEntity(dto))),
//        HttpStatus.OK);
//  }

//  @GetMapping("/clients")
//  List<Client> getAllClients(Principal principal) {
//    log.info(principal.getName() + " got all clients data");
//    return clientService.getAllClients();
//  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/clients/short",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ClientDto> getAllClients(Principal principal) throws ParseException {
    log.info(principal.getName() + " got all clients data");
    return convertToDtos(clientService.getAllClients());
  }

  @JsonView(Views.Extended.class)
  @GetMapping(path = "/clients/extended",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ClientDto> getAllClientsEverything(Principal principal) throws ParseException {
    log.info(principal.getName() + " got all clients data");
    modelMapper.createTypeMap(Contract.class, ContractDto.class);
    List<Client> clients = clientService.getAllClients();
    return clients.stream()
        .map(client -> convertToDto(client))
        .collect(Collectors.toList());
  }

  @GetMapping("/clients/{id}")
  Client getClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return clientService.getClientById(id);
  }

  @PutMapping("/clients")
  public List<Client> addClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return clientService.updateClients(clients);
  }

  @DeleteMapping("/clients/{id}")
  public void deleteClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " try to delete client with id: " + id);
    clientService.deleteClientById(id);
  }

  @DeleteMapping("/clients")
  public void deleteClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is trying to delete clients: " + clients);
    clientService.deleteClients(clients);
  }

  private ClientDto convertToDto(Client client) {
    return modelMapper.map(client, ClientDto.class);
  }

  private List<ClientDto> convertToDtos(List<Client> clients) {
    List<ClientDto> dtoClients = new ArrayList<ClientDto>();
    clients.forEach(client -> {
      dtoClients.add(modelMapper.map(client, ClientDto.class));
    });
    return dtoClients;
  }

  private Client convertToEntity(ClientDto clientDto) throws ParseException {
    return modelMapper.map(clientDto, Client.class);
  }

}
