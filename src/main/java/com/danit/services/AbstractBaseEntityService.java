package com.danit.services;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.models.BaseEntity;
import com.danit.repositories.BaseEntityRepository;
import com.danit.repositories.specifications.BaseSpecification;
import com.danit.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public abstract class AbstractBaseEntityService<E extends BaseEntity, R> implements BaseEntityService<E> {

  private static final String LOG_MSG1 = "Cant find ";
  private static final String LOG_MSG2 = " with id=";

  @Autowired
  private BaseEntityRepository<E> baseEntityRepository;
  @Autowired
  private BaseSpecification<E, R> baseSpecification;

  @Override
  public E getEntityById(long id) {
    return baseEntityRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(LOG_MSG1 + getEntityName() + LOG_MSG2 + id));
  }

  @Override
  public Page<E> getAllEntities(Pageable pageable) {
    return baseEntityRepository.findAll(pageable);
  }

  public Page<E> getAllEntities(R listRequestDto, Pageable pageable) {
    return baseEntityRepository.findAll(baseSpecification.getFilter(listRequestDto), pageable);
  }

  @Override
  public List<E> saveEntities(List<E> entityList) {
    return (List<E>) baseEntityRepository.saveAll(entityList);
  }

  @Override
  public E saveEntity(E entity) {
    return baseEntityRepository.save(entity);
  }

  @Override
  public List<E> updateEntities(List<E> entityList) {
    entityList.removeIf(e -> Objects.isNull(e.getId()));

    List<E> targetEntities = reloadEntities(entityList);

    List<E> entitiesToSave = new ArrayList<>();
    Iterator<E> iterator = entityList.iterator();
    while (iterator.hasNext()) {
      E s = iterator.next();
      Optional<E> result = targetEntities.stream()
          .filter(v -> v.getId().equals(s.getId()))
          .findFirst();
      if (result.isPresent()) {
        E e = result.get();
        if (ServiceUtils.updateNonEqualFields(s, e)) {
          entitiesToSave.add(e);
        } else {
          iterator.remove();
        }
      } else {
        throw new EntityNotFoundException(LOG_MSG1 + getEntityName() + LOG_MSG2 + s.getId());
      }
    }
    return (List<E>) baseEntityRepository.saveAll(entitiesToSave);
  }

  @Override
  public void deleteEntityById(long id) {
    E e = baseEntityRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(LOG_MSG1 + getEntityName() + LOG_MSG2 + id));
    baseEntityRepository.delete(e);
  }

  @Override
  public void deleteEntities(List<E> entityList) {
    List<E> list = reloadEntities(entityList);
    baseEntityRepository.deleteAll(list);
  }

  @Override
  public long getNumberOfEntities() {
    return baseEntityRepository.count();
  }

  @SuppressWarnings("unchecked")
  private String getEntityName() {
    return ((Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[0]).getSimpleName().toLowerCase();
  }

  private List<E> reloadEntities(List<E> entityList) {
    List<Long> listIds = entityList.stream().map(E::getId).collect(Collectors.toList());
    return baseEntityRepository.findAllEntitiesByIds(listIds);
  }
}