package com.danit.controllers;


import com.danit.dto.ClientDto;
import com.danit.dto.PageDataDto;
import com.danit.dto.Views;
import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import com.danit.services.ClientService;
import com.danit.services.ClientServiceImpl;
import com.danit.services.EntityService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ClientController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all clients data";
  private ClientServiceImpl clientService;

  @Autowired
  public ClientController(ClientServiceImpl clientService) {
    this.clientService = clientService;
  }

  //------not dto------
  @GetMapping("/clients/{id}")
  ResponseEntity<Client> getClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return ResponseEntity.ok(clientService.getEntityById(id));
  }

  //------not dto------
  @GetMapping("/clients")
  public ResponseEntity<Map<String, Object>> getAllClients(Pageable pageable,
                                                           Principal principal,
                                                           ClientListRequestDto clientListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    log.info("clientListRequestDto=" + clientListRequestDto);
    return ResponseEntity.ok(convertToMap(clientService.getAllEntities(clientListRequestDto, pageable)));
  }

/*
  //------not dto------
  @PostMapping("/clients")
  public ResponseEntity<List<Client>> createClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClients(clients));
  }

  //--------dto--------
  @JsonView(Views.Ids.class)
  @PostMapping("/clients/ids")
  public ResponseEntity<List<ClientDto>> createClientsAndReturnIds(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return ResponseEntity.status(HttpStatus.CREATED).body(clientFacade.saveClients(clients));
  }

  //------not dto------
  @GetMapping("/clients")
  public ResponseEntity<Map<String, Object>> getAllClients(Pageable pageable,
                                                           Principal principal,
                                                           ClientListRequestDto clientListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    log.info("clientListRequestDto=" + clientListRequestDto);
    return ResponseEntity.ok(convertToMap(clientService.getAllClients(clientListRequestDto, pageable)));
  }

  //--------dto--------
  @JsonView(Views.Short.class)
  @GetMapping(path = "/clients/short")
  public ResponseEntity<Map<String, Object>> getAllClientsShort(Pageable pageable,
                                                                Principal principal) throws ParseException {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertToMap(clientFacade.getAllClients(pageable))); // NOSONAR
  }

  //--------dto--------
  @JsonView(Views.Extended.class)
  @GetMapping(path = "/clients/extended")
  public ResponseEntity<Map<String, Object>> getAllClientsExtended(Pageable pageable,
                                                                   Principal principal) throws ParseException {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertToMap(clientFacade.getAllClients(pageable))); // NOSONAR
  }

  //------not dto------
  @GetMapping("/clients/{id}")
  ResponseEntity<Client> getClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return ResponseEntity.ok(clientService.getClientById(id));
  }

  //--------dto--------
  @JsonView(Views.Extended.class)
  @GetMapping("/clients/{id}/extended")
  ResponseEntity<ClientDto> getClientByIdExtended(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return ResponseEntity.ok(clientFacade.getClientById(id));
  }
*/
  //------not dto------
  @PutMapping("/clients")
  public ResponseEntity<List<Client>> addClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return ResponseEntity.ok(clientService.updateEntities(clients));
  }
/*

  //--------dto--------
  @JsonView(Views.Ids.class)
  @PutMapping("/clients/ids")
  public ResponseEntity<List<ClientDto>> addClientsAndReturnIds(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return ResponseEntity.ok(clientFacade.updateClients(clients));
  }
*/
  //------not dto------
  @DeleteMapping("/clients/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete client with id: " + id);
    clientService.deleteEntityById(id);
  }
/*
  //--------dto--------
  @DeleteMapping("/clients/{id}/dto")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClientByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete client with id: " + id);
    clientFacade.deleteClientById(id);
  }

  //------not dto------
  @DeleteMapping("/clients")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is trying to delete clients: " + clients);
    clientService.deleteClients(clients);
  }

  //--------dto--------
  @DeleteMapping("/clients/dto")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClientsDto(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is trying to delete clients: " + clients);
    clientFacade.deleteClients(clients);
  }
  */
  private <T> Map<String, Object> convertToMap(Page<T> pageData) {
    Map<String, Object> outputData = new HashMap<>();
    outputData.put("data", pageData.getContent());
    outputData.put("meta", new PageDataDto(pageData.getTotalElements(),
        pageData.getNumber() + 1, pageData.getTotalPages(),
        pageData.getSize(), pageData.getNumberOfElements()));
    return outputData;
  }

}
