package com.danit.services;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.Services;
import com.danit.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServicesServiceImpl implements ServicesService {

  private ServiceRepository serviceRepository;

  @Autowired
  public ServicesServiceImpl(ServiceRepository serviceRepository) {
    this.serviceRepository = serviceRepository;
  }

  @Override
  public List<Services> saveServices(List<Services> services) {
    return serviceRepository.saveAll(services);
  }

  @Override
  public Services getServiceById(Long id) {
    return serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant find service with id=" + id));
  }

  @Override
  public void deleteServiceById(Long id) {
    Services services = serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant find service with id=" + id));
    serviceRepository.delete(services);
  }

  @Override
  public void deleteServices(List<Services> services) {
    Set<Long> servicesId = serviceRepository.getAllServicesId();
    services.forEach(service -> {
      if (!servicesId.contains(service.getId())) {
        throw new EntityNotFoundException("Service with id=" + service.getId() + " is not exist");
      }
    });
    serviceRepository.deleteInBatch(services);
  }

  @Override
  public List<Services> getAllServices() {
    return serviceRepository.findAll();
  }

  @Override
  public int getTotalQuantityOfServices() {
    return serviceRepository.getTotalQuantityOfServices();
  }

  @Override
  public Services saveService(Services service) {
    return serviceRepository.save(service);
  }

}
