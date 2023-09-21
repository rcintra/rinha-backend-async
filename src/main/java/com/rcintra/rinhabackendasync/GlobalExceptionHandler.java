package com.rcintra.rinhabackendasync;

import com.rcintra.rinhabackendasync.pessoa.ApelidoUnicoException;
import com.rcintra.rinhabackendasync.pessoa.PessoaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ExceptionHandler(ApelidoUnicoException.class)
    public ResponseEntity<ErrorResponse> handleApelidoUnicoException(ApelidoUnicoException e) {
        ErrorResponse errorResponse = ErrorResponse.builder(
                        e,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        e.getMessage()
        ).build();
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(PessoaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePessoaNotFoundException(PessoaNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder(
                e,
                HttpStatus.NOT_FOUND,
                e.getMessage()
        ).build();
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", errors);
        return map;
    }

}
