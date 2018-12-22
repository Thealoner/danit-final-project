package com.danit.controllers;

import com.danit.dto.Views;
import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.facades.ServiceCategoryFacade;
import com.danit.facades.ServiceFacade;
import com.danit.models.Service;
import com.danit.models.ServiceCategory;
import com.danit.services.ServiceCategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_NUMBER;
import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_SIZE;
import static com.danit.utils.ControllerUtils.convertDtoToMap;
import static com.danit.utils.ControllerUtils.convertPageToMap;

@RestController
@RequestMapping("/service_categories")
@Slf4j
public class ServiceCategoryController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all service categories data";
  private ServiceCategoryFacade serviceCategoryFacade;
  private ServiceCategoryService serviceCategoryService;
  private ServiceFacade serviceFacade;

  @Autowired
  public ServiceCategoryController(ServiceCategoryFacade serviceCategoryFacade,
                                   ServiceCategoryService serviceCategoryService,
                                   ServiceFacade serviceFacade) {
    this.serviceCategoryFacade = serviceCategoryFacade;
    this.serviceCategoryService = serviceCategoryService;
    this.serviceFacade = serviceFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<Map<String, Object>> createServiceCategoriesDto(@RequestBody List<ServiceCategory>
                                                                            serviceCategories,
                                                                        Principal principal) {
    log.info(principal.getName() + " is saving new service categories: " + serviceCategories);
    return ResponseEntity.ok(convertDtoToMap(serviceCategoryFacade.saveEntities(serviceCategories)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllServiceCategoriesDtoIds(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ServiceCategoryListRequestDto serviceCategoryListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(serviceCategoryFacade
        .getAllEntities(serviceCategoryListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllServiceCategoriesDtoShort(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ServiceCategoryListRequestDto serviceCategoryListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(serviceCategoryFacade
        .getAllEntities(serviceCategoryListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllServiceCategoriesDtoExtended(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ServiceCategoryListRequestDto serviceCategoryListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity
        .ok(convertPageToMap(serviceCategoryFacade.getAllEntities(serviceCategoryListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getServiceCategoryByIdDto(@PathVariable(name = "id") long id,
                                                                       Principal principal) {
    log.info(principal.getName() + " got service categories data with id: " + id);
    return ResponseEntity.ok(convertDtoToMap(serviceCategoryFacade.getEntityById(id)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  public ResponseEntity<Map<String, Object>> updateServiceCategoriesDto(
      @RequestBody List<ServiceCategory> serviceCategories,
      Principal principal) {
    log.info(principal.getName() + " is updating service categories data: " + serviceCategories);
    return ResponseEntity.ok(convertDtoToMap(serviceCategoryFacade.updateEntities(serviceCategories)));
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping
  void deleteServiceCategories(@RequestBody List<ServiceCategory> serviceCategories, Principal principal) {
    log.info(principal.getName() + " try to delete service category data: " + serviceCategories);
    serviceCategoryService.deleteServiceCategory(serviceCategories);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  void deleteServiceCategoryById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " try to delete service category with id: " + id);
    serviceCategoryService.deleteServiceCategotryById(id);
  }

  //------related entities methods--------------------------------------------------------------------------------------

  //Services

  @JsonView(Views.Ids.class)
  @GetMapping("{serviceCategoryId}/services/ids")
  ResponseEntity<Map<String, Object>> getAllServiceCategoryServicesIds(@PathVariable(name = "serviceCategoryId")
                                                                           long id,
                                                                       @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
                                                                       @SortDefault.SortDefaults({
                                                                           @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                                                       }) Pageable pageable,
                                                                       Principal principal) {
    log.info(principal.getName() + " got service categories services data with id: " + id);
    return ResponseEntity.ok(convertPageToMap(serviceFacade
        .findAllServicesDtoForServiceCategoryId(id, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping("{serviceCategoryId}/services/short")
  ResponseEntity<Map<String, Object>> getAllServiceCategoryServicesShort(@PathVariable(name = "serviceCategoryId")
                                                                             long id,
                                                                         @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
                                                                         @SortDefault.SortDefaults({
                                                                             @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                                                         }) Pageable pageable,
                                                                         Principal principal) {
    log.info(principal.getName() + " got service categories services data with id: " + id);
    return ResponseEntity.ok(convertPageToMap(serviceFacade
        .findAllServicesDtoForServiceCategoryId(id, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("{serviceCategoryId}/services")
  ResponseEntity<Map<String, Object>> getAllServiceCategoryServicesExtended(@PathVariable(name = "serviceCategoryId")
                                                                                long id,
                                                                            @PageableDefault(page = DEFAULT_PAGE_NUMBER,
                                                                                size = DEFAULT_PAGE_SIZE)
                                                                            @SortDefault.SortDefaults({
                                                                                @SortDefault(sort = "id",
                                                                                    direction = Sort.Direction.ASC)
                                                                            }) Pageable pageable,
                                                                            Principal principal) {
    log.info(principal.getName() + " got service categories services data with id: " + id);
    return ResponseEntity.ok(convertPageToMap(serviceFacade
        .findAllServicesDtoForServiceCategoryId(id, pageable)));
  }

  @PutMapping("/{serviceCategoryId}/service/{serviceId}")
  @ResponseStatus(HttpStatus.OK)
  void assignServiceToServiceCategory(@PathVariable(name = "serviceCategoryId") Long serviceCategoryId,
                                      @PathVariable(name = "serviceId") Long serviceId,
                                      Principal principal) {
    log.info(principal.getName() + " is trying to assign serviceId=" + serviceId +
        " to serviceCategoryId = " + serviceCategoryId);
    serviceCategoryService.assignServiceToServiceCategory(serviceCategoryId, serviceId);
  }

  @PutMapping("/{serviceCategoryId}/services")
  @ResponseStatus(HttpStatus.OK)
  void assignServicesToServiceCategory(@PathVariable(name = "serviceCategoryId") Long serviceCategoryId,
                                       @RequestBody List<Service> services,
                                       Principal principal) {
    log.info(principal.getName() + " is trying to assign services" + services + " to serviceCategoryId = " +
        serviceCategoryId);
    serviceCategoryService.assignServicesToServiceCategory(serviceCategoryId, services);
  }

  @DeleteMapping("/{serviceCategoryId}/service/{serviceId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteServiceFromServiceCategory(@PathVariable(name = "serviceCategoryId") Long serviceCategoryId,
                                        @PathVariable(name = "serviceId") Long serviceId,
                                        Principal principal) {
    log.info(principal.getName() + " is trying to delete serviceId=" + serviceId +
        " from serviceCategoryId = " + serviceCategoryId);
    serviceCategoryService.deleteServiceFromServiceCategory(serviceCategoryId, serviceId);
  }

  @DeleteMapping("/{serviceCategoryId}/services")
  @ResponseStatus(HttpStatus.OK)
  void deleteServicesFromServiceCategory(@PathVariable(name = "serviceCategoryId") Long serviceCategoryId,
                                         @RequestBody List<Service> services,
                                         Principal principal) {
    log.info(principal.getName() + " is trying to assign services" + services + " to serviceCategoryId = " +
        serviceCategoryId);
    serviceCategoryService.deleteServicesFromServiceCategory(serviceCategoryId, services);
  }


}
