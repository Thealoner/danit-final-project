package com.danit.controllers;

import com.danit.models.Contract;
import com.danit.services.ClientService;
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

import java.util.List;

@RestController
public class ContractController {

  Logger logger = LoggerFactory.getLogger(ContractController.class);

  @Autowired
  private ContractService contractService;

  @Autowired
  private ClientService clientService;

  @GetMapping("/contracts")
  List<Contract> getAllContracts() {
    return contractService.getAllContracts();
  }

  @GetMapping("/contracts/{id}")
  Contract getContractById(@PathVariable(name = "id") long id) {
    return contractService.getContractById(id);
  }

  @PutMapping("/contracts")
  public void addContract(@RequestBody Contract contract) {
    contractService.saveContract(contract);
  }

  @DeleteMapping("/contracts/{id}")
  public void deleteContractById(@PathVariable(name = "id") long id) {
    contractService.deleteContractById(id);
  }

  @PostMapping("/contracts")
  private void createContract(@RequestBody List<Contract> contracts) {
    logger.info("Adding new contract");
    contractService.saveAllContracts(contracts);
    logger.info("Contract saved");
  }

}
