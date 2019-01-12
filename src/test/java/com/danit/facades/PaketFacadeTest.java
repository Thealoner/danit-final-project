package com.danit.facades;


import com.danit.Application;
import com.danit.dto.PaketDto;
import com.danit.models.Paket;
import com.danit.repositories.PaketRepository;
import com.danit.services.PaketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class PaketFacadeTest {

  @Mock
  private PaketService paketService;

  @InjectMocks
  private PaketFacade paketFacade;



  @Test
  public void whenEntityProvided_thenConvertEntityToDto() {
    Paket paket = new Paket();
    paket.setId(8009L);
    paket.setTitle("Mock Paket");
    paket.setActive(true);

    when(paketService.getEntityById(8009L)).thenReturn(paket);

    PaketDto paketDto = paketFacade.convertToDto(paket);

    assertEquals(new Long(8009), paketDto.getId());
    assertEquals("Mock Paket", paketDto.getTitle());
    assertEquals(true, paketDto.getActive());

  }

}
