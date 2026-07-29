package com.vitorcardoso.familia.api.dto;

import com.vitorcardoso.familia.api.model.Casa;

public class CasaResponse {

    private Long id;
    private String nome;
    private int idade;
    private String endereco;
    private double valor;
    private Long donoId;
    private String donoNome;
    private String status;

    public static CasaResponse from(Casa casa) {
        CasaResponse resp = new CasaResponse();
        resp.id = casa.getId();
        resp.nome = casa.getNome();
        resp.idade = casa.getIdade();
        resp.endereco = casa.getEndereco();
        resp.valor = casa.getValor();
        resp.donoId = casa.getDono() != null ? casa.getDono().getId() : null;
        resp.donoNome = casa.nomeDono();
        resp.status = casa.getIdade() >= 20 ? "Casa antiga" : "Casa recente";
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

    public String getEndereco() {
        return endereco;
    }

    public double getValor() {
        return valor;
    }

    public Long getDonoId() {
        return donoId;
    }

    public String getDonoNome() {
        return donoNome;
    }

    public String getStatus() {
        return status;
    }
}
