package com.danit.services;

import com.danit.models.ServiceCategory;
import com.danit.models.Services;

import java.util.List;

public interface ServiceCategoryService {

  void saveServiceCategories(List<ServiceCategory> services);

  ServiceCategory getServiceCategoryById(Long id);

  void deleteServiceCategoryById(Long id);

  void deleteServiceCategories(List<ServiceCategory> services);

  List<ServiceCategory> getAllServiceCategories();

  List<Services> getAllServiceCategoryServices(Long id);

  //  void deleteServiceCategoryService(Long servCatId, Long serviceId);
}
