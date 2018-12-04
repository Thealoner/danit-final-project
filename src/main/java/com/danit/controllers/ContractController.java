package com.danit.controllers;

import com.danit.dto.ContractDto;
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
import java.util.Objects;

import static com.danit.utils.ControllerUtils.convertToMap;


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
  ResponseEntity<List<ContractDto>> createContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is saving new contracts: " + contracts);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(contractFacade.saveEntities(contracts));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllContracts(Pageable pageable,
                                                             Principal principal,
                                                             ContractListRequestDto contractListRequestDto) {
    log.info(principal.getName() + " got all Contract data");
    log.info("clientListRequestDto" + contractListRequestDto);
    return ResponseEntity.ok(convertToMap(Objects.nonNull(contractListRequestDto) ?
        contractFacade.getAllEntities(contractListRequestDto, pageable) :
        contractFacade.getAllEntities(pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<ContractDto> getContractById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got contract data with id: " + id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(contractFacade.getEntityById(id));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  public ResponseEntity<List<ContractDto>> updateContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is updating contracts data: " + contracts);
    return ResponseEntity.status(HttpStatus.OK)
        .body(contractFacade.saveEntities(contracts));
  }

  @JsonView(Views.Extended.class)
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteContractById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " try to delete contract with id: " + id);
    contractFacade.deleteEntityById(id);
  }

  @JsonView(Views.Extended.class)
  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteContracts(@RequestBody List<Contract> contracts, Principal principal) {
    log.info(principal.getName() + " is trying to delete contracts: " + contracts);
    contractFacade.deleteEntities(contracts);
  }

}
