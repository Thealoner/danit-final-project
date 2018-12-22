package com.danit.facades;

import com.danit.dto.ServiceCategoryDto;
import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import com.danit.services.ServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryFacade extends AbstractDtoFacade<ServiceCategoryDto, ServiceCategory,
    ServiceCategoryListRequestDto> {

  private ServiceCategoryService serviceCategoryService;

  @Autowired
  public ServiceCategoryFacade(ServiceCategoryService serviceCategoryService) {
    this.serviceCategoryService = serviceCategoryService;
  }

  public Page<ServiceCategoryDto> findAllServiceCategoriesOfServiceWithId(long id, Pageable pageable) {
    return convertToDtos(serviceCategoryService.findAllServiceCategoriesOfServiceWithId(id, pageable));
  }
}
