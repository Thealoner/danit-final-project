package com.danit.controllers;


import com.danit.models.Client;
import com.danit.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

  Logger logger = LoggerFactory.getLogger(TestController.class);

  private ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping("/clients")
  @ResponseStatus(HttpStatus.CREATED)
  public void createClient(@RequestBody List<Client> clients) {
    logger.info("Adding new clients");
    clientService.saveClients(clients);
    logger.info("Clients saved");
  }

  @GetMapping("/clients")
  List<Client> getAllClients() {
    return clientService.getAllClients();
  }

  @GetMapping("/clients/{id}")
  Client getClientById(@PathVariable(name = "id") long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    logger.info("User " + currentPrincipalName + " got client with id " + id);
    return clientService.getClientById(id);
  }

  @PutMapping("/clients")
  public void addClient(@RequestBody List<Client> clients) {
    clientService.updateClients(clients);
  }

  @DeleteMapping("/clients/{id}")
  public void deleteClientById(@PathVariable(name = "id") long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    logger.info("User " + currentPrincipalName + " try to delete client with id " + id);
    clientService.deleteClientById(id);
  }

  @DeleteMapping("/clients")
  public void deleteClients(@RequestBody List<Client> clients) {
    clientService.deleteClients(clients);
  }

}
