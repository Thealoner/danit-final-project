package com.danit.utils;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class ControllerUtils {

  public static final ControllerUtils CONTROLLER_UTILS = new ControllerUtils();

  private ControllerUtils() {
  }

  public static <T> Map<String, Object> convertToMap(Page<T> pageData) {
    Map<String, Object> outputData = new HashMap<>();
    outputData.put("data", pageData.getContent());
    outputData.put("last_page", pageData.getTotalPages());
    return outputData;
  }
}
