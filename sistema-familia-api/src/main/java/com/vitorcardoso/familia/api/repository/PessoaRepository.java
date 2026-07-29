package com.vitorcardoso.familia.api.repository;

import com.vitorcardoso.familia.api.model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRepository {

    private final List<Pessoa> pessoas = new ArrayList<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    public Pessoa salvar(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            pessoa.setId(sequencia.incrementAndGet());
        }
        pessoas.add(pessoa);
        return pessoa;
    }

    public List<Pessoa> listar() {
        return new ArrayList<>(pessoas);
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public int total() {
        return pessoas.size();
    }
}
