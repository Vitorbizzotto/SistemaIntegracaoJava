package com.vitorcardoso.familia.api.service;

import com.vitorcardoso.familia.api.dto.AnimalRequest;
import com.vitorcardoso.familia.api.exception.NotFoundException;
import com.vitorcardoso.familia.api.model.Animal;
import com.vitorcardoso.familia.api.model.Pessoa;
import com.vitorcardoso.familia.api.repository.AnimalRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final PessoaService pessoaService;

    public AnimalService(AnimalRepository animalRepository, PessoaService pessoaService) {
        this.animalRepository = animalRepository;
        this.pessoaService = pessoaService;
    }

    public Animal cadastrar(AnimalRequest request) {
        Pessoa dono = null;
        if (request.getDonoId() != null) {
            dono = pessoaService.buscar(request.getDonoId());
        }
        Animal animal = new Animal(
                request.getNome().trim(),
                request.getIdade(),
                request.getEspecie().trim(),
                dono);
        return animalRepository.salvar(animal);
    }

    public List<Animal> listar() {
        return animalRepository.listar();
    }

    public Animal buscar(Long id) {
        return animalRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Animal com id " + id + " nao encontrado."));
    }
}
