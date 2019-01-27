package com.danit.utils;

import com.danit.exceptions.IllegalAccessReflectionException;
import com.danit.models.User;
import com.danit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

@Component
public final class ServiceUtils {

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

  public User getUserFromAuthContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    return userRepository.findByUsername(userName);
  }
}
