package com.rcintra.rinhabackendasync.pessoa;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
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

}
