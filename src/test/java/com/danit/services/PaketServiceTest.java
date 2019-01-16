package com.danit.services;


import com.danit.Application;
import com.danit.dto.service.PaketListRequestDto;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.Card;
import com.danit.models.Paket;
import com.danit.repositories.CardRepository;
import com.danit.repositories.PaketRepository;
import com.danit.repositories.specifications.CardListSpecification;
import com.danit.repositories.specifications.PaketListSpecification;
import com.danit.utils.WebSocketUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class PaketServiceTest {

  @InjectMocks
  PaketService paketService;

  @Mock
  PaketRepository paketRepository;

  @Mock
  WebSocketUtils webSocketUtils;

  @Mock
  PaketListSpecification paketListSpecification;

  @Mock
  SimpMessageSendingOperations messagingTemplate;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  Paket getMockPaket(String title) {
    Paket paket = new Paket();
    paket.setTitle("Paket" + title);
    paket.setFreezeMinTerm(7);
    paket.setActive(true);
    paket.setFreezeDays(30);
    paket.setLimitAdditionalServices(true);
    paket.setAccessWithoutCardTimesLimit(7);
    return paket;
  }


  @Test
  public void testFindById() {
    Paket paket = new Paket();
    paket.setTitle("Test Paket");
    paket.setFreezeMinTerm(7);

    Paket savedPaket = paketRepository.save(paket);

    Paket foundPaket = paketService.getEntityById(savedPaket.getId());

    assertEquals(savedPaket, foundPaket);
  }

  @Test(expected = EntityNotFoundException.class)
  public void deletePaketsTest() {
    List<Paket> pakets = new ArrayList<Paket>();
    for (int i = 1; i < 4; i++) {
      Paket paket = getMockPaket("1");
      paket.setId(new Long(i));
      pakets.add(paket);
    }
    doNothing().when(paketRepository).deleteAll(pakets);
    paketService.deleteEntities(pakets);
  }

}
