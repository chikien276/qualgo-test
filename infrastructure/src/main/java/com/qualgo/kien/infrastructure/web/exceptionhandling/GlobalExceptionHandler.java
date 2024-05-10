package com.qualgo.kien.infrastructure.web.exceptionhandling;

import com.qualgo.kien.application.exception.BaseException;
import com.qualgo.kien.application.exception.ForbiddenException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<?> commandError(Exception ex) {
    Throwable rootCause = ExceptionUtils.getRootCause(ex);
    if (rootCause instanceof BaseException) {
      return baseError((BaseException) ex);
    }
    return new ResponseEntity<>(
        ErrorMessage.builder().errorCode(500).message(ex.getMessage()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = BaseException.class)
  public ResponseEntity<?> baseError(BaseException ex) {
    return new ResponseEntity<>(
        ErrorMessage.builder().errorCode(ex.getErrorCode()).message(ex.getMessage()).build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = ForbiddenException.class)
  public ResponseEntity<?> forbidden(ForbiddenException ex) {
    return new ResponseEntity<>(
        ErrorMessage.builder().errorCode(403).message(ex.getMessage()).build(),
        HttpStatus.FORBIDDEN);
  }
}
