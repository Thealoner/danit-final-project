package com.danit.services;

import com.danit.models.Client;
import com.danit.models.Contract;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractService {

  Contract getContractById(long id);

  List<Contract> saveContracts(List<Contract> contracts);

  void deleteContractById(long id);

  void deleteContracts(List<Contract> contracts);

  Page<Contract> getAllContracts(int page, int size);
}
