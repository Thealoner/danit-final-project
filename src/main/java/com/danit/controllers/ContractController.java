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

@RestController
public class ContractController {

  Logger logger = LoggerFactory.getLogger(ContractController.class);

  private ContractService contractService;

  @Autowired
  public ContractController(ContractService contractService) {
    this.contractService = contractService;
  }

  @PostMapping("/contracts")
  @ResponseStatus(HttpStatus.CREATED)
  private void createContracts(@RequestBody List<Contract> contracts) {
    logger.info("Adding new contract");
    contractService.saveContracts(contracts);
    logger.info("Contract saved");
  }

  @GetMapping("/contracts/{id}")
  Contract getContractById(@PathVariable(name = "id") long id) {
    return contractService.getContractById(id);
  }

  @GetMapping("/contracts")
  List<Contract> getAllContracts() {
    return contractService.getAllContracts();
  }

  @PutMapping("/contracts")
  public void addContracts(@RequestBody List<Contract> contracts) {
    contractService.saveContracts(contracts);
  }

  @DeleteMapping("/contracts/{id}")
  public void deleteContractById(@PathVariable(name = "id") long id) {
    contractService.deleteContractById(id);
  }

  @DeleteMapping("/contracts")
  public void deleteContracts(@RequestBody List<Contract> contracts) {
    contractService.deleteContracts(contracts);
  }

}
