package com.danit.utils;

import com.danit.exceptions.IllegalAccessReflectionException;
import com.danit.models.BaseEntity;
import com.danit.models.User;
import com.danit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

@Component
public class ServiceUtils {

  private UserRepository userRepository;

  @Autowired
  public ServiceUtils(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean updateNonEqualFields(Object sourseObj, Object targetObj) {
    boolean updated = false;
    for (Field field : sourseObj.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(sourseObj);
        if (Objects.nonNull(value) && !value.equals(field.get(targetObj))) {
          updated = true;
          if (value instanceof BaseEntity && ((BaseEntity) value).getId().equals(-1L)) {
            field.set(targetObj, null);
          } else {
            field.set(targetObj, value);
          }
        }
      } catch (IllegalAccessException e) {
        throw new IllegalAccessReflectionException(e.getMessage());
      }
    }
    return updated;
  }

  public void reformatBaseEntityFields(Object obj) {
    for (Field field : obj.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(obj);
        if (Objects.nonNull(value) && value instanceof BaseEntity &&
            ((BaseEntity) value).getId().equals(-1L)) {
          field.set(obj, null);
        }
      } catch (IllegalAccessException e) {
        throw new IllegalAccessReflectionException(e.getMessage());
      }
    }
  }

  public User getUserFromAuthContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    return userRepository.findByUsername(userName);
  }
}
