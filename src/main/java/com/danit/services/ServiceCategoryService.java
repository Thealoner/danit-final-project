package com.danit.services;

import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import com.danit.repositories.ServiceCategoryRepository;
import com.danit.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ServiceCategoryService extends AbstractBaseEntityService<ServiceCategory, ServiceCategoryListRequestDto> {

  private ServicesService servicesService;

  private ServiceCategoryRepository serviceCategoryRepository;

  private ServiceRepository serviceRepository;

  @Autowired
  public ServiceCategoryService(ServicesService servicesService, ServiceCategoryRepository serviceCategoryRepository,
                                ServiceRepository serviceRepository) {
    this.servicesService = servicesService;
    this.serviceCategoryRepository = serviceCategoryRepository;
    this.serviceRepository = serviceRepository;
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
    ServiceCategory serviceCategory = getEntityById(serviceCategoryId);

    Boolean containsService = serviceCategory.getServices()
        .stream().filter(s -> s.getId() == serviceId)
        .map(com.danit.models.Service::getId).findFirst().isPresent();

    if (!containsService) {
      servicesService.getEntityById(serviceId).getServiceCategories().add(serviceCategory);
    }
  }

  @Transactional
  public void assignServicesToServiceCategory(Long serviceCategoryId, List<com.danit.models.Service> services) {

    ServiceCategory serviceCategory = getEntityById(serviceCategoryId);

    List<Long> scServicesIds = serviceCategory.getServices()
        .stream().map(com.danit.models.Service::getId).collect(Collectors.toList());

    List<Long> targetIds =
        services.stream()
            .map(com.danit.models.Service::getId)
            .filter(isId -> !scServicesIds.contains(isId))
            .collect(Collectors.toList());

    serviceRepository.findAllEntitiesByIds(targetIds)
        .forEach(service -> service.getServiceCategories().add(serviceCategory));

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

  public Page<ServiceCategory> findAllServiceCategoriesOfServiceWithId(long id, Pageable pageable) {
    return serviceCategoryRepository.findAllServiceCategoriesOfServiceWithId(id, pageable);
  }
}
