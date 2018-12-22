package com.danit.facades;

import com.danit.dto.ContractDto;
import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import com.danit.services.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ContractFacade extends AbstractDtoFacade<ContractDto, Contract, ContractListRequestDto> {

  private ContractService contractService;

  public ContractFacade(ContractService contractService) {
    this.contractService = contractService;
  }

  public Page<ContractDto> findAllContractsDtoForClientId(Long clientId, Pageable pageable) {
    return convertToDtos(contractService.findAllContractsForClientId(clientId, pageable));
  }

  public Page<ContractDto> findAllContractsDtoForPaketId(Long paketId, Pageable pageable) {
    return convertToDtos(contractService.findAllContractsForClientId(paketId, pageable));
  }
}
