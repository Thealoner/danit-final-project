package com.danit.services;

import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import com.danit.repositories.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceCategoryService extends AbstractBaseEntityService<ServiceCategory, ServiceCategoryListRequestDto> {

  private ServicesService servicesService;

  private ServiceCategoryRepository serviceCategoryRepository;

  @Autowired
  public ServiceCategoryService(ServicesService servicesService, ServiceCategoryRepository serviceCategoryRepository) {
    this.servicesService = servicesService;
    this.serviceCategoryRepository = serviceCategoryRepository;
  }

  @Transactional
  public void deleteServiceCategory(List<ServiceCategory> entityList) {
    List<ServiceCategory> serviceCategories = reloadEntities(entityList);
    serviceCategories.forEach(serviceCategory -> serviceCategory.getServices()
            .forEach(service -> service.getServiceCategories().remove(serviceCategory)));
    serviceCategoryRepository.deleteAll(serviceCategories);
  }

  @Transactional
  public void deleteServiceCategotryById(Long id) {
    ServiceCategory serviceCategory = getEntityById(id);
    serviceCategory.getServices().forEach(service -> service.getServiceCategories().remove(serviceCategory));
    serviceCategoryRepository.deleteById(id);
  }


  @Transactional
  public void assignServiceToServiceCategory(Long serviceCategoryId, Long serviceId) {
    getEntityById(serviceCategoryId).getServices().add(servicesService.getEntityById(serviceId));
  }
}
