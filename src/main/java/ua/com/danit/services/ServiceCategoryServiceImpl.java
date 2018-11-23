package ua.com.danit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.danit.models.ServiceCategory;
import ua.com.danit.models.Services;
import ua.com.danit.repositories.ServiceCategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

  private ServiceCategoryRepository serviceCategoryRepository;

  private ServicesService servicesService;

  @Autowired
  public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository, ServicesService servicesService) {
    this.serviceCategoryRepository = serviceCategoryRepository;
    this.servicesService = servicesService;
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
  public void deleteServiceCategoryService(Long serviceCatId, Long serviceId) {
    Services service = servicesService.getServiceById(serviceId);
    service.getServiceCategories().removeIf(el -> el.getId().equals(serviceCatId));
  }

  @Override
  public ServiceCategory saveServiceCategory(ServiceCategory serviceCategory) {
    return serviceCategoryRepository.save(serviceCategory);
  }

  @Override
  public int getTotalQuantityOfServiceCategories() {
    return serviceCategoryRepository.getTotalQuantityOfServiceCategories();
  }
}
