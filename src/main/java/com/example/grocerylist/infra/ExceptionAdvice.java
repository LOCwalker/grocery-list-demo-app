package com.example.grocerylist.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * Ensures that we have the exception message in the response body every time a {@link ResponseStatusException} is thrown.
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDTO> handleResponseStatusExc(ResponseStatusException ex) {
        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getStatusCode().value()))
                .body(new ErrorDTO(ex.getReason(), ex.getStatusCode().value()));
    }
}
