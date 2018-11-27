package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractService {

  Contract getContractById(long id);

  List<Contract> saveContracts(List<Contract> contracts);

  void deleteContractById(long id);

  void deleteContracts(List<Contract> contracts);

  Page<Contract> getAllContracts(Pageable pageable);

  Page<Contract> getAllContracts(ContractListRequestDto contractListRequestDto, Pageable pageable);
}
