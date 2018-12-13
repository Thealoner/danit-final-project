package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContractService extends AbstractBaseEntityService<Contract, ContractListRequestDto> {
  @Override
  public List<Contract> updateEntities(List<Contract> entityList) {
    entityList.get(0).getCards().remove(0);
    return super.updateEntities(entityList);
  }
}
