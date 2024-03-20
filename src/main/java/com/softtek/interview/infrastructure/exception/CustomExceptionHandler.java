package com.softtek.interview.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INVALID_PARAMETER = "Invalid parameter";
    private static final String SOME_ERROR_OCCURRED_WHILE_EXECUTING_THE_SERVICE =
            "Some error occurred while executing the service";
    private static final String THIS_METHOD_IS_NOT_SUPPORTED_BY_THE_SERVICE =
            "This method is not supported by the service";

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                               HttpHeaders headers, HttpStatusCode status,
                                                               WebRequest request) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<ApiError> apiErrorList = fieldErrors
                .stream()
                .map(error -> buildApiError(INVALID_PARAMETER,
                        "Invalid input for field: " + error.getField(),
                        status.toString()))
                .collect(Collectors.toList());

        log.error("[methodArgumentNotValid] - Validation errors: {}", apiErrorList);
        return new ResponseEntity<>(apiErrorList, status);
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ApiError error = buildApiError(THIS_METHOD_IS_NOT_SUPPORTED_BY_THE_SERVICE,
                exception.getMessage(), status.toString());

        log.error("[httpRequestMethodNotSupported] - Error: {}", error);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleExceptions(Exception exception) {
        ApiError error = buildApiError(SOME_ERROR_OCCURRED_WHILE_EXECUTING_THE_SERVICE,
                exception.getMessage(), INTERNAL_SERVER_ERROR.getReasonPhrase());

        log.error("[handleExceptions] - Error: {}", error);
        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }

    private ApiError buildApiError(String description, String message, String type) {
        return ApiError.builder()
                .errorDescription(description)
                .errorMessage(message)
                .errorType(type)
                .build();
    }
}

