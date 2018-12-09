package com.danit.facades;

import com.danit.dto.ServiceDto;
import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Services;
import org.springframework.stereotype.Component;

@Component
public class ServiceFacade extends AbstractDtoFacade<ServiceDto, Services, ServiceListRequestDto> {
}
