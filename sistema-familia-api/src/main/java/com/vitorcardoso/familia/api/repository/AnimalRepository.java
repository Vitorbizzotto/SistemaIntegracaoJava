package com.vitorcardoso.familia.api.repository;

import com.vitorcardoso.familia.api.model.Animal;
import com.vitorcardoso.familia.api.model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class AnimalRepository {

    private final List<Animal> animais = new ArrayList<>();
    private final AtomicLong sequencia = new AtomicLong(0);

    public Animal salvar(Animal animal) {
        if (animal.getId() == null) {
            animal.setId(sequencia.incrementAndGet());
        }
        animais.add(animal);
        return animal;
    }

    public List<Animal> listar() {
        return new ArrayList<>(animais);
    }

    public List<Animal> listarPorDono(Pessoa dono) {
        return animais.stream()
                .filter(a -> a.getDono() == dono)
                .toList();
    }

    public Optional<Animal> buscarPorId(Long id) {
        return animais.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }
}
