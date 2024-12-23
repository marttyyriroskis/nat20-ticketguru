package com.nat20.ticketguru.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
     * Exception handler for validation errors
     * If validation fails, a MethodArgumentNotValidException is thrown,
     * which then returns the failed field(s) and the validation failure message(s)
     * as a BAD_REQUEST response
     * 
     * Source:
     * https://dev.to/shujaat34/exception-handling-and-validation-in-spring-boot-3of9
     * The source details a Global Exception handler, that we have implemented here
     * to handle all the api endpoints
     * 
     * @author Paul Carlson
     * @version 1.0
     * @param ex the exception containing details of the validation errors
     * @return a map of field names and their corresponding validation error messages
     */
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

}
