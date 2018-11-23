package com.danit.controllers;


import com.danit.dto.ClientDto;
import com.danit.dto.Views;
import com.danit.facades.ClientFacade;
import com.danit.models.Client;
import com.danit.services.ClientService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  public Map<String, Object> getAllClients(@RequestParam(name = "page") int page,
                                           @RequestParam(name = "size") int size,
                                           Principal principal) {
    log.info(principal.getName() + logMsg1);
    return convertToMap(clientService.getAllClients(page, size));
  }

  //--------dto--------
  @JsonView(Views.Short.class)
  @GetMapping(path = "/clients/short")
  public Map<String, Object> getAllClientsShort(@RequestParam(name = "page") int page,
                                                @RequestParam(name = "size") int size,
                                                Principal principal) throws ParseException {
    log.info(principal.getName() + logMsg1); // NOSONAR
    return convertToMap(clientFacade.getAllClients(page, size)); // NOSONAR
  }

  //--------dto--------
  @JsonView(Views.Extended.class)
  @GetMapping(path = "/clients/extended")
  public Map<String, Object> getAllClientsExtended(@RequestParam(name = "page") int page,
                                                   @RequestParam(name = "size") int size,
                                                   Principal principal) throws ParseException {
    log.info(principal.getName() + logMsg1); // NOSONAR
    return convertToMap(clientFacade.getAllClients(page, size)); // NOSONAR
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
    List<Client> updatedClients = clientService.updateClients(clients);
    System.out.println(updatedClients);
    return updatedClients;
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

  private <T> Map<String, Object> convertToMap(Page<T> pageData) {
    Map<String, Object> outputData = new HashMap<>();
    outputData.put("data", pageData.getContent());
    outputData.put("last_page", pageData.getTotalPages());
    return outputData;
  }

}
