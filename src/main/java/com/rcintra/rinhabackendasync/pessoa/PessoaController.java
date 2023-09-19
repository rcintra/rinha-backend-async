package com.rcintra.rinhabackendasync.pessoa;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
public class PessoaController {

    private final PessoaAsyncService service;

    public PessoaController(PessoaAsyncService service) {
        this.service = service;
    }

    @GetMapping(value = "/pessoas")
    public CompletableFuture<List<Pessoa>> searchPessoas(@RequestParam String t) {
        return this.service.getPessoaByTerm(t);
    }

    @GetMapping(value = "/contagem-pessoas")
    public CompletableFuture<Long> count() {
        return this.service.count();
    }

    @GetMapping(value = "/pessoas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Pessoa> get(@PathVariable("id") UUID id) {
        return this.service.findById(id);
    }

    @PostMapping(value = "/pessoas")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Object> create(@Valid @RequestBody Pessoa pessoa) {
        return this.service.existsByApelido(pessoa.getNickname())
                .thenApply( exists -> (exists)
                        ? CompletableFuture.<Pessoa>failedFuture(new ApelidoUnicoException("O apelido ["+pessoa.getNickname()+"] precisa ser unico"))
                        : this.service.save(pessoa));
    }

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
    public ResponseEntity<Object> handleNotFoundException(ApelidoUnicoException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", errors);
        return map;
    }
}
