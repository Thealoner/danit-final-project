package com.danit.repositories.specifications;

import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ServiceListSpecification extends BaseSpecification<Service, ServiceListRequestDto> {
  @Override
  public Specification<Service> getFilter(ServiceListRequestDto request) {
    return null;
  }
}
