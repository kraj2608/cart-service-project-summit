package com.foodshop.cartservice.exceptions;

import com.foodshop.cartservice.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> constraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));
        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);
        return new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> cartNotFoundException(CartNotFoundException ex,
                                                                  WebRequest request) {
        return new ResponseEntity<>(ErrorResponseDTO
                .builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value()).build()
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartAuthorizationAccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> cartAuthorizationAccessDeniedException(
            CartAuthorizationAccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseDTO
                .builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value()).build()
                , HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> badRequestException(
            BadRequestException ex, WebRequest request) {
        return new ResponseEntity<>(ErrorResponseDTO
                .builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value()).build()
                , HttpStatus.BAD_REQUEST);
    }



}