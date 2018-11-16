package com.danit.controllers;


import com.danit.models.Client;
import com.danit.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {

  private Logger logger = LoggerFactory.getLogger(ClientController.class);

  private ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping("/clients")
  public ResponseEntity<List<Client>> createClient(@RequestBody List<Client> clients, Principal principal) {
    logger.info(principal.getName() + " is saving new clients: " + clients);
    return ResponseEntity.ok(clientService.saveClients(clients));
  }

  @GetMapping("/clients")
  ResponseEntity<Map<String, Object>> getAllClients(Principal principal) {
    logger.info(principal.getName() + " got all clients data");
    List<Client> allClients = clientService.getAllClients();
    Map<String, Object> result = new HashMap<>();
    result.put("status", HttpStatus.OK);
    result.put("data", allClients);
    return ResponseEntity.ok(result);

  }

  @GetMapping("/clients/{id}")
  Client getClientById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got client data with id: " + id);
    return clientService.getClientById(id);
  }

  @PutMapping("/clients")
  public void addClient(@RequestBody List<Client> clients, Principal principal) {
    logger.info(principal.getName() + " is updating clients data: " + clients);
    clientService.updateClients(clients);
  }

  @DeleteMapping("/clients/{id}")
  public void deleteClientById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete client with id: " + id);
    clientService.deleteClientById(id);
  }

  @DeleteMapping("/clients")
  public void deleteClients(@RequestBody List<Client> clients, Principal principal) {
    logger.info(principal.getName() + " is trying to delete clients: " + clients);
    clientService.deleteClients(clients);
  }

}
