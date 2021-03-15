package com.payments.application.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid() {
        return new ResponseEntity<>("Bad request - incorrect input", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundPaymentException.class})
    public ResponseEntity<Object> handleNotFoundPayment() {
        return new ResponseEntity<>("Bad request - payment not found", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}