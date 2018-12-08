package com.danit.services;

import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import com.danit.models.Services;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCategoryService extends AbstractEntityService<ServiceCategory, ServiceCategoryListRequestDto> {
}
