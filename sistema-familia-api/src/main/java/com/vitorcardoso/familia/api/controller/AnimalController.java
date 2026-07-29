package com.vitorcardoso.familia.api.controller;

import com.vitorcardoso.familia.api.dto.AnimalRequest;
import com.vitorcardoso.familia.api.dto.AnimalResponse;
import com.vitorcardoso.familia.api.dto.AtribuirDonoRequest;
import com.vitorcardoso.familia.api.dto.TransferenciaRequest;
import com.vitorcardoso.familia.api.model.Animal;
import com.vitorcardoso.familia.api.service.AnimalService;
import com.vitorcardoso.familia.api.service.GestorPatrimonioService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService animalService;
    private final GestorPatrimonioService gestorPatrimonioService;

    public AnimalController(AnimalService animalService,
                            GestorPatrimonioService gestorPatrimonioService) {
        this.animalService = animalService;
        this.gestorPatrimonioService = gestorPatrimonioService;
    }

    // Opcao 2 do menu: Cadastrar animal
    @PostMapping
    public ResponseEntity<AnimalResponse> cadastrar(@Valid @RequestBody AnimalRequest request) {
        Animal animal = animalService.cadastrar(request);
        return ResponseEntity
                .created(URI.create("/animais/" + animal.getId()))
                .body(AnimalResponse.from(animal));
    }

    // Opcao 6 do menu: Listar animais
    @GetMapping
    public List<AnimalResponse> listar() {
        return animalService.listar().stream()
                .map(AnimalResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public AnimalResponse buscar(@PathVariable Long id) {
        return AnimalResponse.from(animalService.buscar(id));
    }

    // Opcao 10 do menu (para animal): Atribuir proprietario sem custo
    @PutMapping("/{id}/dono")
    public AnimalResponse atribuirDono(@PathVariable Long id,
                                       @Valid @RequestBody AtribuirDonoRequest request) {
        return AnimalResponse.from(
                gestorPatrimonioService.atribuirDonoAnimal(id, request.getNovoDonoId()));
    }

    // Opcao 13 do menu: Transferir animal (sem custo)
    @PostMapping("/{id}/transferencia")
    public AnimalResponse transferir(@PathVariable Long id,
                                     @Valid @RequestBody TransferenciaRequest request) {
        return AnimalResponse.from(
                gestorPatrimonioService.transferirAnimal(id, request));
    }
}
