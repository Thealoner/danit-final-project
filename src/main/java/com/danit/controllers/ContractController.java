package com.danit.controllers;

import com.danit.dto.Views;
import com.danit.dto.service.ContractListRequestDto;
import com.danit.facades.ContractFacade;
import com.danit.models.Card;
import com.danit.models.Contract;
import com.danit.services.ContractService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/contracts")
@Slf4j
public class ContractController {

  private ContractFacade contractFacade;

  private ContractService contractService;

  @Autowired
  public ContractController(ContractFacade contractFacade, ContractService contractService) {
    this.contractFacade = contractFacade;
    this.contractService = contractService;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  ResponseEntity<Map<String, Object>> createContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is saving new contracts: " + contracts);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.saveEntities(contracts)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping("/ids")
  ResponseEntity<Map<String, Object>> getAllContractsDtoIds(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ContractListRequestDto contractListRequestDto) {
    log.info(principal.getName() + " got all Contract data");
    log.info("clientListRequestDto" + contractListRequestDto);
    return ResponseEntity.ok(convertPageToMap(contractFacade.getAllEntities(contractListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping("/short")
  ResponseEntity<Map<String, Object>> getAllContractsDtoShort(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ContractListRequestDto contractListRequestDto) {
    log.info(principal.getName() + " got all Contract data");
    log.info("clientListRequestDto" + contractListRequestDto);
    return ResponseEntity.ok(convertPageToMap(contractFacade.getAllEntities(contractListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  ResponseEntity<Map<String, Object>> getAllContractsDtoExtended(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ContractListRequestDto contractListRequestDto) {
    log.info(principal.getName() + " got all Contract data");
    log.info("clientListRequestDto" + contractListRequestDto);
    return ResponseEntity.ok(convertPageToMap(contractFacade.getAllEntities(contractListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<Map<String, Object>> getContractById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got contract data with id: " + id);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(id)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  ResponseEntity<Map<String, Object>> updateContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is updating contracts data: " + contracts);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.updateEntities(contracts)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  void deleteContractById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " try to delete contract with id: " + id);
    contractFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  void deleteContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is trying to delete contracts: " + contracts);
    contractFacade.deleteEntities(contracts);
  }

  //related entities methods--------------------------------------------------------------------------------------------

  //Clients
  @JsonView(Views.Extended.class)
  @PutMapping("/{contractId}/client/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignClientToContract(@PathVariable(name = "contractId") Long contractId,
                                                             @PathVariable(name = "clientId") Long clientId,
                                                             Principal principal) {
    log.info(principal.getName() + " is trying to assign clientId=" + clientId + " to contractId= " + contractId);
    contractService.assignClientToContract(contractId, clientId);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(contractId)));
  }

  @DeleteMapping("/{contractId}/client/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteClientFromContract(@PathVariable(name = "contractId") Long contractId,
                                @PathVariable(name = "clientId") Long clientId,
                                Principal principal) {
    log.info(principal.getName() + " is trying to delete clientId=" + clientId + " from contractId= " + contractId);
    contractService.deleteClientFromContract(contractId, clientId);
  }

  //Pakets
  @JsonView(Views.Extended.class)
  @PutMapping("/{contractId}/paket/{paketId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignPaketToContract(@PathVariable(name = "contractId") Long contractId,
                                                            @PathVariable(name = "paketId") Long paketId,
                                                            Principal principal) {
    log.info(principal.getName() + " is trying to assign paketId=" + paketId + " to contractId= " + contractId);
    contractService.assignPaketToContract(contractId, paketId);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(contractId)));
  }

  @DeleteMapping("/{contractId}/paket/{paketId}")
  @ResponseStatus(HttpStatus.OK)
  void deletePaketFromContract(@PathVariable(name = "contractId") Long contractId,
                               @PathVariable(name = "paketId") Long paketId,
                               Principal principal) {
    log.info(principal.getName() + " is trying to delete paketId=" + paketId + " from contractId= " + contractId);
    contractService.deletePaketFromContract(contractId, paketId);
  }

  //Cards
  @JsonView(Views.Extended.class)
  @PutMapping("/{contractId}/card/{cardId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignCardToContract(@PathVariable(name = "contractId") Long contractId,
                                                           @PathVariable(name = "cardId") Long cardId,
                                                           Principal principal) {
    log.info(principal.getName() + " is trying to assign cardId=" + cardId + " to contractId= " + contractId);
    contractService.assignCardToContract(contractId, cardId);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(contractId)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping("/{contractId}/cards")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignCardsToContract(@PathVariable(name = "contractId") Long contractId,
                                                            @RequestBody List<Card> cards,
                                                            Principal principal) {
    log.info(principal.getName() + " is trying to assign cards" + cards + " to contractId= " + contractId);
    contractService.assignCardsToContract(contractId, cards);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(contractId)));
  }

  @DeleteMapping("/{contractId}/card/{cardId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteCardFromContract(@PathVariable(name = "contractId") Long contractId,
                              @PathVariable(name = "cardId") Long cardId,
                              Principal principal) {
    log.info(principal.getName() + " is trying to delete cardId=" + cardId + " from contractId= " + contractId);
    contractService.deleteCardFromContract(contractId, cardId);
  }

  @DeleteMapping("/{contractId}/cards")
  @ResponseStatus(HttpStatus.OK)
  void deleteCardsToContract(@PathVariable(name = "contractId") Long contractId,
                             @RequestBody List<Card> cards,
                             Principal principal) {
    log.info(principal.getName() + " is trying to delete cards" + cards + " from contractId= " + contractId);
    contractService.deleteCardsFromContract(contractId, cards);
  }

}
