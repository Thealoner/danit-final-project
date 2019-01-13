package com.danit.services;


import com.danit.Application;
import com.danit.dto.service.PaketListRequestDto;
import com.danit.models.Paket;
import com.danit.repositories.PaketRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@ContextConfiguration
public class PaketServiceTest {

  @Autowired
  PaketService paketService;

  @Autowired
  PaketRepository paketRepository;

  @Configuration
  static class Config {
    @Bean
    @Primary
    public PaketService paketService() {
      return mock(PaketService.class);
    }

    @Bean
    @Primary
    public PaketRepository paketRepository() {
      return mock(PaketRepository.class);
    }
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


//  @InjectMocks
//  private PaketService paketService;
//  @Mock
//  private PaketRepository paketRepository;
//
//  @Mock
//  private PaketListRequestDto paketListRequestDto;
//
//  @Before
//  public void setupMock() {
//    MockitoAnnotations.initMocks(this);
//  }

//  @Test
//  public void shouldReturnProduct_whenGetProductByIdIsCalled() throws Exception {
//    Paket paket = new Paket();
//    paket.setTitle("Test Paket");
//    paket.setFreezeMinTerm(7);
//    // Arrange
//    when(paketRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(paket));
//    // Act
//    Paket retrievedPaket = paketService.getEntityById(1L);
//    // Assert
//    assertThat(retrievedPaket.getTitle(), is(equalTo(paket.getTitle())));
//  }


//  @Test
//  public void shouldReturnProduct_whenSaveProductIsCalled() throws Exception {
//    Paket paket = new Paket();
//    paket.setTitle("Mock paket");
//    paket.setFreezeMinTerm(7);
//
//    // Arrange
//    when(paketRepository.save(paket)).thenReturn(paket);
//    // Act
//    paketService.saveEntity(paket);
//    // Assert
////    assertThat(savedPaket, is(equalTo(paket)));
//    verify(paketRepository, times(1)).save(paket);
//  }

//  @Test
//  public void saveEntity() {
//
//    Paket paket = new Paket();
//    paket.setTitle("Mock paket");
//    paket.setActive(true);
//    paket.setFreezeMinTerm(7);
//
//    Mockito.when(paketRepository.save(paket)).thenReturn(paket);
//
//    Paket paketServ = paketService.saveEntity(paket);
//
//    verify(paketRepository, times(1)).save(paket);
//  }

}
