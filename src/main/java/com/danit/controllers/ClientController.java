package com.danit.controllers;


import com.danit.dto.ClientDto;
import com.danit.dto.Views;
import com.danit.dto.service.ClientListRequestDto;
import com.danit.facades.ClientFacade;
import com.danit.models.Client;
import com.danit.services.ClientService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.danit.utils.ControllerUtils.convertToMap;

@RestController
@RequestMapping("/clients")
@Slf4j
public class ClientController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all clients data";
  private ClientService clientService;
  private ClientFacade clientFacade;

  @Autowired
  public ClientController(ClientService clientService, ClientFacade clientFacade) {
    this.clientService = clientService;
    this.clientFacade = clientFacade;
  }

  //--------dto---------------------------------------------------------------------------------------------------------
  @JsonView(Views.Extended.class)
  @PostMapping("/extended")
  public ResponseEntity<List<ClientDto>> createClientsDtoExtended(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return ResponseEntity.status(HttpStatus.CREATED).body(clientFacade.saveEntities(clients));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllClientsDtoShort(Pageable pageable,
                                                                   Principal principal,
                                                                   ClientListRequestDto clientListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertToMap(Objects.nonNull(clientListRequestDto) ?
        clientFacade.getAllEntities(clientListRequestDto, pageable) :
        clientFacade.getAllEntities(pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/extended")
  public ResponseEntity<Map<String, Object>> getAllClientsDtoExtended(Pageable pageable,
                                                                      Principal principal,
                                                                      ClientListRequestDto clientListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertToMap(Objects.nonNull(clientListRequestDto) ?
        clientFacade.getAllEntities(clientListRequestDto, pageable) :
        clientFacade.getAllEntities(pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}/extended")
  ResponseEntity<ClientDto> getClientByIdDtoExtended(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return ResponseEntity.ok(clientFacade.getEntityById(id));
  }

  @JsonView(Views.Extended.class)
  @PutMapping("/extended")
  public ResponseEntity<List<ClientDto>> updateClientsDto(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return ResponseEntity.ok(clientFacade.updateEntities(clients));
  }

  @DeleteMapping("/{id}/dto")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClientByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete client with id: " + id);
    clientFacade.deleteEntityById(id);
  }

  @DeleteMapping("/dto")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClientsDto(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is trying to delete clients: " + clients);
    clientFacade.deleteEntities(clients);
  }

  //------not dto-------------------------------------------------------------------------------------------------------

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllClients(Pageable pageable,
                                                           Principal principal,
                                                           ClientListRequestDto clientListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    log.info("clientListRequestDto=" + clientListRequestDto);
    return ResponseEntity.ok(convertToMap(clientService.getAllEntities(clientListRequestDto, pageable)));
  }

  @GetMapping("/{id}")
  ResponseEntity<Client> getClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return ResponseEntity.ok(clientService.getEntityById(id));
  }

  @PostMapping
  public ResponseEntity<List<Client>> createClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveEntities(clients));
  }

  @PutMapping
  public ResponseEntity<List<Client>> addClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return ResponseEntity.ok(clientService.updateEntities(clients));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClientById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete client with id: " + id);
    clientService.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteClients(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is trying to delete clients: " + clients);
    clientService.deleteEntities(clients);
  }

}
