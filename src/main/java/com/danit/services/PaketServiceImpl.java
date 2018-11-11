package com.danit.services;

import com.danit.models.Paket;
import com.danit.repositories.PaketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class PaketServiceImpl implements PaketService {

  private PaketRepository paketRepository;

  @Autowired
  public PaketServiceImpl(PaketRepository paketRepository) {
    this.paketRepository = paketRepository;
  }

  @Override
  public List<Paket> getAllPakets() {
    return paketRepository.findAll();
  }

  @Override
  public Paket getPaketById(long id) {
    return paketRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find Paket with id=" + id));
  }

  @Override
  public List<Paket> savePakets(List<Paket> pakets) {
    return paketRepository.saveAll(pakets);
  }

  @Override
  public void deletePaketById(long id) {
    paketRepository.deleteById(id);
  }

  @Override
  public void deletePakets(List<Paket> pakets) {
    Set<Long> paketsId = paketRepository.getAllPaketsId();
    pakets.forEach(paket -> {
      if (!paketsId.contains(paket.getId())) {
        throw new EntityNotFoundException("Paket with id=" + paket.getId() + " is not exist");
      }
    });
    paketRepository.deleteInBatch(pakets);
  }
}
