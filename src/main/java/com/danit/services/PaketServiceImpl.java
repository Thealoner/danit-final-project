package com.danit.services;

import com.danit.dto.service.PaketListRequestDto;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.models.Paket;
import com.danit.repositories.PaketRepository;
import com.danit.repositories.specifications.PaketListSpecification;
import com.danit.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class PaketServiceImpl implements PaketService {

  private final PaketRepository paketRepository;

  private final PaketListSpecification paketListSpecification;

  @Autowired
  public PaketServiceImpl(PaketRepository paketRepository, PaketListSpecification paketListSpecification) {
    this.paketRepository = paketRepository;
    this.paketListSpecification = paketListSpecification;
  }

  @Override
  public Page<Paket> getAllPakets(Pageable pageable) {
    return paketRepository.findAll(pageable);
  }

  @Override
  public Page<Paket> getAllPakets(PaketListRequestDto paketListRequestDto, Pageable pageable) {
    return paketRepository.findAll(paketListSpecification.getFilter(paketListRequestDto), pageable);
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
      if (Objects.nonNull(id)) {
        Paket targetPaket = paketRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Cant find Paket with id=" + id));
        if (ServiceUtils.updateNonEqualFields(sourcePaket, targetPaket)) {
          savedPakets.add(paketRepository.save(targetPaket));
        }
      } else {
        savedPakets.add(paketRepository.save(sourcePaket));
      }
    });
    return savedPakets;
  }

  @Override
  public List<Paket> updatePakets(List<Paket> pakets) {
    List<Paket> savedPakets = new ArrayList<>();
    pakets.forEach(sourceClient -> {
      Long id = sourceClient.getId();
      if (Objects.nonNull(id)) {
        Paket targetClient = paketRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("Cant find Client with id=" + id));
        if (ServiceUtils.updateNonEqualFields(sourceClient, targetClient)) {
          savedPakets.add(paketRepository.save(targetClient));
        }
      } else {
        throw new EntityParticularDataException("id field is empty");
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
    paketRepository.deleteAll(pakets);
  }
}
