package com.danit.facades;

import com.danit.dto.BaseDto;
import com.danit.models.BaseEntity;
import com.danit.services.AbstractBaseEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractDtoFacade<D extends BaseDto, E extends BaseEntity, R> implements DtoFacade<D, E, R> {
  @Autowired
  private AbstractBaseEntityService<E, R> entityService;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  @SuppressWarnings("unchecked")
  public D convertToDto(E entity) {
    return modelMapper.map(entity, (Class<D>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0]);
  }

  public List<D> convertToDtos(List<E> entities) {
    List<D> dtoEntities = new ArrayList<>();
    entities.forEach(e -> dtoEntities.add(convertToDto(e)));
    return dtoEntities;
  }

  public Page<D> convertToDtos(Page<E> entities) {
    return entities.map(this::convertToDto);
  }


  //not sure about it(getActualTypeArguments()[0] or getActualTypeArguments()[1])
  @Override
  public E convertDtoToEntity(D dto) {
    return modelMapper.map(dto, (Class<E>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[1]);
  }

  private List<E> convertDtosToEntities(List<D> dtos) {
    List<E> entities = new ArrayList<>();
    dtos.forEach(d -> entities.add(convertDtoToEntity(d)));
    return entities;
  }

  @Override
  public Page<D> getAllEntities(Pageable pageable) {
    return convertToDtos(entityService.getAllEntities(pageable));
  }

  public Page<D> getAllEntities(R listRequestDto, Pageable pageable) {
    return convertToDtos(entityService.getAllEntities(listRequestDto, pageable));
  }

  @Override
  public List<D> saveEntities(List<E> entities) {
    return convertToDtos(entityService.saveEntities(entities));
  }

  @Override
  public D getEntityById(Long id) {
    return convertToDto(entityService.getEntityById(id));
  }

  @Override
  public List<D> updateEntities(List<E> entities) {
    return convertToDtos(entityService.updateEntities(entities));
  }

  @Override
  public void deleteEntityById(Long id) {
    entityService.deleteEntityById(id);
  }

  @Override
  public void deleteEntities(List<E> entities) {
    entityService.deleteEntities(entities);
  }
}
