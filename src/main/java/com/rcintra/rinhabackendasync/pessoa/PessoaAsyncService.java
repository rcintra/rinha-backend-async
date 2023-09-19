package com.rcintra.rinhabackendasync.pessoa;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PessoaAsyncService {

    private final PessoaService pessoaService;

    public PessoaAsyncService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    public CompletableFuture<Pessoa> findById(UUID id) {
        return CompletableFuture.supplyAsync(() -> pessoaService.findById(id));
    }

    @Async
    public CompletableFuture<List<Pessoa>> getPessoaByTerm(String term) {
        return CompletableFuture.supplyAsync(() -> pessoaService.findAllByTerm(term));
    }

    public CompletableFuture<Long> count() {
        return CompletableFuture.supplyAsync(() -> pessoaService.count());
    }

    @Async
    public CompletableFuture<Pessoa> save(Pessoa pessoa) {
        return CompletableFuture.supplyAsync(() -> pessoaService.save(pessoa));
    }

    public CompletableFuture<Boolean> existsByApelido(String apelido) {
        return CompletableFuture.supplyAsync(() -> pessoaService.existsByApelido(apelido));
    }

}
