package com.vitorcardoso.familia.api.dto;

import com.vitorcardoso.familia.api.model.Carro;

public class CarroResponse {

    private Long id;
    private String nome;
    private int idade;
    private String marca;
    private double valor;
    private Long donoId;
    private String donoNome;
    private String status;

    public static CarroResponse from(Carro carro) {
        CarroResponse resp = new CarroResponse();
        resp.id = carro.getId();
        resp.nome = carro.getNome();
        resp.idade = carro.getIdade();
        resp.marca = carro.getMarca();
        resp.valor = carro.getValor();
        resp.donoId = carro.getDono() != null ? carro.getDono().getId() : null;
        resp.donoNome = carro.nomeDono();
        resp.status = carro.getIdade() >= 5 ? "Usado" : "Seminovo";
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

    public String getMarca() {
        return marca;
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
