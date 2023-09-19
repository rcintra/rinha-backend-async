package com.rcintra.rinhabackendasync.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    List<Pessoa> findBySearchContainingIgnoreCase(String term);

    Boolean existsByNicknameIgnoreCase(String apelido);
}
