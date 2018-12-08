package com.danit.controllers;

import com.danit.dto.ServiceCategoryDto;
import com.danit.dto.ServiceDto;
import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.facades.ServiceCategoryFacade;
import com.danit.models.ServiceCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static com.danit.utils.ControllerUtils.convertToMap;

@RestController
@RequestMapping("/service_categories")
@Slf4j
public class ServiceCategoryController {

  private ServiceCategoryFacade serviceCategoryFacade;

  public ServiceCategoryController(ServiceCategoryFacade serviceCategoryFacade) {
    this.serviceCategoryFacade = serviceCategoryFacade;
  }

  @PostMapping
  public ResponseEntity<List<ServiceCategoryDto>> createServiceCategories(@RequestBody List<ServiceCategory> serviceCategories, Principal principal) {
    log.info(principal.getName() + " is saving new service categories: " + serviceCategories);
    return ResponseEntity.ok(serviceCategoryFacade.saveEntities(serviceCategories));
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllServiceCategories(Pageable pageable,
                                                                     Principal principal,
                                                                     ServiceCategoryListRequestDto serviceCategoryListRequestDto) {
    log.info(principal.getName() + " got all service categories data");
    return ResponseEntity.ok(convertToMap(serviceCategoryFacade.getAllEntities(serviceCategoryListRequestDto,pageable)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ServiceCategoryDto> getServiceCategoryById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got service categories data with id: " + id);
    return serviceCategoryService.getServiceCategoryById(id);
  }

  @PutMapping
  List<ServiceCategory> updateServiceCategories(@RequestBody List<ServiceCategory> serviceCategories, Principal principal) {
    logger.info(principal.getName() + " is updating service categories data: " + serviceCategories);
    return serviceCategoryService.saveServiceCategories(serviceCategories);
  }

  @PutMapping("/{id}")
  ServiceCategory updateServiceCategory(@RequestBody ServiceCategory serviceCategory, Principal principal) {
    logger.info(principal.getName() + " is updating service category data: " + serviceCategory);
    return serviceCategoryService.saveServiceCategory(serviceCategory);
  }

  @DeleteMapping
  void deleteServiceCategories(@RequestBody List<ServiceCategory> serviceCategories, Principal principal) {
    logger.info(principal.getName() + " try to delete service category data: " + serviceCategories);
    serviceCategoryService.deleteServiceCategories(serviceCategories);
  }

  @DeleteMapping("/{id}")
  void deleteServiceCategoryById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete service category with id: " + id);
    serviceCategoryService.deleteServiceCategoryById(id);
  }

  @DeleteMapping("/{servCatId}/services/{serviceId}")
  void deleteServiceCategoryById(@PathVariable(name = "servCatId") long servCatId,
                                 @PathVariable(name = "serviceId") long serviceId,
                                 Principal principal) {
    logger.info(principal.getName() + " try to delete service with id: " + serviceId
        + " in service category with id: " + servCatId);
    serviceCategoryService.deleteServiceCategoryService(servCatId, serviceId);
  }

//  @GetMapping("/{id}/services")
//  List<Services> getAllServiceCategoryServices(@PathVariable(name = "id") long id, Principal principal) {
//    logger.info(principal.getName() + " got services from service category with id: " + id);
//    return serviceCategoryService.getAllServiceCategoryServices(id);
//  }

}
