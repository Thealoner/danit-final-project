package com.danit.facades;

import com.danit.Application;
import com.danit.dto.PaketDto;
import com.danit.models.Paket;
import com.danit.services.PaketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class PaketFacadeTest {

  @InjectMocks
  PaketFacade paketFacade;

  @Mock
  PaketService paketService;

  @Mock
  ModelMapper modelMapper;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void convertToDtoTest() {
    Paket paket = mock(Paket.class);
    paketFacade.convertToDto(paket);
    verify(modelMapper, times(1)).map(paket, PaketDto.class);
  }

  @Test
  public void convertToDtosTest() {
    Paket paket1 = mock(Paket.class);
    Paket paket2 = mock(Paket.class);
    List<Paket> pakets = Arrays.asList(new Paket[]{paket1, paket2});
    Pageable pageable = PageRequest.of(0, 4);
    Page<Paket> page = new PageImpl<>(pakets, pageable, 4);
    paketFacade.convertToDtos(page);
    verify(modelMapper, times(1)).map(pakets.get(0), PaketDto.class);
    verify(modelMapper, times(1)).map(pakets.get(1), PaketDto.class);
  }

  @Test
  public void convertDtoToEntityTest() {
    PaketDto paketDto = mock(PaketDto.class);
    paketFacade.convertDtoToEntity(paketDto);
    verify(modelMapper, times(1)).map(paketDto, Paket.class);
  }

  @Test
  public void getAllEntitiesTest() {
    Paket paket1 = mock(Paket.class);
    Paket paket2 = mock(Paket.class);
    List<Paket> pakets = Arrays.asList(new Paket[]{paket1, paket2});
    Pageable pageable = PageRequest.of(0, 4);
    Page<Paket> page = new PageImpl<>(pakets, pageable, 4);

    when(paketService.getAllEntities(pageable)).thenReturn(page);

    Page<PaketDto> allEntities = paketFacade.getAllEntities(pageable);

    assertEquals(2, allEntities.getNumberOfElements());

    verify(paketService, times(1)).getAllEntities(pageable);
    verify(modelMapper, times(1)).map(pakets.get(0), PaketDto.class);
    verify(modelMapper, times(1)).map(pakets.get(1), PaketDto.class);
  }

  @Test
  public void getEntityByIdTest() {
    Paket paket = mock(Paket.class);

    when(paketService.getEntityById(1L)).thenReturn(paket);

    paketFacade.getEntityById(1L);

    verify(paketService, times(1)).getEntityById(1L);
    verify(modelMapper, times(1)).map(paket, PaketDto.class);
  }

  @Test
  public void saveEntitiesTest() {
    Paket paket1 = mock(Paket.class);
    Paket paket2 = mock(Paket.class);
    List<Paket> pakets = Arrays.asList(new Paket[]{paket1, paket2});
    List<PaketDto> paketsDto = paketFacade.convertToDtos(pakets);

    when(paketService.saveEntities(pakets)).thenReturn(pakets);

    paketFacade.saveEntities(paketsDto);

    verify(modelMapper, times(1)).map(pakets.get(0), PaketDto.class);
    verify(modelMapper, times(1)).map(pakets.get(1), PaketDto.class);
  }

  @Test
  public void updateEntitiesTest() {
    Paket paket1 = mock(Paket.class);
    Paket paket2 = mock(Paket.class);
    List<Paket> pakets = Arrays.asList(new Paket[]{paket1, paket2});
    List<PaketDto> paketsDto = paketFacade.convertToDtos(pakets);

    when(paketService.updateEntities(pakets)).thenReturn(pakets);

    paketFacade.updateEntities(paketsDto);

    verify(modelMapper, times(1)).map(pakets.get(0), PaketDto.class);
    verify(modelMapper, times(1)).map(pakets.get(1), PaketDto.class);
  }
}
