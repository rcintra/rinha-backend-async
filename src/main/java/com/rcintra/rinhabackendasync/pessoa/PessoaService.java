package com.rcintra.rinhabackendasync.pessoa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {

    private static final Logger log = LoggerFactory.getLogger(PessoaService.class);

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public Pessoa save(Pessoa pessoa) {
        log.info("Adding new person: {}", pessoa);
        Pessoa p2 = repository.save(pessoa);
        log.info("Person added: {}", p2);
        return p2;
    }

    @Cacheable(value = "pessoas", key = "#id")
    public Pessoa findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new PessoaNotFoundException("Pessoa com id ["+id+"] não encontrada"));
    }

    public Long count() {
        return repository.count();
    }

    public List<Pessoa> findAllByTerm(String t) {
        return repository.findBySearchContainingIgnoreCase(t);
    }

    @Cacheable(value = "pessoas", key = "#apelido")
    public Boolean existsByApelido(String apelido) {
        return repository.existsByNicknameIgnoreCase(apelido);
    }
}
