package com.danit.services;

import com.danit.models.ServiceCategory;
import com.danit.models.Services;
import com.danit.repositories.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

  private ServiceCategoryRepository serviceCategoryRepository;

  @Autowired
  private ServicesService servicesService;

  @Autowired
  public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository) {
    this.serviceCategoryRepository = serviceCategoryRepository;
  }

  @Override
  public List<ServiceCategory> saveServiceCategories(List<ServiceCategory> services) {
    return serviceCategoryRepository.saveAll(services);
  }

  @Override
  public ServiceCategory getServiceCategoryById(Long id) {
    return serviceCategoryRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find ServiceCategory with id=" + id));
  }

  @Override
  public void deleteServiceCategoryById(Long id) {
    serviceCategoryRepository.deleteById(id);
  }

  @Override
  public void deleteServiceCategories(List<ServiceCategory> serviceCategories) {
    serviceCategoryRepository.deleteInBatch(serviceCategories);
  }

  @Override
  public List<ServiceCategory> getAllServiceCategories() {
    return serviceCategoryRepository.findAll();
  }

  @Override
  public List<Services> getAllServiceCategoryServices(Long id) {
    ServiceCategory serviceCategory = serviceCategoryRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find ServiceCategory with id=" + id));

    return serviceCategory.getServices();
  }

  @Override
  @Transactional
  public void deleteServiceCategoryService(Long servCatId, Long serviceId) {
    ServiceCategory serviceCategory = getServiceCategoryById(servCatId);
    List<Services> services = serviceCategory.getServices();
    Services service = servicesService.getServiceById(serviceId);
    services.remove(service);
  }

  @Override
  public ServiceCategory saveServiceCategory(ServiceCategory serviceCategory) {
    return serviceCategoryRepository.save(serviceCategory);
  }
}
