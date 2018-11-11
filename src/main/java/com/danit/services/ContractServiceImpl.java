package com.danit.services;

import com.danit.models.Contract;
import com.danit.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class ContractServiceImpl implements ContractService {

  private ContractRepository contractRepository;

  @Autowired
  public ContractServiceImpl(ContractRepository contractRepository) {
    this.contractRepository = contractRepository;
  }

  @Override
  public List<Contract> getAllContracts() {
    return contractRepository.findAll();
  }

  @Override
  public Contract getContractById(long id) {
    return contractRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find Contract with id=" + id));
  }

  @Override
  public List<Contract> saveContracts(List<Contract> contracts) {
    return contractRepository.saveAll(contracts);
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
    contractRepository.deleteInBatch(contracts);
  }

}
