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

import java.security.Principal;
import java.util.List;

@RestController
public class ServiceCategoryController {

  private Logger logger = LoggerFactory.getLogger(ServiceCategoryController.class);

  private ServiceCategoryService serviceCategoryService;

  @Autowired
  public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
    this.serviceCategoryService = serviceCategoryService;
  }

  @PostMapping("/service_categories")
  List<ServiceCategory> createServiceCategories(@RequestBody List<ServiceCategory> serviceCategories, Principal principal) {
    logger.info(principal.getName() + " is saving new service categories: " + serviceCategories);
    return serviceCategoryService.saveServiceCategories(serviceCategories);
  }

  @GetMapping("/service_categories")
  List<ServiceCategory> getAllServiceCategories(Principal principal) {
    logger.info(principal.getName() + " got all service categories data");
    return serviceCategoryService.getAllServiceCategories();
  }

  @GetMapping("/service_categories/{id}")
  ServiceCategory getServiceCategoryById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got service categories data with id: " + id);
    return serviceCategoryService.getServiceCategoryById(id);
  }

  @PutMapping("/service_categories")
  List<ServiceCategory> updateServiceCategories(@RequestBody List<ServiceCategory> serviceCategories, Principal principal) {
    logger.info(principal.getName() + " is updating service categories data: " + serviceCategories);
    return serviceCategoryService.saveServiceCategories(serviceCategories);
  }

  @PutMapping("/service_categories/{id}")
  ServiceCategory updateServiceCategory(@RequestBody ServiceCategory serviceCategory, Principal principal) {
    logger.info(principal.getName() + " is updating service category data: " + serviceCategory);
    return serviceCategoryService.saveServiceCategory(serviceCategory);
  }

  @DeleteMapping("/service_categories")
  void deleteServiceCategories(@RequestBody List<ServiceCategory> serviceCategories, Principal principal) {
    logger.info(principal.getName() + " try to delete service category data: " + serviceCategories);
    serviceCategoryService.deleteServiceCategories(serviceCategories);
  }

  @DeleteMapping("/service_categories/{id}")
  void deleteServiceCategoryById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete service category with id: " + id);
    serviceCategoryService.deleteServiceCategoryById(id);
  }

  @DeleteMapping("/service_categories/{servCatId}/services/{serviceId}")
  void deleteServiceCategoryById(@PathVariable(name = "servCatId") long servCatId,
                                 @PathVariable(name = "serviceId") long serviceId,
                                 Principal principal) {
    logger.info(principal.getName() + " try to delete service with id: " + serviceId
        + " in service category with id: " + servCatId);
    serviceCategoryService.deleteServiceCategoryService(servCatId, serviceId);
  }

  @GetMapping("/service_categories/{id}/services")
  List<Services> getAllServiceCategoryServices(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got services from service category with id: " + id);
    return serviceCategoryService.getAllServiceCategoryServices(id);
  }

}
