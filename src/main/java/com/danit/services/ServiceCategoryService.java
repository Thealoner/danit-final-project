package com.danit.services;

import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import org.springframework.stereotype.Service;

@Service
public class ServiceCategoryService extends AbstractBaseEntityService<ServiceCategory, ServiceCategoryListRequestDto> {
}
