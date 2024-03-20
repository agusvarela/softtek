package com.softtek.interview.unit.infrastructure.exception;

import com.softtek.interview.infrastructure.exception.CustomExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomExceptionHandlerTest {

    private final CustomExceptionHandler exceptionHandler = new CustomExceptionHandler();

    @Test
    public void handleMethodArgumentNotValid_thenReturnsResponseEntityWithValidationErrors() {
        FieldError fieldError = new FieldError(
                "objectName", "fieldName", "Error");

        BindingResult bindingResult = new org.springframework.validation.BeanPropertyBindingResult(
                Collections.singleton(fieldError), "objectName");

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Object> responseEntity = exceptionHandler.handleMethodArgumentNotValid(
                exception, null, HttpStatus.BAD_REQUEST, null);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void handleHttpRequestMethodNotSupported_thenReturnsResponseEntityWithMethodNotSupportedError() {
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException(null);

        ResponseEntity<Object> responseEntity = exceptionHandler.handleHttpRequestMethodNotSupported(
                exception, null, HttpStatus.METHOD_NOT_ALLOWED, null);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());
    }

    @Test
    public void handleExceptions_thenReturnsResponseEntityWithInternalServerError() {
        Exception exception = new Exception("Some error");

        ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptions(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
