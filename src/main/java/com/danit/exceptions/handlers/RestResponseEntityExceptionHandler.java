package com.danit.exceptions.handlers;

import com.danit.exceptions.EmailNotFoundException;
import com.danit.exceptions.EntityNotFoundException;
import com.danit.exceptions.EntityParticularDataException;
import com.danit.exceptions.IllegalAccessReflectionException;
import com.danit.exceptions.IllegalDateConversionException;
import com.danit.exceptions.IllegalEntityFormatException;
import com.danit.exceptions.ImageFormatException;
import com.danit.exceptions.InvalidJwtTokenException;
import com.danit.exceptions.JwtUserMapException;
import com.danit.exceptions.ObjectToJsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {EntityNotFoundException.class,
      EntityParticularDataException.class, IllegalAccessReflectionException.class,
      IllegalEntityFormatException.class, IllegalDateConversionException.class,
      InvalidJwtTokenException.class, ObjectToJsonProcessingException.class,
      JwtUserMapException.class, EmailNotFoundException.class, ImageFormatException.class})
  public final ResponseEntity<ErrorDetails> handleEntityNotFoundException(RuntimeException ex, WebRequest request) {
    log.error(ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = Exception.class)
  public final ResponseEntity<ErrorDetails> handleInternalError(Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ErrorDetails errorDetails = new ErrorDetails(new Date(), "internal server error",
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

