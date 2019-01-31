package com.danit.facades;

import com.danit.dto.ContractDto;
import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import com.danit.services.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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

  @Override
  public List<ContractDto> updateEntities(List<ContractDto> entities) {
    entities.forEach(contractDto -> {
      if(Objects.nonNull(contractDto.getPackageId())) {
        contractService.assignPaketToContract(contractDto.getId(), contractDto.getPackageId());
      } else {
        contractService.deAssignPaketFromContract(contractDto.getId(), contractDto.getPackageId());
      }
      if(Objects.nonNull(contractDto.getClientId())) {
        contractService.assignClientToContract(contractDto.getId(), contractDto.getClientId());
      } else {
        contractService.deAssignClientFromContract(contractDto.getId(), contractDto.getClientId());
      }
    });
    return super.updateEntities(entities);
  }

  /*@Override
  public List<ContractDto> saveEntities(List<ContractDto> entities) {
    List<ContractDto> contractDtos = super.saveEntities(entities);
    contractDtos.forEach(new Consumer<ContractDto>() {
      @Override
      public void accept(ContractDto contractDto) {

      }
    });
  }*/
}
