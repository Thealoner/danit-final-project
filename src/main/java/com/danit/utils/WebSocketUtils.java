package com.danit.utils;

import com.danit.exceptions.ObjectToJsonProcessingException;
import com.danit.models.BaseEntity;
import com.danit.models.WebSocketEventMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.List;

public class WebSocketUtils {

  public static final String POST_EVENT = "/events/post";
  public static final String PUT_EVENT = "/events/put";
  public static final String DELETE_EVENT = "/events/delete";
  public static final WebSocketUtils WEB_SOCKET_UTILS = new WebSocketUtils();
  private static ObjectMapper objectMapper = new ObjectMapper();

  private WebSocketUtils() {
  }

  public static <T extends BaseEntity> String convertEntityToJson(T entity) {
    List<T> data = new ArrayList<>();
    data.add(entity);
    return convertEntitiesToJson(data);
  }


  public static <T extends BaseEntity> String convertEntitiesToJson(List<T> entities) {
    ObjectWriter ow = objectMapper.writer();
    List<WebSocketEventMsg> data = new ArrayList<>();
    entities.forEach(t -> data.add(new WebSocketEventMsg(t.getId(), getEntityName(t))));
    try {
      return ow.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      throw new ObjectToJsonProcessingException("cant convert entities to json");
    }
  }


  private static <T> String getEntityName(T entity) {
    return entity.getClass().getSimpleName().toLowerCase();
  }

}
