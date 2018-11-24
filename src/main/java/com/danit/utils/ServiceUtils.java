package com.danit.utils;

import com.danit.exceptions.IllegalAccessReflectionException;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ServiceUtils {

  public static final ServiceUtils SERVICE_UTILS = new ServiceUtils();

  private ServiceUtils() {

  }

  public static boolean updateNonEqualFields(Object sourseObj, Object targetObj) {
    boolean updated = false;
    for (Field field : sourseObj.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(sourseObj);
        if (Objects.nonNull(value) && (value != field.get(targetObj))) {
          updated = true;
          field.set(targetObj, value);
        }
      } catch (IllegalAccessException e) {
        throw new IllegalAccessReflectionException(e.getMessage());
      }
    }
    return updated;
  }

  public static <T> Map<String, Object> convertToMap(Page<T> pageData) {
    Map<String, Object> outputData = new HashMap<>();
    outputData.put("data", pageData.getContent());
    outputData.put("last_page", pageData.getTotalPages());
    return outputData;
  }
}
