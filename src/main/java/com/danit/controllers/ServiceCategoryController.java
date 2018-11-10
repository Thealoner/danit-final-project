package com.danit.controllers;

import com.danit.models.ServiceCategory;
import com.danit.models.Services;
import com.danit.services.ServiceCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceCategoryController {
  Logger logger = LoggerFactory.getLogger(ServiceCategoryController.class);

  private ServiceCategoryService serviceCategoryService;

  @Autowired
  public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
    this.serviceCategoryService = serviceCategoryService;
  }

  @PostMapping("/service_categories")
  private void createServiceCategories(@RequestBody List<ServiceCategory> serviceCategories) {
    logger.info("Adding new service category");
    serviceCategoryService.saveServiceCategories(serviceCategories);
    logger.info("Service category saved");
  }

  @GetMapping("/service_categories/{id}")
  ServiceCategory getServiceCategoryById(@PathVariable(name = "id") long id) {
    return serviceCategoryService.getServiceCategoryById(id);
  }

  @PutMapping("/service_categories/{id}")
  public void updateServiceCategory(@RequestBody List<ServiceCategory> serviceCategories) {
    serviceCategoryService.saveServiceCategories(serviceCategories);
  }

  @DeleteMapping("/service_categories")
  public void deleteServiceCategories(@RequestBody List<ServiceCategory> serviceCategories) {
    serviceCategoryService.deleteServiceCategories(serviceCategories);
  }

  @DeleteMapping("/service_categories/{id}")
  public void deleteServiceCategoryById(@PathVariable(name = "id") long id) {
    serviceCategoryService.deleteServiceCategoryById(id);
  }

  @GetMapping("/service_categories")
  List<ServiceCategory> getAllServiceCategories() {
    return serviceCategoryService.getAllServiceCategories();
  }

  @GetMapping("/service_categories/{id}/services")
  List<Services> getAllServiceCategoryServices(@PathVariable(name = "id") long id) {
    return serviceCategoryService.getAllServiceCategoryServices(id);
  }

  //  @DeleteMapping("/service_categories/{servCatId}/services/{serviceId}")
  //  public void deleteServiceCategoryById(@PathVariable(name = "servCatId") long servCatId, @PathVariable(name = "serviceId") long serviceId) {
  //    serviceCategoryService.deleteServiceCategoryService(servCatId, serviceId);
  //  }

}
