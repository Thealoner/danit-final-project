package com.danit.services;

import com.danit.dto.service.PaketListRequestDto;
import com.danit.models.Paket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaketService {

  Page<Paket> getAllPakets(Pageable pageable);

  Page<Paket> getAllPakets(PaketListRequestDto paketListRequestDto, Pageable pageable);

  Paket getPaketById(long id);

  List<Paket> savePakets(List<Paket> pakets);

  List<Paket> updatePakets(List<Paket> pakets);

  void deletePaketById(long id);

  void deletePakets(List<Paket> pakets);

}
