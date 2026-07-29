package com.vitorcardoso.familia.api.repository;

import com.vitorcardoso.familia.api.model.Casa;
import com.vitorcardoso.familia.api.model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class CasaRepository {

    private final List<Casa> casas = new ArrayList<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    public Casa salvar(Casa casa) {
        if (casa.getId() == null) {
            casa.setId(sequencia.incrementAndGet());
        }
        casas.add(casa);
        return casa;
    }

    public List<Casa> listar() {
        return new ArrayList<>(casas);
    }

    public List<Casa> listarPorDono(Pessoa dono) {
        return casas.stream()
                .filter(c -> c.getDono() == dono)
                .toList();
    }

    public Optional<Casa> buscarPorId(Long id) {
        return casas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
}
