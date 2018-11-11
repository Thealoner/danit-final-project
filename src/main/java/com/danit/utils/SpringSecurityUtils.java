package com.danit.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtils {
  public static final SpringSecurityUtils SPRING_SECURITY_UTILS = new SpringSecurityUtils();

  private SpringSecurityUtils() {
  }

  public static String getCurrentPrincipalName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

}
