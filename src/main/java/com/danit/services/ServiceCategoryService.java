package com.danit.services;

import com.danit.models.ServiceCategory;
import com.danit.models.Services;

import java.util.List;

public interface ServiceCategoryService {

  List<ServiceCategory> saveServiceCategories(List<ServiceCategory> servicCategories);

  ServiceCategory getServiceCategoryById(Long id);

  void deleteServiceCategoryById(Long id);

  void deleteServiceCategories(List<ServiceCategory> services);

  List<ServiceCategory> getAllServiceCategories();

  List<Services> getAllServiceCategoryServices(Long id);

  void deleteServiceCategoryService(Long servCatId, Long serviceId);

  ServiceCategory saveServiceCategory(ServiceCategory serviceCategory);
}
