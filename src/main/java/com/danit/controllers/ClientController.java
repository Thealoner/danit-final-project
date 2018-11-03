package com.danit.controllers;


import com.danit.models.Client;
import com.danit.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

  @Autowired
  ClientService clientService;

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/clients")
  List<Client> getAllClients() {
    return clientService.getAllClients();
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/clients/{id}")
  Optional<Client> getClientById(@PathVariable(name = "id") long id) {
    return clientService.getClientById(id);
  }



}
