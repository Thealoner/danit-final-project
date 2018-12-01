package com.danit.services;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public abstract class AbstractEntityService<E, T> implements EntityService<E, T> {

  @Autowired
  EntityRepository<E> entityRepository;

  @Override
  public E getEntityById(long id) {
      return entityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant find entity with id=" + id));
  }
}
