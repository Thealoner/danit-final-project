package com.danit.controllers;


import com.danit.dto.ClientDto;
import com.danit.dto.Views;
import com.danit.facades.ClientFacade;
import com.danit.models.Client;
import com.danit.services.ClientService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@RestController
@Slf4j
public class ClientController {

  private ClientService clientService;
  private ClientFacade clientFacade;

  private String logMsg1 = " got all clients data";

  @Autowired
  public ClientController(ClientService clientService, ClientFacade clientFacade) {
    this.clientService = clientService;
    this.clientFacade = clientFacade;
  }


  //------not dto------
  @PostMapping("/clients")
  public List<Client> createClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return clientService.saveClients(clients);
  }

  //--------dto--------
  @JsonView(Views.Ids.class)
  @PostMapping("/clients/ids")
  public List<ClientDto> createClientsAndReturnIds(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return clientFacade.saveClients(clients);
  }

  //------not dto------
  @GetMapping("/clients")
  List<Client> getAllClients(Principal principal) {
    log.info(principal.getName() + logMsg1);
    return clientService.getAllClients();
  }

  //--------dto--------
  @JsonView(Views.Short.class)
  @GetMapping(path = "/clients/short",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ClientDto> getAllClientsShort(Principal principal) throws ParseException {
    log.info(principal.getName() + logMsg1);
    return clientFacade.getAllClients();
  }

  //--------dto--------
  @JsonView(Views.Extended.class)
  @GetMapping(path = "/clients/extended")
  public List<ClientDto> getAllClientsExtended(Principal principal) throws ParseException {
    log.info(principal.getName() + logMsg1);
    return clientFacade.getAllClients();
  }

  //------not dto------
  @GetMapping("/clients/{id}")
  Client getClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return clientService.getClientById(id);
  }

  //--------dto--------
  @JsonView(Views.Extended.class)
  @GetMapping("/clients/{id}/extended")
  ClientDto getClientByIdExtended(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return clientFacade.getClientById(id);
  }

  //------not dto------
  @PutMapping("/clients")
  public List<Client> addClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return clientService.updateClients(clients);
  }


  //--------dto--------
  @JsonView(Views.Ids.class)
  @PutMapping("/clients/ids")
  public List<ClientDto> addClientsAndReturnIds(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return clientFacade.updateClients(clients);
  }

  //------not dto------
  @DeleteMapping("/clients/{id}")
  public void deleteClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete client with id: " + id);
    clientService.deleteClientById(id);
  }

  //--------dto--------
  @DeleteMapping("/clients/{id}/dto")
  public void deleteClientByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete client with id: " + id);
    clientFacade.deleteClientById(id);
  }

  //------not dto------
  @DeleteMapping("/clients")
  public void deleteClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is trying to delete clients: " + clients);
    clientService.deleteClients(clients);
  }

  //--------dto--------
  @DeleteMapping("/clients/dto")
  public void deleteClientsDto(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is trying to delete clients: " + clients);
    clientFacade.deleteClients(clients);
  }

}
