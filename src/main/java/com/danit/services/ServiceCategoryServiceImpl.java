package com.danit.services;

import com.danit.models.ServiceCategory;
import com.danit.models.Services;
import com.danit.repositories.ServiceCategoryRepository;
import com.danit.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

  private ServiceCategoryRepository serviceCategoryRepository;

  private ServiceRepository serviceRepository;

  @Autowired
  public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository) {
    this.serviceCategoryRepository = serviceCategoryRepository;
  }

  @Override
  public void saveServiceCategories(List<ServiceCategory> services) {
    serviceCategoryRepository.saveAll(services);
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

  //  @Override
  //  public void deleteServiceCategoryService(Long servCatId, Long serviceId) {
  //    serviceCategoryRepository.deleteServiceCategoryService(servCatId, serviceId);
  //  }
}
