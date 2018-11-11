package com.danit.controllers;


import com.danit.models.Client;
import com.danit.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.danit.utils.SpringSecurityUtils.getCurrentPrincipalName;

@RestController
public class ClientController {

  private Logger logger = LoggerFactory.getLogger(ClientController.class);

  private ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping("/clients")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Client> createClient(@RequestBody List<Client> clients) {
    logger.info("User " + getCurrentPrincipalName() + " is saving new clients: " + clients);
    return clientService.saveClients(clients);
  }

  @GetMapping("/clients")
  List<Client> getAllClients() {
    logger.info("User " + getCurrentPrincipalName() + " got all clients data");
    return clientService.getAllClients();
  }

  @GetMapping("/clients/{id}")
  Client getClientById(@PathVariable(name = "id") long id) {
    logger.info("User " + getCurrentPrincipalName() + " got client data with id: " + id);
    return clientService.getClientById(id);
  }

  @PutMapping("/clients")
  public void addClient(@RequestBody List<Client> clients) {
    logger.info("User " + getCurrentPrincipalName() + " is updating clients data: " + clients);
    clientService.updateClients(clients);
  }

  @DeleteMapping("/clients/{id}")
  public void deleteClientById(@PathVariable(name = "id") long id) {
    logger.info("User " + getCurrentPrincipalName() + " try to delete client with id: " + id);
    clientService.deleteClientById(id);
  }

  @DeleteMapping("/clients")
  public void deleteClients(@RequestBody List<Client> clients) {
    logger.info("User " + getCurrentPrincipalName() + " is trying to delete clients: " + clients);
    clientService.deleteClients(clients);
  }

}
