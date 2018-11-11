package com.danit.services;

import com.danit.models.Services;
import com.danit.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ServicesServiceImpl implements ServicesService{

  @Autowired
  ServiceRepository serviceRepository;

  @Override
  public void saveServices(List<Services> services) {
    serviceRepository.saveAll(services);
  }

  @Override
  public Services getServiceById(Long id) {
    return serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant find service with id=" + id));
  }

  @Override
  public void deleteServiceById(Long id) {
    serviceRepository.deleteById(id);
  }

  @Override
  public void deleteServices(List<Services> services) {
    serviceRepository.deleteInBatch(services);
  }

  @Override
  public List<Services> getAllServices() {
    return serviceRepository.findAll();
  }

  @Override
  public void saveService(Services service) {
    serviceRepository.save(service);
  }

}