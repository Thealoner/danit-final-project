package com.danit.utils;

import com.danit.dto.PageDataDto;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class ControllerUtils {

  public static final ControllerUtils CONTROLLER_UTILS = new ControllerUtils();

  public static final int DEFAULT_PAGE_SIZE = 6;

  public static final int DEFAULT_PAGE_NUMBER = 1;

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
}
