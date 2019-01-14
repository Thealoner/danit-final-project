package com.danit.annotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Aspect
@Component
public class LogPrincipalActionAspect {

  private Logger logger = LoggerFactory.getLogger(LogPrincipalActionAspect.class.getSimpleName());

  @After("@annotation(org.springframework.web.bind.annotation.GetMapping)")
  public void logGetMapping(JoinPoint joinPoint) {
    Principal principal = (Principal) joinPoint.getArgs()[1];
    Object someParameter = joinPoint.getArgs()[0];
    String additionalInfo = (someParameter.getClass().getSimpleName().equals(Long.class.getSimpleName())) ?
        " with id = " + someParameter.toString() : "";
    String methodName = splitCamelCase(joinPoint.getSignature().getName()).toLowerCase();
    logger.info(principal.getName() + " " + methodName + additionalInfo);
  }

  static String splitCamelCase(String s) {
    return s.replaceAll(
        String.format("%s|%s|%s",
            "(?<=[A-Z])(?=[A-Z][a-z])",
            "(?<=[^A-Z])(?=[A-Z])",
            "(?<=[A-Za-z])(?=[^A-Za-z])"
        ),
        " "
    );
  }

}
