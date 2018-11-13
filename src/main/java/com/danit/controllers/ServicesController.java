package com.danit.controllers;

import com.danit.models.Services;
import com.danit.services.ServicesService;
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
public class ServicesController {

  private Logger logger = LoggerFactory.getLogger(ServicesController.class);

  private ServicesService servicesService;

  @Autowired
  public ServicesController(ServicesService servicesService) {
    this.servicesService = servicesService;
  }

  @PostMapping("/services")
  List<Services> createServices(@RequestBody List<Services> services, Principal principal) {
    logger.info(principal.getName() + " is saving new services: " + services);
    return servicesService.saveServices(services);
  }

  @GetMapping("/services/{id}")
  Services getServiceById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got service data with id: " + id);
    return servicesService.getServiceById(id);
  }

  @GetMapping("/services")
  List<Services> getAllServices(Principal principal) {
    logger.info(principal.getName() + " got all services data");
    return servicesService.getAllServices();
  }

  @PutMapping("/services")
  void updateServices(@RequestBody List<Services> services, Principal principal) {
    logger.info(principal.getName() + " is updating services data: " + services);
    servicesService.saveServices(services);
  }

  @PutMapping("/services/{id}")
  void updateService(@PathVariable(name = "id") long id, @RequestBody Services service, Principal principal) {
    logger.info(principal.getName() + " is updating service data: " + service);
    servicesService.saveService(service);
  }

  @DeleteMapping("/services/{id}")
  void deleteServiceById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete service with id: " + id);
    servicesService.deleteServiceById(id);
  }

  @DeleteMapping("/services")
  void deleteServices(@RequestBody List<Services> services, Principal principal) {
    logger.info(principal.getName() + " is trying to delete services: " + services);
    servicesService.deleteServices(services);
  }


}
