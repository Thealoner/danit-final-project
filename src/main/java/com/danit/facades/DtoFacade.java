package com.danit.facades;

import com.danit.dto.BaseDto;
import com.danit.models.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DtoFacade<D extends BaseDto, E extends BaseEntity, R> {

  D convertToDto(E entity);

  Page<D> getAllEntities(Pageable pageable);

  Page<D> getAllEntities(R listRequestDto, Pageable pageable);

  List<D> saveEntities(List<E> entities);

  D getEntityById(Long id);

  List<D> updateEntities(List<E> entities);

  void deleteEntityById(Long id);

  void deleteEntities(List<E> entities);
}
