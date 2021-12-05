package com.example.routecalculator.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.*;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorProjection<String>>
    handleCountryNotFoundException(HttpServletRequest request,
                                   CountryNotFoundException ex) {

        log.error("CountryNotFoundException for {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ErrorProjection<>(
                        "Country not found - bad country cca3",
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<ErrorProjection<String>>
    handleRouteNotFoundException(HttpServletRequest request,
                                 RouteNotFoundException ex) {

        log.error("RouteNotFoundException for {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ErrorProjection<>(
                        "Land route not found!",
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorProjection<String>>
    handleValidationException(HttpServletRequest request,
                              ValidationException ex) {

        log.error("ValidationException for {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ErrorProjection<>(
                        "Validation exception",
                        List.of(ex.getMessage())));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorProjection<String>>
    handleMissingServletRequestParameterException(HttpServletRequest request,
                                                  MissingServletRequestParameterException ex) {

        log.error("MissingServletRequestParameterException for {}\n", request.getRequestURI(), ex);

        return ResponseEntity
                .badRequest()
                .body(new ErrorProjection<>("Missing request parameter",
                        List.of(Objects.requireNonNull(ex.getMessage()))));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorProjection<Map<String, String>>>
    handleMethodArgumentTypeMismatchException(HttpServletRequest request,
                                              MethodArgumentTypeMismatchException ex) {

        log.error("MethodArgumentTypeMismatchException for {}\n", request.getRequestURI(), ex);

        Map<String, String> details = new HashMap<>();
        details.put("paramName", ex.getName());
        details.put("paramValue", ex.getValue() == null ? "" : ex.getValue().toString());
        details.put("errorMessage", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ErrorProjection<>(
                        "Method argument type mismatch",
                        List.of(details)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorProjection<Map<String, String>>>
    handleMethodArgumentNotValidException(HttpServletRequest request,
                                          MethodArgumentNotValidException ex) {

        log.error("MethodArgumentNotValidException for {}\n", request.getRequestURI(), ex);

        List<Map<String, String>> details = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    Map<String, String> detail = new HashMap<>();
                    detail.put("objectName", fieldError.getObjectName());
                    detail.put("field", fieldError.getField());
                    detail.put("rejectedValue", "" + fieldError.getRejectedValue());
                    detail.put("errorMessage", fieldError.getDefaultMessage());
                    details.add(detail);
                });

        return ResponseEntity
                .badRequest()
                .body(new ErrorProjection<>(
                        "Method argument validation failed",
                        details));
    }

}
