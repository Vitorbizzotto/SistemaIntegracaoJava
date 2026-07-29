package com.vitorcardoso.familia.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AnimalRequest {

    @NotBlank(message = "O nome nao pode ser vazio.")
    private String nome;

    @Min(value = 0, message = "A idade nao pode ser negativa.")
    private int idade;

    @NotBlank(message = "A especie nao pode ser vazia.")
    private String especie;

    private Long donoId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Long getDonoId() {
        return donoId;
    }

    public void setDonoId(Long donoId) {
        this.donoId = donoId;
    }
}
