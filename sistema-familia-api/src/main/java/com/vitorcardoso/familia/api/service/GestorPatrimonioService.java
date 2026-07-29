package com.vitorcardoso.familia.api.service;

import com.vitorcardoso.familia.api.dto.PagamentoDividaResponse;
import com.vitorcardoso.familia.api.dto.TransferenciaRequest;
import com.vitorcardoso.familia.api.dto.TransferenciaResponse;
import com.vitorcardoso.familia.api.exception.BusinessException;
import com.vitorcardoso.familia.api.model.Animal;
import com.vitorcardoso.familia.api.model.Carro;
import com.vitorcardoso.familia.api.model.Casa;
import com.vitorcardoso.familia.api.model.Pessoa;
import org.springframework.stereotype.Service;

@Service
public class GestorPatrimonioService {

    private final PessoaService pessoaService;
    private final AnimalService animalService;
    private final CasaService casaService;
    private final CarroService carroService;

    public GestorPatrimonioService(PessoaService pessoaService,
                                   AnimalService animalService,
                                   CasaService casaService,
                                   CarroService carroService) {
        this.pessoaService = pessoaService;
        this.animalService = animalService;
        this.casaService = casaService;
        this.carroService = carroService;
    }

    // --- ATRIBUIR PROPRIETARIO (opcao 10 do menu, sem custo financeiro) ---

    public Animal atribuirDonoAnimal(Long animalId, Long novoDonoId) {
        Animal animal = animalService.buscar(animalId);
        Pessoa novoDono = pessoaService.buscar(novoDonoId);

        if (animal.getDono() == novoDono) {
            throw new BusinessException(novoDono.getNome() + " ja e o proprietario deste animal.");
        }
        animal.setDono(novoDono);
        return animal;
    }

    public Casa atribuirDonoCasa(Long casaId, Long novoDonoId) {
        Casa casa = casaService.buscar(casaId);
        Pessoa novoDono = pessoaService.buscar(novoDonoId);

        if (!novoDono.podeSerProprietarioDeBemImovelOuVeiculo()) {
            throw new BusinessException(novoDono.getNome()
                    + " e menor de idade e nao pode ser proprietario de casa.");
        }
        if (casa.getDono() == novoDono) {
            throw new BusinessException(novoDono.getNome() + " ja e o proprietario desta casa.");
        }
        casa.setDono(novoDono);
        return casa;
    }

    public Carro atribuirDonoCarro(Long carroId, Long novoDonoId) {
        Carro carro = carroService.buscar(carroId);
        Pessoa novoDono = pessoaService.buscar(novoDonoId);

        if (!novoDono.podeSerProprietarioDeBemImovelOuVeiculo()) {
            throw new BusinessException(novoDono.getNome()
                    + " e menor de idade e nao pode ser proprietario de carro.");
        }
        if (carro.getDono() == novoDono) {
            throw new BusinessException(novoDono.getNome() + " ja e o proprietario deste carro.");
        }
        carro.setDono(novoDono);
        return carro;
    }

    // --- TRANSFERENCIAS (opcoes 11, 12, 13 do menu) ---

    public TransferenciaResponse transferirCasa(Long casaId, TransferenciaRequest request) {
        Casa casa = casaService.buscar(casaId);
        Pessoa novoDono = pessoaService.buscar(request.getNovoDonoId());
        Pessoa donoAtual = casa.getDono();

        validarTransferencia(donoAtual, novoDono, "casa");

        TransferenciaResponse resp = executarVenda("casa", casa.getId(), casa.getNome(),
                casa.getValor(), donoAtual, novoDono, request.isAceitaEmprestimo());
        casa.setDono(novoDono);
        return resp;
    }

    public TransferenciaResponse transferirCarro(Long carroId, TransferenciaRequest request) {
        Carro carro = carroService.buscar(carroId);
        Pessoa novoDono = pessoaService.buscar(request.getNovoDonoId());
        Pessoa donoAtual = carro.getDono();

        validarTransferencia(donoAtual, novoDono, "carro");

        TransferenciaResponse resp = executarVenda("carro", carro.getId(), carro.getNome(),
                carro.getValor(), donoAtual, novoDono, request.isAceitaEmprestimo());
        carro.setDono(novoDono);
        return resp;
    }

    public Animal transferirAnimal(Long animalId, TransferenciaRequest request) {
        Animal animal = animalService.buscar(animalId);
        Pessoa novoDono = pessoaService.buscar(request.getNovoDonoId());
        Pessoa donoAtual = animal.getDono();

        validarTransferencia(donoAtual, novoDono, "animal");

        animal.setDono(novoDono);
        return animal;
    }

    // --- VALIDACAO DE TRANSFERENCIA (mesma logica do metodo validarTransferencia original) ---

    private void validarTransferencia(Pessoa donoAtual, Pessoa novoDono, String tipoBem) {
        if (donoAtual == null) {
            throw new BusinessException("Este " + tipoBem
                    + " nao possui proprietario. Atribua um proprietario antes de transferir.");
        }
        if (novoDono == donoAtual) {
            throw new BusinessException("O novo proprietario deve ser diferente do atual ("
                    + donoAtual.getNome() + ").");
        }
        if (("casa".equals(tipoBem) || "carro".equals(tipoBem))
                && !novoDono.podeSerProprietarioDeBemImovelOuVeiculo()) {
            throw new BusinessException(novoDono.getNome()
                    + " e menor de idade e nao pode receber a propriedade de um " + tipoBem + ".");
        }
    }

    // --- VENDA (executarVenda original sem Scanner e sem System.out) ---
    // O usuario do console era perguntado "deseja emprestimo? (S/N)" e depois "confirma a venda? (S/N)".
    // Na API, o envio do POST ja e a confirmacao. O campo aceitaEmprestimo substitui a primeira pergunta.

    private TransferenciaResponse executarVenda(String tipoBem, Long bemId, String nomeBem,
                                                double valorBem, Pessoa vendedor, Pessoa comprador,
                                                boolean aceitaEmprestimo) {

        double saldoComprador = comprador.getCarteira();
        double faltante = Math.max(0, valorBem - saldoComprador);

        double valorPago;
        double valorEmprestimo;

        if (faltante == 0) {
            valorPago = valorBem;
            valorEmprestimo = 0;
        } else {
            if (!aceitaEmprestimo) {
                throw new BusinessException(String.format(
                        "Saldo insuficiente: faltam R$ %.2f. Reenvie com 'aceitaEmprestimo=true'"
                                + " para contratar emprestimo sem juros pelo valor restante.",
                        faltante));
            }
            valorPago = saldoComprador;
            valorEmprestimo = faltante;
        }

        comprador.debitar(valorPago);
        if (valorEmprestimo > 0) {
            comprador.aumentarDivida(valorEmprestimo);
        }
        vendedor.creditar(valorBem);

        TransferenciaResponse resp = new TransferenciaResponse();
        resp.setTipoBem(tipoBem);
        resp.setBemId(bemId);
        resp.setBemNome(nomeBem);
        resp.setValorBem(valorBem);
        resp.setVendedor(vendedor.getNome());
        resp.setComprador(comprador.getNome());
        resp.setValorPago(valorPago);
        resp.setValorEmprestimo(valorEmprestimo);
        resp.setCarteiraVendedorApos(vendedor.getCarteira());
        resp.setCarteiraCompradorApos(comprador.getCarteira());
        resp.setDividaCompradorApos(comprador.getDivida());
        resp.setMensagem(String.format("Venda concluida! %s \"%s\" agora pertence a %s.",
                capitalizar(tipoBem), nomeBem, comprador.getNome()));
        return resp;
    }

    // --- PAGAMENTO DE DIVIDA (opcao 14 do menu) ---

    public PagamentoDividaResponse pagarDivida(Long pessoaId, double valor) {
        Pessoa pessoa = pessoaService.buscar(pessoaId);

        if (!pessoa.temDivida()) {
            throw new BusinessException(pessoa.getNome() + " nao possui divida.");
        }
        if (pessoa.getCarteira() <= 0) {
            throw new BusinessException(String.format(
                    "%s nao possui saldo na carteira para pagar a divida (Divida: R$ %.2f).",
                    pessoa.getNome(), pessoa.getDivida()));
        }

        double maximoPagavel = Math.min(pessoa.getDivida(), pessoa.getCarteira());
        if (valor > maximoPagavel) {
            throw new BusinessException(String.format(
                    "Valor maior que o permitido. Maximo: R$ %.2f.", maximoPagavel));
        }

        pessoa.pagarDivida(valor);
        return new PagamentoDividaResponse(pessoa.getId(), pessoa.getNome(),
                valor, pessoa.getCarteira(), pessoa.getDivida());
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        return Character.toUpperCase(texto.charAt(0)) + texto.substring(1);
    }
}
