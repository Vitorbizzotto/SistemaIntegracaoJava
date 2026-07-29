package com.vitorcardoso.familia.api.controller;

import com.vitorcardoso.familia.api.dto.AtribuirDonoRequest;
import com.vitorcardoso.familia.api.dto.CasaRequest;
import com.vitorcardoso.familia.api.dto.CasaResponse;
import com.vitorcardoso.familia.api.dto.TransferenciaRequest;
import com.vitorcardoso.familia.api.dto.TransferenciaResponse;
import com.vitorcardoso.familia.api.model.Casa;
import com.vitorcardoso.familia.api.service.CasaService;
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
@RequestMapping("/casas")
public class CasaController {

    private final CasaService casaService;
    private final GestorPatrimonioService gestorPatrimonioService;

    public CasaController(CasaService casaService,
                          GestorPatrimonioService gestorPatrimonioService) {
        this.casaService = casaService;
        this.gestorPatrimonioService = gestorPatrimonioService;
    }

    // Opcao 3 do menu: Cadastrar casa
    @PostMapping
    public ResponseEntity<CasaResponse> cadastrar(@Valid @RequestBody CasaRequest request) {
        Casa casa = casaService.cadastrar(request);
        return ResponseEntity
                .created(URI.create("/casas/" + casa.getId()))
                .body(CasaResponse.from(casa));
    }

    // Opcao 7 do menu: Listar casas
    @GetMapping
    public List<CasaResponse> listar() {
        return casaService.listar().stream()
                .map(CasaResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public CasaResponse buscar(@PathVariable Long id) {
        return CasaResponse.from(casaService.buscar(id));
    }

    // Opcao 10 do menu (para casa): Atribuir proprietario sem custo
    @PutMapping("/{id}/dono")
    public CasaResponse atribuirDono(@PathVariable Long id,
                                     @Valid @RequestBody AtribuirDonoRequest request) {
        return CasaResponse.from(
                gestorPatrimonioService.atribuirDonoCasa(id, request.getNovoDonoId()));
    }

    // Opcao 11 do menu: Transferir casa (venda com possivel emprestimo)
    @PostMapping("/{id}/transferencia")
    public TransferenciaResponse transferir(@PathVariable Long id,
                                            @Valid @RequestBody TransferenciaRequest request) {
        return gestorPatrimonioService.transferirCasa(id, request);
    }
}
