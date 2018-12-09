package com.danit.repositories.specifications;

import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Services;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ServiceListSpecification extends BaseSpecification<Services, ServiceListRequestDto> {
  @Override
  public Specification<Services> getFilter(ServiceListRequestDto request) {
    return null;
  }
}
