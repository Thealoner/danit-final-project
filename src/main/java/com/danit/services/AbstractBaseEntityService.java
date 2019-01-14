package com.danit.services;

import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.IllegalEntityFormatException;
import com.danit.models.BaseEntity;
import com.danit.repositories.BaseEntityRepository;
import com.danit.repositories.specifications.BaseSpecification;
import com.danit.services.events.WebSocketEvent;
import com.danit.utils.ServiceUtils;
import com.danit.utils.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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

  protected static final String LOG_MSG1 = "Cant find ";

  protected static final String LOG_MSG2 = " with id=";

  @Autowired
  protected BaseEntityRepository<E> baseEntityRepository;

  @Autowired
  protected BaseSpecification<E, R> baseSpecification;
  @Autowired
  WebSocketUtils webSocketUtils;
  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

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
    entityList.forEach(e -> {
      if (Objects.nonNull(e.getId())) {
        throw new IllegalEntityFormatException(getEntityName() + LOG_MSG2 + e.getId() +
            " shouldn't contain id to be persisted in DB");
      }
    });
    List<E> savedEntityList = (List<E>) baseEntityRepository.saveAll(entityList);
    notifyChannel(WebSocketEvent.POST, savedEntityList);
    return savedEntityList;
  }

  @Override
  public E saveEntity(E entity) {
    E savedEntity = baseEntityRepository.save(entity);
    notifyChannel(WebSocketEvent.POST, savedEntity);
    return savedEntity;
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
    List<E> savedEntities = (List<E>) baseEntityRepository.saveAll(entitiesToSave);
    notifyChannel(WebSocketEvent.PUT, savedEntities);
    return savedEntities;
  }

  @Override
  public void deleteEntityById(long id) {
    E e = baseEntityRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException(LOG_MSG1 + getEntityName() + LOG_MSG2 + id));
    baseEntityRepository.delete(e);
    notifyChannel(WebSocketEvent.DELETE, e);
  }

  @Override
  public void deleteEntities(List<E> entityList) {
    if(entityList.isEmpty()) {
      throw new EntityNotFoundException("can't find any entities to perform delete");
    }
    List<E> list = reloadEntities(entityList);
    entityList.forEach(e -> {
      if (!list.contains(e)) {
        throw new EntityNotFoundException(LOG_MSG1 + getEntityName() + LOG_MSG2 + e.getId());
      }
    });
    baseEntityRepository.deleteAll(list);
    notifyChannel(WebSocketEvent.DELETE, entityList);
  }

  @Override
  public long getNumberOfEntities() {
    return baseEntityRepository.count();
  }

  public List<E> reloadEntities(List<E> entityList) {
    List<Long> listIds = entityList.stream().map(E::getId).collect(Collectors.toList());
    return baseEntityRepository.findAllEntitiesByIds(listIds);
  }

  public void notifyChannel(WebSocketEvent webSocketEvent, List<E> entityList) {
    switch (webSocketEvent.name()) {
      case "PUT":
        messagingTemplate.convertAndSend(webSocketUtils.getPutEventEndpoint(),
            webSocketUtils.convertEntitiesToJson(entityList));
        break;
      case "POST":
        messagingTemplate.convertAndSend(webSocketUtils.getPostEventEndpoint(),
            webSocketUtils.convertEntitiesToJson(entityList));
        break;
      case "GET":
        messagingTemplate.convertAndSend(webSocketUtils.getGetEventEndpoint(),
            webSocketUtils.convertEntitiesToJson(entityList));
        break;
      case "DELETE":
        messagingTemplate.convertAndSend(webSocketUtils.getDeleteEventEndpoint(),
            webSocketUtils.convertEntitiesToJson(entityList));
        break;
      default:
        throw new UnsupportedOperationException(webSocketEvent.name() + " event is not supported");
    }

  }

  public void notifyChannel(WebSocketEvent webSocketEvent, E entity) {
    switch (webSocketEvent.name()) {
      case "PUT":
        messagingTemplate.convertAndSend(webSocketUtils.getPutEventEndpoint(),
            webSocketUtils.convertEntityToJson(entity));
        break;
      case "POST":
        messagingTemplate.convertAndSend(webSocketUtils.getPostEventEndpoint(),
            webSocketUtils.convertEntityToJson(entity));
        break;
      case "GET":
        messagingTemplate.convertAndSend(webSocketUtils.getGetEventEndpoint(),
            webSocketUtils.convertEntityToJson(entity));
        break;
      case "DELETE":
        messagingTemplate.convertAndSend(webSocketUtils.getDeleteEventEndpoint(),
            webSocketUtils.convertEntityToJson(entity));
        break;
      default:
        throw new UnsupportedOperationException(webSocketEvent.name() + " event is not supported");
    }
  }

  @SuppressWarnings("unchecked")
  protected String getEntityName() {
    return ((Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[0]).getSimpleName().toLowerCase();
  }

}
