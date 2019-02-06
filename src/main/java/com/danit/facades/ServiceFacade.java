package com.danit.facades;

import com.danit.dto.ServiceDto;
import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Service;
import com.danit.services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ServiceFacade extends AbstractDtoFacade<ServiceDto, Service, ServiceListRequestDto> {

  private ServicesService servicesService;

  @Autowired
  public ServiceFacade(ServicesService servicesService) {
    this.servicesService = servicesService;
  }

  public Page<ServiceDto> findAllServicesDtoForServiceCategoryId(Long id, Pageable pageable) {
    return convertToDtos(servicesService.findAllServicesForServiceCategoryId(id, pageable));
  }
}
