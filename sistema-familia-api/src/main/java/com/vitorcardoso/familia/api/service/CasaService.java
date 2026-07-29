package com.vitorcardoso.familia.api.service;

import com.vitorcardoso.familia.api.dto.CasaRequest;
import com.vitorcardoso.familia.api.exception.BusinessException;
import com.vitorcardoso.familia.api.exception.NotFoundException;
import com.vitorcardoso.familia.api.model.Casa;
import com.vitorcardoso.familia.api.model.Pessoa;
import com.vitorcardoso.familia.api.repository.CasaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CasaService {

    private final CasaRepository casaRepository;
    private final PessoaService pessoaService;

    public CasaService(CasaRepository casaRepository, PessoaService pessoaService) {
        this.casaRepository = casaRepository;
        this.pessoaService = pessoaService;
    }

    public Casa cadastrar(CasaRequest request) {
        Pessoa dono = null;
        if (request.getDonoId() != null) {
            Pessoa candidato = pessoaService.buscar(request.getDonoId());
            if (!candidato.podeSerProprietarioDeBemImovelOuVeiculo()) {
                throw new BusinessException(candidato.getNome()
                        + " e menor de idade e nao pode ser proprietario de casa.");
            }
            dono = candidato;
        }
        Casa casa = new Casa(
                request.getNome().trim(),
                request.getIdade(),
                request.getEndereco().trim(),
                request.getValor(),
                dono);
        return casaRepository.salvar(casa);
    }

    public List<Casa> listar() {
        return casaRepository.listar();
    }

    public Casa buscar(Long id) {
        return casaRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Casa com id " + id + " nao encontrada."));
    }
}
