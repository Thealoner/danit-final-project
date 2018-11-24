package com.danit.controllers;

import com.danit.dto.ContractDto;
import com.danit.dto.Views;
import com.danit.facades.ContractFacade;
import com.danit.models.Contract;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.List;
import java.util.Map;

import static com.danit.utils.ControllerUtils.convertToMap;


@RestController
@JsonView(Views.Extended.class)
@RequestMapping("/contracts")
public class ContractController {

  private Logger logger = LoggerFactory.getLogger(ContractController.class);

  private ContractFacade contractFacade;

  @Autowired
  public ContractController(ContractFacade contractFacade) {
    this.contractFacade = contractFacade;
  }

  @PostMapping
  ResponseEntity<List<ContractDto>> createContracts(@RequestBody List<Contract> contracts, Principal principal) {
    logger.info(principal.getName() + " is saving new contracts: " + contracts);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(contractFacade.saveContracts(contracts));
  }

  @GetMapping
  ResponseEntity<Map<String, Object>> getAllContracts(@RequestParam(name = "page") int page,
                                 @RequestParam(name = "size") int size,
                                 Principal principal) {
    logger.info(principal.getName() + " got all contracts data");
    return ResponseEntity.status(HttpStatus.OK)
        .body(convertToMap(contractFacade.getAllContracts(page, size)));
  }

  @GetMapping("/{id}")
  ResponseEntity<ContractDto> getContractById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got contract data with id: " + id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(contractFacade.getContractById(id));
  }

  @PutMapping
  public ResponseEntity<List<ContractDto>> updateContracts(@RequestBody List<Contract> contracts, Principal principal) {
    logger.info(principal.getName() + " is updating contracts data: " + contracts);
    return ResponseEntity.status(HttpStatus.OK)
        .body(contractFacade.saveContracts(contracts));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteContractById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete contract with id: " + id);
    contractFacade.deleteContractById(id);
    return (ResponseEntity) ResponseEntity.status(HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity deleteContracts(@RequestBody List<Contract> contracts, Principal principal) {
    logger.info(principal.getName() + " is trying to delete contracts: " + contracts);
    contractFacade.deleteContracts(contracts);
    return (ResponseEntity) ResponseEntity.status(HttpStatus.OK);
  }

}
