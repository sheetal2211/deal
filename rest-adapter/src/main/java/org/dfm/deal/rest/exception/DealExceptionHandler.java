package org.dfm.deal.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.dfm.deal.domain.exception.DealNotFoundException;

@RestControllerAdvice(basePackages = {"org.dfm.deal"})
public class DealExceptionHandler {

  @ExceptionHandler(value = DealNotFoundException.class)
  public final ResponseEntity<DealExceptionResponse> handleDealNotFoundException(
      final Exception exception, final WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        DealExceptionResponse.builder().message(exception.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
  }
}
