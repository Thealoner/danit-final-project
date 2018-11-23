package ua.com.danit.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.danit.exceptions.EntityNotFoundException;
import ua.com.danit.exceptions.EntityParticularDataException;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {EntityNotFoundException.class,
      EntityParticularDataException.class})
  public final ResponseEntity<ErrorDetails> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
    log.error(ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = Exception.class)
  public final ResponseEntity<ErrorDetails> handleInternalError(Exception ex, WebRequest request) {
    log.error(ex.getMessage());
    ErrorDetails errorDetails = new ErrorDetails(new Date(), "internal server error",
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

