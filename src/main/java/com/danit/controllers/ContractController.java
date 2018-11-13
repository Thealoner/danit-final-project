package com.danit.controllers;

import com.danit.models.Contract;
import com.danit.services.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class ContractController {

  private Logger logger = LoggerFactory.getLogger(ContractController.class);

  private ContractService contractService;

  @Autowired
  public ContractController(ContractService contractService) {
    this.contractService = contractService;
  }

  @PostMapping("/contracts")
  List<Contract> createContracts(@RequestBody List<Contract> contracts, Principal principal) {
    logger.info(principal.getName() + " is saving new contracts: " + contracts);
    return contractService.saveContracts(contracts);
  }

  @GetMapping("/contracts")
  List<Contract> getAllContracts(Principal principal) {
    logger.info(principal.getName() + " got all contracts data");
    return contractService.getAllContracts();
  }

  @GetMapping("/contracts/{id}")
  Contract getContractById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got contract data with id: " + id);
    return contractService.getContractById(id);
  }

  @PutMapping("/contracts")
  public void addContracts(@RequestBody List<Contract> contracts, Principal principal) {
    logger.info(principal.getName() + " is updating contracts data: " + contracts);
    contractService.saveContracts(contracts);
  }

  @DeleteMapping("/contracts/{id}")
  public void deleteContractById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete contract with id: " + id);
    contractService.deleteContractById(id);
  }

  @DeleteMapping("/contracts")
  public void deleteContracts(@RequestBody List<Contract> contracts, Principal principal) {
    logger.info(principal.getName() + " is trying to delete contracts: " + contracts);
    contractService.deleteContracts(contracts);
  }

}
