package com.danit.listeners;

import com.danit.models.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class EntityControllersListener {

  @After("@annotation(org.springframework.web.bind.annotation.GetMapping)")
  public void logGetMapping(JoinPoint joinPoint) {
    printLogMsg(joinPoint);
  }

  @After("@annotation(org.springframework.web.bind.annotation.PostMapping)")
  public void logPostMapping(JoinPoint joinPoint) {
    printLogMsg(joinPoint);
  }

  @After("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
  public void logDeleteMapping(JoinPoint joinPoint) {
    printLogMsg(joinPoint);
  }

  @After("@annotation(org.springframework.web.bind.annotation.PutMapping)")
  public void logPutMapping(JoinPoint joinPoint) {
    printLogMsg(joinPoint);
  }

  private void printLogMsg(JoinPoint joinPoint) {
    Map<String, Object> logData = getLogMsgComponents(joinPoint);
    log.info(logData.get("className") + ": " + logData.get("principalName") +
        " " + logData.get("methodName") + logData.get("tail"));
  }

  private Map<String, Object> getLogMsgComponents(JoinPoint joinPoint) {
    Map<String, Object> logData = new HashMap<>(5);
    int idCount = 0;
    for (int i = 0; i < joinPoint.getArgs().length; i++) {
      if (joinPoint.getArgs()[i] instanceof Long) {
        logData.put("id" + idCount++, joinPoint.getArgs()[i].toString());
      } else if (joinPoint.getArgs()[i] instanceof Principal) {
        logData.put("principalName", ((Principal) joinPoint.getArgs()[i]).getName());
      } else if (joinPoint.getArgs()[i] instanceof List) {
        logData.put("list", joinPoint.getArgs()[i]);
      }
    }
    logData.put("methodName", splitCamelCase(joinPoint.getSignature().getName()).toLowerCase());
    logData.put("className", getClassSimpleName(joinPoint.getSignature().getDeclaringTypeName()));
    if (logData.containsKey("id0") && logData.containsKey("id1")) {
      logData.put("tail", ", id0 = " + logData.get("id0") + " -> " + logData.get("id1"));
    } else if (logData.containsKey("id0") && logData.containsKey("list")) {
      logData.put("tail", ", id0 = " + logData.get("list") + " -> " + logData.get("id0"));
    } else if (logData.containsKey("list")) {
      List entityList = (List) logData.get("list");
      List<Long> entityIdList = (List<Long>) entityList.stream()
          .filter(entity -> entity instanceof BaseEntity)
          .map(o -> ((BaseEntity) o).getId())
          .collect(Collectors.toList());
      logData.put("tail", ", id array = " + entityIdList);
    } else if (logData.containsKey("id0")) {
      logData.put("tail", ", id0 = " + logData.get("id0"));
    } else {
      logData.put("tail", "");
    }
    return logData;
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
    return className.replace("com.danit.controllers.", "");
  }

}
