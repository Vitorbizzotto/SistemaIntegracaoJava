package com.vitorcardoso.familia.api.controller;

import com.vitorcardoso.familia.api.dto.AtribuirDonoRequest;
import com.vitorcardoso.familia.api.dto.CarroRequest;
import com.vitorcardoso.familia.api.dto.CarroResponse;
import com.vitorcardoso.familia.api.dto.TransferenciaRequest;
import com.vitorcardoso.familia.api.dto.TransferenciaResponse;
import com.vitorcardoso.familia.api.model.Carro;
import com.vitorcardoso.familia.api.service.CarroService;
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
@RequestMapping("/carros")
public class CarroController {

    private final CarroService carroService;
    private final GestorPatrimonioService gestorPatrimonioService;

    public CarroController(CarroService carroService,
                           GestorPatrimonioService gestorPatrimonioService) {
        this.carroService = carroService;
        this.gestorPatrimonioService = gestorPatrimonioService;
    }

    // Opcao 4 do menu: Cadastrar carro
    @PostMapping
    public ResponseEntity<CarroResponse> cadastrar(@Valid @RequestBody CarroRequest request) {
        Carro carro = carroService.cadastrar(request);
        return ResponseEntity
                .created(URI.create("/carros/" + carro.getId()))
                .body(CarroResponse.from(carro));
    }

    // Opcao 8 do menu: Listar carros
    @GetMapping
    public List<CarroResponse> listar() {
        return carroService.listar().stream()
                .map(CarroResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public CarroResponse buscar(@PathVariable Long id) {
        return CarroResponse.from(carroService.buscar(id));
    }

    // Opcao 10 do menu (para carro): Atribuir proprietario sem custo
    @PutMapping("/{id}/dono")
    public CarroResponse atribuirDono(@PathVariable Long id,
                                      @Valid @RequestBody AtribuirDonoRequest request) {
        return CarroResponse.from(
                gestorPatrimonioService.atribuirDonoCarro(id, request.getNovoDonoId()));
    }

    // Opcao 12 do menu: Transferir carro (venda com possivel emprestimo)
    @PostMapping("/{id}/transferencia")
    public TransferenciaResponse transferir(@PathVariable Long id,
                                            @Valid @RequestBody TransferenciaRequest request) {
        return gestorPatrimonioService.transferirCarro(id, request);
    }
}
