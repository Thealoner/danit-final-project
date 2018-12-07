package com.danit.controllers;

import com.danit.dto.Views;
import com.danit.dto.service.ContractListRequestDto;
import com.danit.facades.ContractFacade;
import com.danit.models.Contract;
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
@RequestMapping("/contracts")
@Slf4j
public class ContractController {

  private ContractFacade contractFacade;

  @Autowired
  public ContractController(ContractFacade contractFacade) {
    this.contractFacade = contractFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  ResponseEntity<Map<String, Object>> createContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is saving new contracts: " + contracts);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.saveEntities(contracts)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping("/ids")
  public ResponseEntity<Map<String, Object>> getAllContractsDtoIds(Pageable pageable,
                                                                   Principal principal,
                                                                   ContractListRequestDto contractListRequestDto) {
    log.info(principal.getName() + " got all Contract data");
    log.info("clientListRequestDto" + contractListRequestDto);
    return ResponseEntity.ok(convertPageToMap(contractFacade.getAllEntities(contractListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping("/short")
  public ResponseEntity<Map<String, Object>> getAllContractsDtoShort(Pageable pageable,
                                                                     Principal principal,
                                                                     ContractListRequestDto contractListRequestDto) {
    log.info(principal.getName() + " got all Contract data");
    log.info("clientListRequestDto" + contractListRequestDto);
    return ResponseEntity.ok(convertPageToMap(contractFacade.getAllEntities(contractListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllContractsDtoExtended(Pageable pageable,
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
  public ResponseEntity<Map<String, Object>> updateContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is updating contracts data: " + contracts);
    return ResponseEntity.ok(convertDtoToMap(contractFacade.saveEntities(contracts)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteContractById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " try to delete contract with id: " + id);
    contractFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is trying to delete contracts: " + contracts);
    contractFacade.deleteEntities(contracts);
  }

}
