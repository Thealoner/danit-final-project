package com.danit.services;

import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Service;
import com.danit.repositories.ServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Service
public class ServicesService extends AbstractBaseEntityService<Service, ServiceListRequestDto> {

  private ServiceRepository serviceRepository;

  public ServicesService(ServiceRepository serviceRepository) {
    this.serviceRepository = serviceRepository;
  }

  public Page<Service> findAllServicesForServiceCategoryId(Long serviceCategoryId, Pageable pageable) {
    return serviceRepository.findAllServicesForServiceCategoryId(serviceCategoryId, pageable);
  }
}
