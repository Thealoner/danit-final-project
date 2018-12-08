package com.danit.controllers;

import com.danit.dto.Views;
import com.danit.dto.service.ServiceListRequestDto;
import com.danit.facades.ServiceFacade;
import com.danit.models.Services;
import com.fasterxml.jackson.annotation.JsonView;
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

import static com.danit.utils.ControllerUtils.convertDtoToMap;
import static com.danit.utils.ControllerUtils.convertPageToMap;

@RestController
@RequestMapping("/services")
@Slf4j
public class ServicesController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all services data";
  private ServiceFacade serviceFacade;

  public ServicesController(ServiceFacade serviceFacade) {
    this.serviceFacade = serviceFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<Map<String, Object>> createServicesDto(@RequestBody List<Services> services, Principal principal) {
    log.info(principal.getName() + " is saving new services: " + services);
    return ResponseEntity.ok(convertDtoToMap(serviceFacade.saveEntities(services)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getServiceByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got service data with id: " + id);
    return ResponseEntity.ok(convertDtoToMap(serviceFacade.getEntityById(id)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllServicesDtoIds(Pageable pageable,
                                                                       Principal principal,
                                                                       ServiceListRequestDto serviceListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(serviceFacade.getAllEntities(serviceListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllServicesDtoShort(Pageable pageable,
                                                                       Principal principal,
                                                                       ServiceListRequestDto serviceListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(serviceFacade.getAllEntities(serviceListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllServicesDtoExtended(Pageable pageable,
                                                            Principal principal,
                                                            ServiceListRequestDto serviceListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(serviceFacade.getAllEntities(serviceListRequestDto, pageable)));
  }

  @PutMapping
  public ResponseEntity<Map<String, Object>> updateServices(@RequestBody List<Services> services, Principal principal) {
    log.info(principal.getName() + " is updating services data: " + services);
    return ResponseEntity.ok(convertDtoToMap(serviceFacade.saveEntities(services)));
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
