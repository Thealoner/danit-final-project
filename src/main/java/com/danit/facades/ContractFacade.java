package com.danit.facades;

import com.danit.dto.ContractDto;
import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractFacade {

  ContractDto convertToDto(Contract contract);

  List<ContractDto> saveContracts(List<Contract> contracts);

  ContractDto getContractById(Long id);

  List<ContractDto> updateContracts(List<Contract> contracts);

  void deleteContractById(Long id);

  void deleteContracts(List<Contract> contracts);

  Page<ContractDto> getAllContracts(Pageable pageable);

  Page<ContractDto> getAllContracts(ContractListRequestDto contractListRequestDto, Pageable pageable);
}
