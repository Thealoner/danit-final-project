package com.danit.services;

import com.danit.models.Paket;

import java.util.List;

public interface PaketService {

  List<Paket> getAllPakets();

  Paket getPaketById(long id);

  void savePakets(List<Paket> pakets);

  void deletePaketById(long id);

  void deletePakets(List<Paket> pakets);

}
