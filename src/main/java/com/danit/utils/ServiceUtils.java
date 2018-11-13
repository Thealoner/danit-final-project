package com.danit.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ServiceUtils {

  public static final ServiceUtils SERVICE_UTILS = new ServiceUtils();

  private ServiceUtils() {

  }

  public static boolean updateNonEqualFields(Object sourseObj, Object targetObj) {
    boolean updated = false;
    for(Field field : sourseObj.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(sourseObj);
        if(Objects.nonNull(value) && (value != field.get(targetObj))) {
          updated = true;
          field.set(targetObj, value);
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return updated;
  }

  public String transformObjectToIdJson(List<Object> objects) {
    StringBuffer jsonString = new StringBuffer();
    objects.forEach(new Consumer<Object>() {
      @Override
      public void accept(Object o) {
        jsonString.append(o.getId());
      }
    });
  }
}
