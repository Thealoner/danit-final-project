package com.danit.facades;

import com.danit.dto.PaketDto;
import com.danit.models.Paket;
import com.danit.services.PaketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaketFacadeImpl implements PaketFacade {

  private final PaketService paketService;

  private final ModelMapper modelMapper;

  @Autowired
  public PaketFacadeImpl(PaketService paketService, ModelMapper modelMapper) {
    this.paketService = paketService;
    this.modelMapper = modelMapper;
  }

  @Override
  public PaketDto convertToDto(Paket paket) {
    return modelMapper.map(paket, PaketDto.class);
  }

  @Override
  public Page<PaketDto> getAllPakets(Pageable pageable) {
    return convertToDtos(paketService.getAllPakets(pageable));
  }

  @Override
  public PaketDto getPaketById(long id) {
    return convertToDto(paketService.getPaketById(id));
  }

  @Override
  public List<PaketDto> savePakets(List<Paket> pakets) {
    return convertToDtos(paketService.savePakets(pakets));
  }

  @Override
  public List<PaketDto> updatePakets(List<Paket> pakets) {
    return convertToDtos(paketService.updatePakets(pakets));
  }

  @Override
  public void deletePaketById(long id) {
    paketService.deletePaketById(id);
  }

  @Override
  public void deletePakets(List<Paket> pakets) {
    paketService.deletePakets(pakets);
  }

  private List<PaketDto> convertToDtos(List<Paket> pakets) {
    List<PaketDto> dtoPakets = new ArrayList<>();
    pakets.forEach(paket ->
        dtoPakets.add(modelMapper.map(paket, PaketDto.class)));
    return dtoPakets;
  }

  private Page<PaketDto> convertToDtos(Page<Paket> pakets) {
    return pakets.map(this::convertToDto);
  }

}
