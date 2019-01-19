package com.danit.utils;

import com.danit.exceptions.ObjectToJsonProcessingException;
import com.danit.models.BaseEntity;
import com.danit.models.WebSocketEventMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:application.yml")
public class WebSocketUtils {

  @Getter
  @Value("${websocket.postEventEndPoint}")
  private String postEventEndpoint;

  @Getter
  @Value("${websocket.putEventEndPoint}")
  private String putEventEndpoint;

  @Getter
  @Value("${websocket.deleteEventEndPoint}")
  private String deleteEventEndpoint;

  @Getter
  @Value("${websocket.getEventEndPoint}")
  private String getEventEndpoint;

  @Getter
  @Value("${websocket.stompEndpoint}")
  private String stompEndpoint;

  @Getter
  @Value("${websocket.prefix}")
  private String prefix;

  private ObjectMapper objectMapper;

  @Autowired
  public WebSocketUtils(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  private static <T> String getEntityName(T entity) {
    return entity.getClass().getSimpleName().toLowerCase();
  }

  public <T extends BaseEntity> String convertEntityToJson(T entity) {
    List<T> data = new ArrayList<>();
    data.add(entity);
    return convertEntitiesToJson(data);
  }

  public <T extends BaseEntity> String convertEntitiesToJson(List<T> entities) {
    ObjectWriter ow = objectMapper.writer();
    List<WebSocketEventMsg> data = new ArrayList<>();
    entities.forEach(t -> data.add(new WebSocketEventMsg(t.getId(), getEntityName(t))));
    try {
      return ow.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      throw new ObjectToJsonProcessingException("cant convert entities to json");
    }
  }

}
