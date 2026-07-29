package com.vitorcardoso.familia.api.dto;

import com.vitorcardoso.familia.api.model.Pessoa;

public class PessoaResponse {

    private Long id;
    private String nome;
    private int idade;
    private String parentesco;
    private double carteira;
    private double divida;
    private boolean maiorDeIdade;

    public static PessoaResponse from(Pessoa pessoa) {
        PessoaResponse resp = new PessoaResponse();
        resp.id = pessoa.getId();
        resp.nome = pessoa.getNome();
        resp.idade = pessoa.getIdade();
        resp.parentesco = pessoa.getParentesco();
        resp.carteira = pessoa.getCarteira();
        resp.divida = pessoa.getDivida();
        resp.maiorDeIdade = pessoa.podeSerProprietarioDeBemImovelOuVeiculo();
        return resp;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getParentesco() {
        return parentesco;
    }

    public double getCarteira() {
        return carteira;
    }

    public double getDivida() {
        return divida;
    }

    public boolean isMaiorDeIdade() {
        return maiorDeIdade;
    }
}
