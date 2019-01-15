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
    String className = getClassSimpleName(joinPoint.getSignature().getDeclaringTypeName());
    logger.info(className + ": " + principal.getName() + " " + methodName + additionalInfo);
  }

  @After("@annotation(org.springframework.web.bind.annotation.PostMapping)")
  public void logPostMapping(JoinPoint joinPoint) {
    Principal principal = (Principal) joinPoint.getArgs()[1];
    Object entitiesList = joinPoint.getArgs()[0];
    String methodName = splitCamelCase(joinPoint.getSignature().getName()).toLowerCase();
    String className = getClassSimpleName(joinPoint.getSignature().getDeclaringTypeName());
    logger.info(className + ": " + principal.getName() + " " + methodName + " " + entitiesList);
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
      logger.info(className + ": " + principal.getName() + " " + methodName + firstAdditionalInfo);
    }  else {
      logger.info(className + ": " + principal.getName() + " " + methodName + firstAdditionalInfo);
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
      logger.info(className + ": " + principal.getName() + " " + methodName + firstAdditionalInfo);
    }  else {
      logger.info(className + ": " + principal.getName() + " " + methodName + firstAdditionalInfo);
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
