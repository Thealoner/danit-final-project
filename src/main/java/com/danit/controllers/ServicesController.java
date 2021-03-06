package com.danit.controllers;

import com.danit.dto.ServiceDto;
import com.danit.dto.Views;
import com.danit.dto.service.ServiceListRequestDto;
import com.danit.facades.ServiceCategoryFacade;
import com.danit.facades.ServiceFacade;
import com.danit.models.Service;
import com.fasterxml.jackson.annotation.JsonView;
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
@RequestMapping("/services")
public class ServicesController {

  private ServiceFacade serviceFacade;
  private ServiceCategoryFacade serviceCategoryFacade;

  public ServicesController(ServiceFacade serviceFacade, ServiceCategoryFacade serviceCategoryFacade) {
    this.serviceFacade = serviceFacade;
    this.serviceCategoryFacade = serviceCategoryFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<Map<String, Object>> createServices(@RequestBody List<ServiceDto> services, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(serviceFacade.saveEntities(services)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getServiceByIdDto(@PathVariable(name = "id") Long id, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(serviceFacade.getEntityById(id)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllServicesDtoIds(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ServiceListRequestDto serviceListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(serviceFacade.getAllEntities(serviceListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllServicesDtoShort(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ServiceListRequestDto serviceListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(serviceFacade.getAllEntities(serviceListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllServicesDtoExtended(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      ServiceListRequestDto serviceListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(serviceFacade.getAllEntities(serviceListRequestDto, pageable)));
  }

  @PutMapping
  public ResponseEntity<Map<String, Object>> updateServices(@RequestBody List<ServiceDto> services, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(serviceFacade.updateEntities(services)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  void deleteServiceById(@PathVariable(name = "id") Long id, Principal principal) {
    serviceFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  void deleteServices(@RequestBody List<Service> services, Principal principal) {
    serviceFacade.deleteEntities(services);
  }

  @JsonView(Views.Short.class)
  @GetMapping("/{serviceId}/service_category")
  ResponseEntity<Map<String, Object>> getAllServiceCategoriesForServiceId(
      @PathVariable(name = "serviceId") Long serviceId,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER,
          size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id",
              direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(serviceCategoryFacade
        .findAllServiceCategoriesOfServiceWithId(serviceId, pageable)));
  }

}
