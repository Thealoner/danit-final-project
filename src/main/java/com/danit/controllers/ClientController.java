package com.danit.controllers;


import com.danit.dto.clientdto.ClientDto;
import com.danit.models.Client;
import com.danit.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

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
  public List<Client> createClient(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return clientService.saveClients(clients);
  }

  //  @PostMapping(path = "/clients",
  //      consumes = MediaType.APPLICATION_JSON_VALUE,
  //      produces = MediaType.APPLICATION_JSON_VALUE)
  //  public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto dto) throws ParseException {
  //    return new ResponseEntity<>(
  //        convertToDto(clientService.saveClient(convertToEntity(dto))), HttpStatus.OK);
  //  }

  @GetMapping("/clients")
  List<Client> getAllClients(Principal principal) {
    log.info(principal.getName() + " got all clients data");
    return clientService.getAllClients();
  }

  @GetMapping("/clients/{id}")
  Client getClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return clientService.getClientById(id);
  }

  @PutMapping("/clients")
  public void addClient(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    clientService.updateClients(clients);
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
    ClientDto clientDto = modelMapper.map(client, ClientDto.class);
    return clientDto;
  }

  private Client convertToEntity(ClientDto clientDto) throws ParseException {
    Client client = modelMapper.map(clientDto, Client.class);
    return client;
  }

}
