package com.danit.services;

import com.danit.models.Contract;

import java.util.List;

public interface ContractService {

  List<Contract> getAllContracts();

  Contract getContractById(long id);

  List<Contract> saveContracts(List<Contract> contracts);

  void deleteContractById(long id);

  void deleteContracts(List<Contract> contracts);

}
