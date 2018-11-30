package com.danit.facades;

import com.danit.dto.PaketDto;
import com.danit.models.Paket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaketFacade {

  PaketDto convertToDto(Paket paket);

  Page<PaketDto> getAllPakets(Pageable pageable);

  PaketDto getPaketById(long id);

  List<PaketDto> savePakets(List<Paket> pakets);

  List<PaketDto> updatePakets(List<Paket> pakets);

  void deletePaketById(long id);

  void deletePakets(List<Paket> pakets);

}
