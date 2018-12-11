package com.danit.services;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import org.springframework.stereotype.Service;

@Service
public class ContractService extends AbstractBaseEntityService<Contract, ContractListRequestDto> {
}
