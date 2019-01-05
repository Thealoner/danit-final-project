package com.danit.services;

import com.danit.dto.service.PaketListRequestDto;
import com.danit.models.Contract;
import com.danit.models.Paket;
import com.danit.repositories.PaketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class PaketService extends AbstractBaseEntityService<Paket, PaketListRequestDto> {

  private PaketRepository paketRepository;

  public PaketService(PaketRepository paketRepository) {
    this.paketRepository = paketRepository;
  }

  @Override
  @Transactional
  public void deleteEntityById(long id) {
    Paket paket = getEntityById(id);
    paket.getContracts().forEach(contract -> contract.setPaket(null));
    paketRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteEntities(List<Paket> entityList) {
    List<Paket> pakets = reloadEntities(entityList);
    pakets.forEach(paket -> {
      if(Objects.nonNull(paket.getContracts())) {
        paket.getContracts().forEach(contract -> contract.setPaket(null));
      }
    });
    paketRepository.deleteAll(pakets);
  }
}
