package com.danit.services;

import com.danit.Application;
import com.danit.repositories.ContractRepository;
import com.danit.repositories.specifications.ContractListSpecification;
import com.danit.utils.WebSocketUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ContractServiceTest {

  @InjectMocks
  ContractService contractService;

  @Mock
  ContractRepository contractRepository;

  @Mock
  WebSocketUtils webSocketUtils;

  @Mock
  ContractListSpecification contractListSpecification;

  @Mock
  SimpMessageSendingOperations messagingTemplate;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findAllContractsForClientIdTest() {
    Pageable pageable = PageRequest.of(0, 4);
    contractService.findAllContractsForClientId(1L, pageable);
    verify(contractRepository, times(1)).findAllContractsForClientId(1L, pageable);
  }

  @Test
  public void findAllContractsForPaketIdTest() {
    Pageable pageable = PageRequest.of(0, 4);
    contractService.findAllContractsForPaketId(1L, pageable);
    verify(contractRepository, times(1)).findAllContractsForPaketId(1L, pageable);
  }

}
