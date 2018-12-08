package com.danit.repositories.specifications;

import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryListSpecification extends BaseSpecification<ServiceCategory, ServiceCategoryListRequestDto> {
  @Override
  public Specification<ServiceCategory> getFilter(ServiceCategoryListRequestDto request) {
    return null;
  }
}
