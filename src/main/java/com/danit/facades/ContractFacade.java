package com.danit.facades;

import com.danit.dto.ContractDto;
import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import org.springframework.stereotype.Component;

@Component
public class ContractFacade extends AbstractDtoFacade<ContractDto, Contract, ContractListRequestDto> {
}
