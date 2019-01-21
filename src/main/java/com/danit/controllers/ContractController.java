package com.danit.controllers;

import com.danit.dto.Views;
import com.danit.dto.service.ContractListRequestDto;
import com.danit.facades.CardFacade;
import com.danit.facades.ContractFacade;
import com.danit.models.Card;
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
@RequestMapping("/contracts")
public class ContractController {

  private ContractFacade contractFacade;

  private ContractService contractService;

  private CardFacade cardFacade;

  @Autowired
  public ContractController(ContractFacade contractFacade, ContractService contractService,
                            CardFacade cardFacade) {
    this.contractFacade = contractFacade;
    this.contractService = contractService;
    this.cardFacade = cardFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  ResponseEntity<Map<String, Object>> createContracts(@RequestBody List<Contract> contracts, Principal principal) {
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
    return ResponseEntity.ok(convertPageToMap(contractFacade.getAllEntities(contractListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<Map<String, Object>> getContractById(@PathVariable(name = "id") long id, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(id)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  ResponseEntity<Map<String, Object>> updateContracts(@RequestBody List<Contract> contracts, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(contractFacade.updateEntities(contracts)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  void deleteContractById(@PathVariable(name = "id") long id, Principal principal) {
    contractFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  void deleteContracts(@RequestBody List<Contract> contracts, Principal principal) {
    contractFacade.deleteEntities(contracts);
  }

  //related entities methods--------------------------------------------------------------------------------------------

  //Clients
  @JsonView(Views.Extended.class)
  @PutMapping("/{contractId}/client/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignClientToContract(@PathVariable(name = "clientId") Long clientId,
                                                             @PathVariable(name = "contractId") Long contractId,
                                                             Principal principal) {
    contractService.assignClientToContract(contractId, clientId);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(contractId)));
  }

  @DeleteMapping("/{contractId}/client/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteClientFromContract(@PathVariable(name = "contractId") Long contractId,
                                Principal principal,
                                @PathVariable(name = "clientId") Long clientId) {
    contractService.deleteClientFromContract(contractId, clientId);
  }

  //Pakets
  @JsonView(Views.Extended.class)
  @PutMapping("/{contractId}/paket/{paketId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignPaketToContract(@PathVariable(name = "paketId") Long paketId,
                                                            @PathVariable(name = "contractId") Long contractId,
                                                            Principal principal) {
    contractService.assignPaketToContract(contractId, paketId);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(contractId)));
  }

  @DeleteMapping("/{contractId}/paket/{paketId}")
  @ResponseStatus(HttpStatus.OK)
  void deletePaketFromContract(@PathVariable(name = "paketId") Long paketId,
                               @PathVariable(name = "contractId") Long contractId,
                               Principal principal) {
    contractService.deletePaketFromContract(contractId, paketId);
  }

  //Cards
  @JsonView(Views.Extended.class)
  @PutMapping("/{contractId}/card/{cardId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignCardToContract(@PathVariable(name = "cardId") Long cardId,
                                                           @PathVariable(name = "contractId") Long contractId,
                                                           Principal principal) {
    contractService.assignCardToContract(contractId, cardId);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(contractId)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping("/{contractId}/cards")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignCardsToContract(@RequestBody List<Card> cards,
                                                            @PathVariable(name = "contractId") Long contractId,
                                                            Principal principal) {
    contractService.assignCardsToContract(contractId, cards);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.getEntityById(contractId)));
  }

  @DeleteMapping("/{contractId}/card/{cardId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteCardFromContract(@PathVariable(name = "cardId") Long cardId,
                              @PathVariable(name = "contractId") Long contractId,
                              Principal principal) {
    contractService.deAssignCardFromContract(contractId, cardId);
  }

  @DeleteMapping("/{contractId}/cards")
  @ResponseStatus(HttpStatus.OK)
  void deleteCardsFromContract(@RequestBody List<Card> cards,
                               @PathVariable(name = "contractId") Long contractId,
                               Principal principal) {
    contractService.deAssignCardsFromContract(contractId, cards);
  }

  @JsonView(Views.Short.class)
  @GetMapping("{contractId}/cards/short")
  ResponseEntity<Map<String, Object>> getAllCardsForContractIdShort(
      @PathVariable(name = "contractId") Long contractId,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(cardFacade.findAllCardsForContractId(contractId, pageable)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping("{contractId}/cards/ids")
  ResponseEntity<Map<String, Object>> getAllCardsForContractIdIds(
      @PathVariable(name = "contractId") Long contractId,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(cardFacade.findAllCardsForContractId(contractId, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("{contractId}/cards")
  ResponseEntity<Map<String, Object>> getAllCardsForContractIdExtended(
      @PathVariable(name = "contractId") Long contractId,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(cardFacade.findAllCardsForContractId(contractId, pageable)));
  }

}
