package com.danit.facades;

import com.danit.dto.ServiceCategoryDto;
import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import com.danit.repositories.specifications.ServiceCategoryListSpecification;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryFacade extends AbstractDtoFacade<ServiceCategoryDto,ServiceCategory, ServiceCategoryListRequestDto> {
}
