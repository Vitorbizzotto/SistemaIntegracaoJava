package com.vitorcardoso.familia.api.service;

import com.vitorcardoso.familia.api.dto.AnimalResponse;
import com.vitorcardoso.familia.api.dto.CarroResponse;
import com.vitorcardoso.familia.api.dto.CasaResponse;
import com.vitorcardoso.familia.api.dto.PatrimonioResponse;
import com.vitorcardoso.familia.api.dto.PessoaRequest;
import com.vitorcardoso.familia.api.exception.NotFoundException;
import com.vitorcardoso.familia.api.model.Animal;
import com.vitorcardoso.familia.api.model.Carro;
import com.vitorcardoso.familia.api.model.Casa;
import com.vitorcardoso.familia.api.model.Pessoa;
import com.vitorcardoso.familia.api.repository.AnimalRepository;
import com.vitorcardoso.familia.api.repository.CarroRepository;
import com.vitorcardoso.familia.api.repository.CasaRepository;
import com.vitorcardoso.familia.api.repository.PessoaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final AnimalRepository animalRepository;
    private final CasaRepository casaRepository;
    private final CarroRepository carroRepository;

    public PessoaService(PessoaRepository pessoaRepository,
                         AnimalRepository animalRepository,
                         CasaRepository casaRepository,
                         CarroRepository carroRepository) {
        this.pessoaRepository = pessoaRepository;
        this.animalRepository = animalRepository;
        this.casaRepository = casaRepository;
        this.carroRepository = carroRepository;
    }

    public Pessoa cadastrar(PessoaRequest request) {
        Pessoa pessoa = new Pessoa(
                request.getNome().trim(),
                request.getIdade(),
                request.getParentesco().trim(),
                request.getCarteira());
        return pessoaRepository.salvar(pessoa);
    }

    public List<Pessoa> listar() {
        return pessoaRepository.listar();
    }

    public Pessoa buscar(Long id) {
        return pessoaRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Pessoa com id " + id + " nao encontrada."));
    }

    public PatrimonioResponse calcularPatrimonio(Long pessoaId) {
        Pessoa pessoa = buscar(pessoaId);

        List<Animal> animais = animalRepository.listarPorDono(pessoa);
        List<Casa> casas = casaRepository.listarPorDono(pessoa);
        List<Carro> carros = carroRepository.listarPorDono(pessoa);

        double totalBens = casas.stream().mapToDouble(Casa::getValor).sum()
                + carros.stream().mapToDouble(Carro::getValor).sum();

        PatrimonioResponse resp = new PatrimonioResponse();
        resp.setPessoaId(pessoa.getId());
        resp.setNome(pessoa.getNome());
        resp.setAnimais(animais.stream().map(AnimalResponse::from).toList());
        resp.setCasas(casas.stream().map(CasaResponse::from).toList());
        resp.setCarros(carros.stream().map(CarroResponse::from).toList());
        resp.setTotalEmBens(totalBens);
        resp.setCarteira(pessoa.getCarteira());
        resp.setDivida(pessoa.getDivida());
        resp.setPatrimonioLiquido(totalBens + pessoa.getCarteira() - pessoa.getDivida());
        return resp;
    }
}
