package com.danit.controllers;

import com.danit.models.Contract;
import com.danit.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContractController {

  @Autowired
  private ContractService contractService;

  @GetMapping("/contracts")
  List<Contract> getAllContracts() {
    return contractService.getAllContracts();
  }

  @GetMapping("/contracts/{id}")
  Contract getContractById(@PathVariable(name = "id") long id) {
    return contractService.getContractById(id);
  }

}
