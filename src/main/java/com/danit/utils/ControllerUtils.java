package com.danit.utils;

import com.danit.dto.BaseDto;
import com.danit.dto.PageDataDto;
import com.danit.exceptions.ObjectToJsonProcessingException;
import com.danit.models.BaseEntity;
import com.danit.models.WebSocketEventMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class ControllerUtils {

  private static ObjectMapper objectMapper = new ObjectMapper();

  public static final ControllerUtils CONTROLLER_UTILS = new ControllerUtils();

  public static final int DEFAULT_PAGE_SIZE = 6;

  public static final int DEFAULT_PAGE_NUMBER = 0;

  private ControllerUtils() {
  }

  public static <T> Map<String, Object> convertPageToMap(Page<T> pageData) {
    Map<String, Object> outputData = new HashMap<>();
    outputData.put("data", pageData.getContent());
    outputData.put("meta", new PageDataDto(pageData.getTotalElements(),
        pageData.getNumber() + 1, pageData.getTotalPages(),
        pageData.getSize(), pageData.getNumberOfElements()));
    return outputData;
  }

  public static <T> Map<String, Object> convertDtoToMap(T data) {
    Map<String, Object> outputData = new HashMap<>();
    outputData.put("data", data);
    outputData.put("meta", new PageDataDto(1, 1,
        1, 1, 1));
    return outputData;
  }

  public static <T extends BaseEntity> String convertEntityToJson(T entity) {
    return "{id:" + entity.getId() + "}";
  }

  public static String convertIdToJson(Long id) {
    return "{\"id\":" + id + "}";
  }

  public static <T extends BaseEntity> String convertDtoToJson(T entity) {
    return "{id:" + entity.getId() + "}";
  }

  public static <T extends BaseEntity> String convertEntitiesToJson(List<T> entities) {
    ObjectWriter ow = objectMapper.writer();
    List<WebSocketEventMsg> data = new ArrayList<>();
    entities.forEach(t -> data.add(new WebSocketEventMsg(t.getId())));
    try {
      return ow.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      throw new ObjectToJsonProcessingException("cant convert entities to json");
    }
  }

  public static <T extends BaseDto> String convertDtosToJson(List<T> entities) {
    ObjectWriter ow = objectMapper.writer();
    List<WebSocketEventMsg> data = new ArrayList<>();
    entities.forEach(t -> data.add(new WebSocketEventMsg(t.getId())));
    try {
      return ow.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      throw new ObjectToJsonProcessingException("cant convert entities to json");
    }
  }
}
