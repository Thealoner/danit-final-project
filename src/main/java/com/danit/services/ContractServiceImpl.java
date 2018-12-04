package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import com.danit.repositories.ContractRepository;
import com.danit.repositories.specifications.ContractListSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class ContractServiceImpl implements ContractService {

  private ContractRepository contractRepository;
  private ContractListSpecification contractListSpecification;

  @Autowired
  public ContractServiceImpl(ContractRepository contractRepository, ContractListSpecification contractListSpecification) {
    this.contractRepository = contractRepository;
    this.contractListSpecification = contractListSpecification;
  }

  @Override
  public Contract getContractById(long id) {
    return contractRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find Contract with id=" + id));
  }

  @Override
  public List<Contract> saveContracts(List<Contract> contracts) {
    return (List<Contract>)contractRepository.saveAll(contracts);
  }

  @Override
  public void deleteContractById(long id) {
    contractRepository.deleteById(id);
  }

  @Override
  public void deleteContracts(List<Contract> contracts) {
    Set<Long> contractsId = contractRepository.getAllContractsId();
    contracts.forEach(contract -> {
      if (!contractsId.contains(contract.getId())) {
        throw new EntityNotFoundException("Contract with id=" + contract.getId() + " is not exist");
      }
    });
    contractRepository.deleteAll(contracts);
  }

  @Override
  public Page<Contract> getAllContracts(Pageable pageable) {
    return contractRepository.findAll(pageable);
  }

  @Override
  public Page<Contract> getAllContracts(ContractListRequestDto contractListRequestDto, Pageable pageable) {
    return contractRepository.findAll(contractListSpecification.getFilter(contractListRequestDto), pageable);
  }

}
