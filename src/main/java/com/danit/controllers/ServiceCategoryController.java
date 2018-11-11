package com.danit.controllers;

import com.danit.models.ServiceCategory;
import com.danit.models.Services;
import com.danit.services.ServiceCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.danit.utils.SpringSecurityUtils.getCurrentPrincipalName;

@RestController
public class ServiceCategoryController {
  Logger logger = LoggerFactory.getLogger(ServiceCategoryController.class);

  private ServiceCategoryService serviceCategoryService;

  @Autowired
  public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
    this.serviceCategoryService = serviceCategoryService;
  }

  @PostMapping("/service_categories")
  @ResponseStatus(HttpStatus.CREATED)
  public void createServiceCategories(@RequestBody List<ServiceCategory> serviceCategories) {
    logger.info(getCurrentPrincipalName() + " is saving new service categories: " + serviceCategories);
    serviceCategoryService.saveServiceCategories(serviceCategories);
  }

  @GetMapping("/service_categories")
  List<ServiceCategory> getAllServiceCategories() {
    logger.info(getCurrentPrincipalName() + " got all service categories data");
    return serviceCategoryService.getAllServiceCategories();
  }

  @GetMapping("/service_categories/{id}")
  ServiceCategory getServiceCategoryById(@PathVariable(name = "id") long id) {
    logger.info(getCurrentPrincipalName() + " got service categories data with id: " + id);
    return serviceCategoryService.getServiceCategoryById(id);
  }

  @PutMapping("/service_categories")
  public void updateServiceCategories(@RequestBody List<ServiceCategory> serviceCategories) {
    logger.info(getCurrentPrincipalName() + " is updating service categories data: " + serviceCategories);
    serviceCategoryService.saveServiceCategories(serviceCategories);
  }

  @PutMapping("/service_categories/{id}")
  public void updateServiceCategory(@RequestBody ServiceCategory serviceCategory) {
    logger.info(getCurrentPrincipalName() + " is updating service category data: " + serviceCategory);
    serviceCategoryService.saveServiceCategory(serviceCategory);
  }

  @DeleteMapping("/service_categories")
  public void deleteServiceCategories(@RequestBody List<ServiceCategory> serviceCategories) {
    logger.info(getCurrentPrincipalName() + " try to delete service category data: " + serviceCategories);
    serviceCategoryService.deleteServiceCategories(serviceCategories);
  }

  @DeleteMapping("/service_categories/{id}")
  public void deleteServiceCategoryById(@PathVariable(name = "id") long id) {
    logger.info(getCurrentPrincipalName() + " try to delete service category with id: " + id);
    serviceCategoryService.deleteServiceCategoryById(id);
  }

  @DeleteMapping("/service_categories/{servCatId}/services/{serviceId}")
  public void deleteServiceCategoryById(@PathVariable(name = "servCatId") long servCatId,
                                        @PathVariable(name = "serviceId") long serviceId) {
    logger.info(getCurrentPrincipalName() + " try to delete service with id: " + serviceId
        + " in service category with id: " + servCatId);
    serviceCategoryService.deleteServiceCategoryService(servCatId, serviceId);
  }

  @GetMapping("/service_categories/{id}/services")
  List<Services> getAllServiceCategoryServices(@PathVariable(name = "id") long id) {
    logger.info(getCurrentPrincipalName() + " got services from service category with id: " + id);
    return serviceCategoryService.getAllServiceCategoryServices(id);
  }

}
