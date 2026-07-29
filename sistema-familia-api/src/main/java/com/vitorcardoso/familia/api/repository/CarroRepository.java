package com.vitorcardoso.familia.api.repository;

import com.vitorcardoso.familia.api.model.Carro;
import com.vitorcardoso.familia.api.model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class CarroRepository {

    private final List<Carro> carros = new ArrayList<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    public Carro salvar(Carro carro) {
        if (carro.getId() == null) {
            carro.setId(sequencia.incrementAndGet());
        }
        carros.add(carro);
        return carro;
    }

    public List<Carro> listar() {
        return new ArrayList<>(carros);
    }

    public List<Carro> listarPorDono(Pessoa dono) {
        return carros.stream()
                .filter(c -> c.getDono() == dono)
                .toList();
    }

    public Optional<Carro> buscarPorId(Long id) {
        return carros.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
}
