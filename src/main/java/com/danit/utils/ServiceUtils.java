package com.danit.utils;

import com.danit.exceptions.IllegalAccessReflectionException;

import java.lang.reflect.Field;
import java.util.Objects;

public class ServiceUtils {

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
}
