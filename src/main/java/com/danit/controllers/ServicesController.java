package com.danit.controllers;

import com.danit.dto.ServiceDto;
import com.danit.dto.service.ServiceListRequestDto;
import com.danit.facades.ServiceFacade;
import com.danit.models.Services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

import static com.danit.utils.ControllerUtils.convertToMap;

@RestController
@RequestMapping("/services")
@Slf4j
public class ServicesController {

  private ServiceFacade serviceFacade;

  public ServicesController(ServiceFacade serviceFacade) {
    this.serviceFacade = serviceFacade;
  }

  @PostMapping
  public ResponseEntity<List<ServiceDto>> createServices(@RequestBody List<Services> services, Principal principal) {
    log.info(principal.getName() + " is saving new services: " + services);
    return ResponseEntity.status(HttpStatus.CREATED).body(serviceFacade.saveEntities(services));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ServiceDto> getServiceById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got service data with id: " + id);
    return ResponseEntity.ok(serviceFacade.getEntityById(id));
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllServices(Pageable pageable,
                                                            Principal principal,
                                                            ServiceListRequestDto serviceListRequestDto) {
    log.info(principal.getName() + " got all services data");
    return ResponseEntity.ok(convertToMap(serviceFacade.getAllEntities(serviceListRequestDto, pageable)));
  }

  @PutMapping
  public ResponseEntity<List<ServiceDto>> updateServices(@RequestBody List<Services> services, Principal principal) {
    log.info(principal.getName() + " is updating services data: " + services);
    return ResponseEntity.ok(serviceFacade.saveEntities(services));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  void deleteServiceById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " try to delete service with id: " + id);
    serviceFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  void deleteServices(@RequestBody List<Services> services, Principal principal) {
    log.info(principal.getName() + " is trying to delete services: " + services);
    serviceFacade.deleteEntities(services);
  }


}
