package com.danit.facades;

import com.danit.dto.PaketDto;
import com.danit.dto.service.PaketListRequestDto;
import com.danit.models.Paket;
import org.springframework.stereotype.Component;

@Component
public class PaketFacade extends AbstractDtoFacade<PaketDto, Paket, PaketListRequestDto> {
}
