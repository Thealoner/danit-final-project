package com.danit.controllers;

import com.danit.models.Contract;
import com.danit.services.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.danit.utils.SpringSecurityUtils.getCurrentPrincipalName;

@RestController
public class ContractController {

  private Logger logger = LoggerFactory.getLogger(ContractController.class);

  private ContractService contractService;

  @Autowired
  public ContractController(ContractService contractService) {
    this.contractService = contractService;
  }

  @PostMapping("/contracts")
  @ResponseStatus(HttpStatus.CREATED)
  List<Contract> createContracts(@RequestBody List<Contract> contracts) {
    logger.info("User " + getCurrentPrincipalName() + " is saving new contracts: " + contracts);
    return contractService.saveContracts(contracts);
  }

  @GetMapping("/contracts")
  List<Contract> getAllContracts() {
    logger.info("User " + getCurrentPrincipalName() + " got all contracts data");
    return contractService.getAllContracts();
  }

  @GetMapping("/contracts/{id}")
  Contract getContractById(@PathVariable(name = "id") long id) {
    logger.info("User " + getCurrentPrincipalName() + " got contract data with id: " + id);
    return contractService.getContractById(id);
  }

  @PutMapping("/contracts")
  public void addContracts(@RequestBody List<Contract> contracts) {
    logger.info("User " + getCurrentPrincipalName() + " is updating contracts data: " + contracts);
    contractService.saveContracts(contracts);
  }

  @DeleteMapping("/contracts/{id}")
  public void deleteContractById(@PathVariable(name = "id") long id) {
    logger.info("User " + getCurrentPrincipalName() + " try to delete contract with id: " + id);
    contractService.deleteContractById(id);
  }

  @DeleteMapping("/contracts")
  public void deleteContracts(@RequestBody List<Contract> contracts) {
    logger.info("User " + getCurrentPrincipalName() + " is trying to delete contracts: " + contracts);
    contractService.deleteContracts(contracts);
  }

}
