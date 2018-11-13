package com.danit.services;

import com.danit.models.Paket;
import com.danit.repositories.PaketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import static com.danit.utils.ServiceUtils.updateNonEqualFields;

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
    List<Paket> savedPakets = new ArrayList<>();
    pakets.forEach(sourcePaket -> {
      Long id = sourcePaket.getId();
      if(Objects.nonNull(id)) {
        Paket targetPaket = paketRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Cant find Paket with id=" + id));
        if(updateNonEqualFields(sourcePaket, targetPaket)) {
          savedPakets.add(paketRepository.save(targetPaket));
        }
      } else {
        savedPakets.add(paketRepository.save(sourcePaket));
      }
    });
    return savedPakets;
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
