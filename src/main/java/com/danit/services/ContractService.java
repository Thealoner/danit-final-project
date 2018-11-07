package com.danit.services;

import com.danit.models.Contract;

import java.util.List;

public interface ContractService {
  List<Contract> getAllContracts();

  Contract getContractById(long id);

  void saveContract(Contract contract);

  void deleteContractById(long id);

  void saveAllContracts(List<Contract> contracts);

}
