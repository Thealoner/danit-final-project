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

import java.util.List;

@RestController
public class ServicesController {

  Logger logger = LoggerFactory.getLogger(ServicesController.class);

  private ServicesService servicesService;

  @Autowired
  public ServicesController(ServicesService servicesService) {
    this.servicesService = servicesService;
  }

  @PostMapping("/services")
  private void createServices(@RequestBody List<Services> services) {
    logger.info("Adding new service");
    servicesService.saveServices(services);
    logger.info("Services saved");
  }

  @GetMapping("/services/{id}")
  Services getServiceById(@PathVariable(name = "id") long id) {
    return servicesService.getServiceById(id);
  }

  @PutMapping("/services")
  public void addServices(@RequestBody List<Services> services) {
    servicesService.saveServices(services);
  }

  @DeleteMapping("/services/{id}")
  public void deleteServiceById(@PathVariable(name = "id") long id) {
    servicesService.deleteServiceById(id);
  }

  @DeleteMapping("/services")
  public void deleteServices(@RequestBody List<Services> services) {
    servicesService.deleteServices(services);
  }

  @GetMapping("/services")
  List<Services> getAllServices() {
    return servicesService.getAllServices();
  }

}
