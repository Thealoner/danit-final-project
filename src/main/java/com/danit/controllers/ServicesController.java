package com.danit.controllers;

import com.danit.models.Services;
import com.danit.services.ServicesService;
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
public class ServicesController {

  Logger logger = LoggerFactory.getLogger(ServicesController.class);

  private ServicesService servicesService;

  @Autowired
  public ServicesController(ServicesService servicesService) {
    this.servicesService = servicesService;
  }

  @PostMapping("/services")
  @ResponseStatus(HttpStatus.CREATED)
  public void createServices(@RequestBody List<Services> services) {
    logger.info(getCurrentPrincipalName() + " is saving new services: " + services);
    servicesService.saveServices(services);
  }

  @GetMapping("/services/{id}")
  Services getServiceById(@PathVariable(name = "id") long id) {
    logger.info(getCurrentPrincipalName() + " got service data with id: " + id);
    return servicesService.getServiceById(id);
  }

  @GetMapping("/services")
  List<Services> getAllServices() {
    logger.info(getCurrentPrincipalName() + " got all services data");
    return servicesService.getAllServices();
  }

  @PutMapping("/services")
  public void updateServices(@RequestBody List<Services> services) {
    logger.info(getCurrentPrincipalName() + " is updating services data: " + services);
    servicesService.saveServices(services);
  }

  @PutMapping("/services/{id}")
  public void updateService(@PathVariable(name = "id") long id,@RequestBody Services service) {
    logger.info(getCurrentPrincipalName() + " is updating service data: " + service);
    servicesService.saveService(service);
  }

  @DeleteMapping("/services/{id}")
  public void deleteServiceById(@PathVariable(name = "id") long id) {
    logger.info(getCurrentPrincipalName() + " try to delete service with id: " + id);
    servicesService.deleteServiceById(id);
  }

  @DeleteMapping("/services")
  public void deleteServices(@RequestBody List<Services> services) {
    logger.info(getCurrentPrincipalName() + " is trying to delete services: " + services);
    servicesService.deleteServices(services);
  }



}
