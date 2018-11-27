package com.danit.facades;

import com.danit.dto.ContractDto;
import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import com.danit.services.ContractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContractFacadeImpl implements ContractFacade{

  private final ContractService contractService;

  private final ModelMapper modelMapper;

  @Autowired
  public ContractFacadeImpl(ContractService contractService, ModelMapper modelMapper) {
    this.contractService = contractService;
    this.modelMapper = modelMapper;
  }

  @Override
  public ContractDto convertToDto(Contract contract) {
    return modelMapper.map(contract, ContractDto.class);
  }

  private List<ContractDto> convertToDtos(List<Contract> contracts) {
    List<ContractDto> dtoContracts = new ArrayList<>();
    contracts.forEach(contract ->
        dtoContracts.add(modelMapper.map(contract, ContractDto.class)));
    return dtoContracts;
  }

  private Page<ContractDto> convertToDtos(Page<Contract> contracts) {
    return contracts.map(this::convertToDto);
  }

  @Override
  public List<ContractDto> saveContracts(List<Contract> contracts) {
    return convertToDtos(contractService.saveContracts(contracts));
  }

  @Override
  public ContractDto getContractById(Long id) {
    return convertToDto(contractService.getContractById(id));
  }

  @Override
  public List<ContractDto> updateContracts(List<Contract> contracts) {
    return convertToDtos(contractService.saveContracts(contracts));
  }

  @Override
  public void deleteContractById(Long id) {
    contractService.deleteContractById(id);
  }

  @Override
  public void deleteContracts(List<Contract> contracts) {
    contractService.deleteContracts(contracts);
  }

  @Override
  public Page<ContractDto> getAllContracts(Pageable pageable) {
    return convertToDtos(contractService.getAllContracts(pageable));
  }

  @Override
  public Page<ContractDto> getAllContracts(ContractListRequestDto contractListRequestDto, Pageable pageable) {
    return convertToDtos(contractService.getAllContracts(contractListRequestDto, pageable));
  }
}
