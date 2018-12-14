package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContractService extends AbstractBaseEntityService<Contract, ContractListRequestDto> {

}
