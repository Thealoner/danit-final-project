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

  @Transactional
  public void assignServicesToServiceCategory(Long serviceCategoryId, List<com.danit.models.Service> services) {
    ServiceCategory serviceCategory = getEntityById(serviceCategoryId);
    servicesService.reloadEntities(services).forEach(service -> service.getServiceCategories().add(serviceCategory));
  }

  @Transactional
  public void deleteServiceFromServiceCategory(Long serviceCategoryId, Long serviceId) {
    getEntityById(serviceCategoryId).getServices().remove(servicesService.getEntityById(serviceId));
    servicesService.getEntityById(serviceId).getServiceCategories().remove(getEntityById(serviceCategoryId));
  }

  @Transactional
  public void deleteServicesFromServiceCategory(Long serviceCategoryId, List<com.danit.models.Service> services) {
    ServiceCategory serviceCategory = getEntityById(serviceCategoryId);
    services.forEach(service -> serviceCategory.getServices().remove(service));
    servicesService.reloadEntities(services).forEach(service -> service.getServiceCategories().remove(serviceCategory));
  }
}
