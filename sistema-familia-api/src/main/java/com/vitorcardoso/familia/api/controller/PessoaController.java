package com.vitorcardoso.familia.api.controller;

import com.vitorcardoso.familia.api.dto.PagamentoDividaRequest;
import com.vitorcardoso.familia.api.dto.PagamentoDividaResponse;
import com.vitorcardoso.familia.api.dto.PatrimonioResponse;
import com.vitorcardoso.familia.api.dto.PessoaRequest;
import com.vitorcardoso.familia.api.dto.PessoaResponse;
import com.vitorcardoso.familia.api.model.Pessoa;
import com.vitorcardoso.familia.api.service.GestorPatrimonioService;
import com.vitorcardoso.familia.api.service.PessoaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;
    private final GestorPatrimonioService gestorPatrimonioService;

    public PessoaController(PessoaService pessoaService,
                            GestorPatrimonioService gestorPatrimonioService) {
        this.pessoaService = pessoaService;
        this.gestorPatrimonioService = gestorPatrimonioService;
    }

    // Opcao 1 do menu: Cadastrar pessoa
    @PostMapping
    public ResponseEntity<PessoaResponse> cadastrar(@Valid @RequestBody PessoaRequest request) {
        Pessoa pessoa = pessoaService.cadastrar(request);
        return ResponseEntity
                .created(URI.create("/pessoas/" + pessoa.getId()))
                .body(PessoaResponse.from(pessoa));
    }

    // Opcao 5 do menu: Listar familia
    @GetMapping
    public List<PessoaResponse> listar() {
        return pessoaService.listar().stream()
                .map(PessoaResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public PessoaResponse buscar(@PathVariable Long id) {
        return PessoaResponse.from(pessoaService.buscar(id));
    }

    // Opcao 9 do menu: Ver patrimonio de uma pessoa
    @GetMapping("/{id}/patrimonio")
    public PatrimonioResponse patrimonio(@PathVariable Long id) {
        return pessoaService.calcularPatrimonio(id);
    }

    // Opcao 14 do menu: Pagar divida
    @PostMapping("/{id}/pagamento-divida")
    public PagamentoDividaResponse pagarDivida(@PathVariable Long id,
                                               @Valid @RequestBody PagamentoDividaRequest request) {
        return gestorPatrimonioService.pagarDivida(id, request.getValor());
    }
}
