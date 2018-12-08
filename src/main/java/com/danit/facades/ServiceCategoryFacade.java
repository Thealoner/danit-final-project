package com.danit.facades;

import com.danit.dto.ServiceCategoryDto;
import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryFacade extends AbstractDtoFacade<ServiceCategoryDto,ServiceCategory,
    ServiceCategoryListRequestDto> {
}
