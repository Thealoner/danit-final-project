package com.danit.services;

import com.danit.Application;
import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Service;
import com.danit.repositories.ServiceRepository;
import com.danit.repositories.specifications.ServiceListSpecification;
import com.danit.utils.WebSocketUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ServicesServiceTest {

  @InjectMocks
  ServicesService servicesService;

  @Mock
  ServiceRepository serviceRepository;

  @Mock
  WebSocketUtils webSocketUtils;

  @Mock
  ServiceListSpecification serviceListSpecification;

  @Mock
  SimpMessageSendingOperations messagingTemplate;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  Service getMockService(String title) {
    Service service = new Service();
    service.setTitle("ServiceTitle" + title);
    service.setActive(true);
    service.setUnit("min");
    service.setCost(300f);
    service.setPrice(200f);
    return service;
  }

  @Test
  public void getAllServicesPageableWithSpecificationTest() {
    List<Service> services = new ArrayList<Service>();
    services.add(getMockService("1"));
    services.add(getMockService("2"));
    Page<Service> page = new PageImpl<Service>(services);
    Pageable pageable = PageRequest.of(0, 4);
    ServiceListRequestDto serviceListRequestDto = new ServiceListRequestDto();
    serviceListRequestDto.setServiceCategoryId("1");

    when(serviceRepository.findAll(serviceListSpecification.getFilter(serviceListRequestDto), pageable)).thenReturn(page);

    Page<Service> getAllEntities = servicesService.getAllEntities(serviceListRequestDto, pageable);

    assertEquals(2, getAllEntities.getNumberOfElements());
    verify(serviceRepository, times(1))
        .findAll(serviceListSpecification.getFilter(serviceListRequestDto), pageable);
  }
}
