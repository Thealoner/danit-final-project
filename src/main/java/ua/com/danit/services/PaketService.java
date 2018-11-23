package ua.com.danit.services;

import ua.com.danit.models.Paket;

import java.util.List;

public interface PaketService {

  List<Paket> getAllPakets();

  Paket getPaketById(long id);

  List<Paket> savePakets(List<Paket> pakets);

  void deletePaketById(long id);

  void deletePakets(List<Paket> pakets);

}
