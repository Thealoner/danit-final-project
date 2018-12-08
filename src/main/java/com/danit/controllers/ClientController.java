package com.danit.controllers;


import com.danit.dto.Views;
import com.danit.dto.service.ClientListRequestDto;
import com.danit.facades.ClientFacade;
import com.danit.models.Client;
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

import static com.danit.utils.ControllerUtils.convertDtoToMap;
import static com.danit.utils.ControllerUtils.convertPageToMap;

@RestController
@RequestMapping("/clients")
@Slf4j
public class ClientController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all clients data";
  private ClientFacade clientFacade;

  @Autowired
  public ClientController(ClientFacade clientFacade) {
    this.clientFacade = clientFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<Map<String, Object>> createClientsDtoExtended(@RequestBody List<Client> clients,
                                                                      Principal principal) {
    log.info(principal.getName() + " is saving new clients: " + clients);
    return ResponseEntity.ok(convertDtoToMap(clientFacade.saveEntities(clients)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllClientsDtoIds(Pageable pageable,
                                                                 Principal principal,
                                                                 ClientListRequestDto clientListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertPageToMap(clientFacade.getAllEntities(clientListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllClientsDtoShort(Pageable pageable,
                                                                   Principal principal,
                                                                   ClientListRequestDto clientListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertPageToMap(clientFacade.getAllEntities(clientListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllClientsDtoExtended(Pageable pageable,
                                                                      Principal principal,
                                                                      ClientListRequestDto clientListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA); // NOSONAR
    return ResponseEntity.ok(convertPageToMap(clientFacade.getAllEntities(clientListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<Map<String, Object>> getClientByIdDtoExtended(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got client data with id: " + id);
    return ResponseEntity.ok(convertDtoToMap(clientFacade.getEntityById(id)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  public ResponseEntity<Map<String, Object>> updateClientsDto(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is updating clients data: " + clients);
    return ResponseEntity.ok(convertDtoToMap(clientFacade.updateEntities(clients)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClientByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " is trying to delete client with id: " + id);
    clientFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteClientsDto(@RequestBody List<Client> clients, Principal principal) {
    log.info(principal.getName() + " is trying to delete clients: " + clients);
    clientFacade.deleteEntities(clients);
  }

}
