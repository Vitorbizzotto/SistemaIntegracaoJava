package com.vitorcardoso.familia.api.dto;

import com.vitorcardoso.familia.api.model.Animal;

public class AnimalResponse {

    private Long id;
    private String nome;
    private int idade;
    private String especie;
    private Long donoId;
    private String donoNome;
    private String status;

    public static AnimalResponse from(Animal animal) {
        AnimalResponse resp = new AnimalResponse();
        resp.id = animal.getId();
        resp.nome = animal.getNome();
        resp.idade = animal.getIdade();
        resp.especie = animal.getEspecie();
        resp.donoId = animal.getDono() != null ? animal.getDono().getId() : null;
        resp.donoNome = animal.nomeDono();
        resp.status = animal.getIdade() >= 1 ? "Adulto" : "Filhote";
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

    public String getEspecie() {
        return especie;
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
