package com.danit.services;

import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Services;
import org.springframework.stereotype.Service;

@Service
public class ServicesService extends AbstractEntityService<Services, ServiceListRequestDto> {
}
