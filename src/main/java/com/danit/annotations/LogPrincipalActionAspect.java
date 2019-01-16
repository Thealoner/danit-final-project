package com.danit.annotations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Slf4j
@Aspect
@Component
public class LogPrincipalActionAspect {

  @After("@annotation(org.springframework.web.bind.annotation.GetMapping)")
  public void logGetMapping(JoinPoint joinPoint) {
    Principal principal = (Principal) joinPoint.getArgs()[1];
    Object someParameter = joinPoint.getArgs()[0];
    String additionalInfo = (someParameter.getClass().getSimpleName().equals(Long.class.getSimpleName())) ?
        " with id = " + someParameter.toString() : "";
    String methodName = splitCamelCase(joinPoint.getSignature().getName()).toLowerCase();
    String className = getClassSimpleName(joinPoint.getSignature().getDeclaringTypeName());
    log.info(className + ": " + principal.getName() + " " + methodName + additionalInfo);
  }

  @After("@annotation(org.springframework.web.bind.annotation.PostMapping)")
  public void logPostMapping(JoinPoint joinPoint) {
    Principal principal = (Principal) joinPoint.getArgs()[1];
    Object entitiesList = joinPoint.getArgs()[0];
    String methodName = splitCamelCase(joinPoint.getSignature().getName()).toLowerCase();
    String className = getClassSimpleName(joinPoint.getSignature().getDeclaringTypeName());
    log.info(className + ": " + principal.getName() + " " + methodName + " " + entitiesList);
  }

  @After("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
  public void logDeleteMapping(JoinPoint joinPoint) {
    int argsQty = joinPoint.getArgs().length;
    Principal principal = (Principal) joinPoint.getArgs()[1];
    Object firstParameter = joinPoint.getArgs()[0];
    String firstAdditionalInfo = (firstParameter.getClass().getSimpleName().equals(Long.class.getSimpleName())) ?
        " with id = " + firstParameter.toString() + " " : firstParameter.toString() + " ";
    String methodName = splitCamelCase(joinPoint.getSignature().getName()).toLowerCase();
    String className = getClassSimpleName(joinPoint.getSignature().getDeclaringTypeName());
    if (argsQty > 2) {
      Object thirdParameter = joinPoint.getArgs()[2];
      String thirdAdditionalInfo = (thirdParameter.getClass().getSimpleName().equals(Long.class.getSimpleName())) ?
          " with id = " + thirdParameter.toString() : thirdParameter.toString();
      methodName = methodName.replace("from",thirdAdditionalInfo + " from");
      log.info(className + ": " + principal.getName() + " " + methodName + firstAdditionalInfo);
    }  else {
      log.info(className + ": " + principal.getName() + " " + methodName + firstAdditionalInfo);
    }
  }

  @After("@annotation(org.springframework.web.bind.annotation.PutMapping)")
  public void logPutMapping(JoinPoint joinPoint) {
    int argsQty = joinPoint.getArgs().length;
    Principal principal = (Principal) joinPoint.getArgs()[1];
    Object firstParameter = joinPoint.getArgs()[0];
    String firstAdditionalInfo = (firstParameter.getClass().getSimpleName().equals(Long.class.getSimpleName())) ?
        " with id = " + firstParameter.toString() + " " : firstParameter.toString() + " ";
    String methodName = splitCamelCase(joinPoint.getSignature().getName()).toLowerCase();
    String className = getClassSimpleName(joinPoint.getSignature().getDeclaringTypeName());
    if (argsQty > 2) {
      Object thirdParameter = joinPoint.getArgs()[2];
      String thirdAdditionalInfo = (thirdParameter.getClass().getSimpleName().equals(Long.class.getSimpleName())) ?
          " with id = " + thirdParameter.toString() : thirdParameter.toString();
      methodName = methodName.replace("to",thirdAdditionalInfo + " to");
      log.info(className + ": " + principal.getName() + " " + methodName + firstAdditionalInfo);
    }  else {
      log.info(className + ": " + principal.getName() + " " + methodName + firstAdditionalInfo);
    }
  }


  private String splitCamelCase(String camelCase) {
    return camelCase.replaceAll(
        String.format("%s|%s|%s",
            "(?<=[A-Z])(?=[A-Z][a-z])",
            "(?<=[^A-Z])(?=[A-Z])",
            "(?<=[A-Za-z])(?=[^A-Za-z])"
        ),
        " "
    );
  }

  private String getClassSimpleName(String className) {
    return className.replace("com.danit.controllers.","");
  }

}
