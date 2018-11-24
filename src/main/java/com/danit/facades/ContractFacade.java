package com.danit.facades;

import com.danit.dto.ContractDto;
import com.danit.models.Contract;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContractFacade {

  ContractDto convertToDto(Contract contract);

  Page<ContractDto> getAllContracts(int page, int size);

  List<ContractDto> saveContracts(List<Contract> contracts);

  ContractDto getContractById(Long id);

  List<ContractDto> updateContracts(List<Contract> contracts);

  void deleteContractById(Long id);

  void deleteContracts(List<Contract> contracts);
}
