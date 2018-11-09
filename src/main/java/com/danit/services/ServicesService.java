package com.danit.services;

import com.danit.models.Services;

import java.util.List;

public interface ServicesService {

  void saveServices(List<Services> services);

  Services getServiceById(Long id);

  void deleteServiceById(Long id);

  void deleteServices(List<Services> services);

  List<Services> getAllServices();

}
