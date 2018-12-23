package com.danit.services;

import com.danit.models.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseEntityService<E extends BaseEntity> {

  Page<E> getAllEntities(Pageable pageable);

  E getEntityById(long id);

  List<E> saveEntities(List<E> entityList);

  E saveEntity(E entity);

  List<E> updateEntities(List<E> entityList);

  E deleteEntityById(long id);

  List<E> deleteEntities(List<E> entityList);

  long getNumberOfEntities();
}
