package com.danit.controllers;


import com.danit.dto.ClientDto;
import com.danit.dto.Views;
import com.danit.facades.ClientFacade;
import com.danit.models.Client;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
@Slf4j
public class ClientController {

  private ClientFacade clientFacade;

  private String logMsg1 = " got all clients data";

  @Autowired
  public ClientController(ClientFacade clientFacade) {
    this.clientFacade = clientFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public List<ClientDto> createClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return clientFacade.saveClients(clients);
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public Map<String, Object> getAllClientsShort(@RequestParam(name = "page") int page,
                                                @RequestParam(name = "size") int size,
                                                Principal principal) throws ParseException {
    log.info(principal.getName() + logMsg1); // NOSONAR
    return convertToMap(clientFacade.getAllClients(page, size)); // NOSONAR
  }

  @JsonView(Views.Extended.class)
  @GetMapping(path = "/extended")
  public Map<String, Object> getAllClientsExtended(@RequestParam(name = "page") int page,
                                                   @RequestParam(name = "size") int size,
                                                   Principal principal) throws ParseException {
    log.info(principal.getName() + logMsg1); // NOSONAR
    return convertToMap(clientFacade.getAllClients(page, size)); // NOSONAR
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ClientDto getClientByIdExtended(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return clientFacade.getClientById(id);
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  public List<ClientDto> addClientsAndReturnIds(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return clientFacade.updateClients(clients);
  }

  @DeleteMapping("/{id}")
  public void deleteClientByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete client with id: " + id);
    clientFacade.deleteClientById(id);
  }

  @DeleteMapping
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
