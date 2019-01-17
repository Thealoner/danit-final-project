package com.danit.controllers;


import com.danit.ApplicationProperties;
import com.danit.dto.ClientDto;
import com.danit.dto.Views;
import com.danit.dto.service.ClientListRequestDto;
import com.danit.facades.ClientFacade;
import com.danit.facades.ContractFacade;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.services.ContractService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_NUMBER;
import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_SIZE;
import static com.danit.utils.ControllerUtils.convertDtoToMap;
import static com.danit.utils.ControllerUtils.convertPageToMap;

@RestController
@RequestMapping("/clients")
public class ClientController {

  @Autowired
  ApplicationProperties applicationProperties;

  private ClientFacade clientFacade;

  private ContractService contractService;

  private ContractFacade contractFacade;

  @Autowired
  public ClientController(ClientFacade clientFacade, ContractService contractService, ContractFacade contractFacade) {
    this.clientFacade = clientFacade;
    this.contractService = contractService;
    this.contractFacade = contractFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  ResponseEntity<Map<String, Object>> createClients(@RequestBody List<Client> clients,
                                                               Principal principal) {
    List<ClientDto> clientDtos = clientFacade.saveEntities(clients);
    return ResponseEntity.ok(convertDtoToMap(clientDtos));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  ResponseEntity<Map<String, Object>> getAllClientsDtoIds(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ClientListRequestDto clientListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(clientFacade.getAllEntities(clientListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  ResponseEntity<Map<String, Object>> getAllClientsDtoShort(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ClientListRequestDto clientListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(clientFacade.getAllEntities(clientListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  ResponseEntity<Map<String, Object>> getAllClientsDtoExtended(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ClientListRequestDto clientListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(clientFacade.getAllEntities(clientListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<Map<String, Object>> getClientByIdDtoExtended(@PathVariable(name = "id") long id, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(clientFacade.getEntityById(id)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  ResponseEntity<Map<String, Object>> updateClientsDto(@RequestBody List<Client> clients, Principal principal) {
    List<ClientDto> clientDtos = clientFacade.updateEntities(clients);
    return ResponseEntity.ok(convertDtoToMap(clientDtos));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  void deleteClientByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    clientFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  void deleteClientsDto(@RequestBody List<Client> clients, Principal principal) {
    clientFacade.deleteEntities(clients);
  }

  //related entities methods--------------------------------------------------------------------------------------------
  @JsonView(Views.Extended.class)
  @PutMapping("/{clientId}/contract/{contractId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignClientToContract(@PathVariable(name = "clientId") Long clientId,
                                                             Principal principal,
                                                             @PathVariable(name = "contractId") Long contractId) {
    contractService.assignClientToContract(contractId, clientId);
    return ResponseEntity.ok(convertDtoToMap(clientFacade.getEntityById(clientId)));
  }

  @JsonView(Views.Extended.class)
  @PostMapping("/{clientId}/contracts")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> createContractsForClient(@RequestBody List<Contract> contracts,
                                                               Principal principal,
                                                               @PathVariable(name = "clientId") Long clientId) {
    contractService.createContractsForClient(clientId, contracts);
    return ResponseEntity.ok(convertDtoToMap(clientFacade.getEntityById(clientId)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping("{clientId}/contracts/ids")
  ResponseEntity<Map<String, Object>> getAllContractsDtoForClientIdIds(
      @PathVariable(name = "clientId") Long clientId,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(contractFacade.findAllContractsDtoForClientId(clientId, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping("{clientId}/contracts/short")
  ResponseEntity<Map<String, Object>> getAllContractsDtoForClientIdShort(
      @PathVariable(name = "clientId") Long clientId,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(contractFacade.findAllContractsDtoForClientId(clientId, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("{clientId}/contracts")
  ResponseEntity<Map<String, Object>> getAllContractsDtoForClientIdExtended(
      @PathVariable(name = "clientId") Long clientId,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(contractFacade.findAllContractsDtoForClientId(clientId, pageable)));
  }

}
