package com.danit.utils;

import com.danit.dto.PageDataDto;
import com.danit.models.BaseEntity;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerUtils {

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

  public static <T extends BaseEntity> String convertToStringIds(T entity) {
    return entity.getId().toString();
  }

  public static <T extends BaseEntity> String convertToStringIds(List<T> entities) {
    StringBuilder builder = new StringBuilder();
    entities.forEach(t -> builder.append(t.getId()).append(";"));
    return builder.deleteCharAt(builder.length() - 1).toString();
  }
}
