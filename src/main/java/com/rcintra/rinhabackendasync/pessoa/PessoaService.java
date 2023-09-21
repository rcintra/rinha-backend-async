package com.rcintra.rinhabackendasync.pessoa;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        return repository.save(pessoa);
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
