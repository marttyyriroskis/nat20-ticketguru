package com.nat20.ticketguru;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.nat20.ticketguru.api.GlobalRestExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalExceptionHandlerTest {
    
    /**
     * Tests the {@code handleValidationExceptions} method of {@link GlobalRestExceptionHandler}.
     * Verifies that the method correctly processes a {@link MethodArgumentNotValidException} and 
     * returns a map of field names to their respective validation error messages.
     */
    @Test
    void handleValidationExceptions_shouldReturnFieldErrorMessages() {
        
        FieldError fieldError1 = new FieldError("objectName", "field1", "must not be null");
        FieldError fieldError2 = new FieldError("objectName", "field2", "size must be between 1 and 10");
        List<FieldError> fieldErrors = Arrays.asList(fieldError1, fieldError2);

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        GlobalRestExceptionHandler handler = new GlobalRestExceptionHandler();
        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("field1", "must not be null");
        expectedErrors.put("field2", "size must be between 1 and 10");

        Map<String, String> actualErrors = handler.handleValidationExceptions(exception);

        assertEquals(expectedErrors, actualErrors);
    }

}
