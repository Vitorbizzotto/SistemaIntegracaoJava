package com.vitorcardoso.familia.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CarroRequest {

    @NotBlank(message = "O nome nao pode ser vazio.")
    private String nome;

    @Min(value = 0, message = "A idade nao pode ser negativa.")
    private int idade;

    @NotBlank(message = "A marca nao pode ser vazia.")
    private String marca;

    @Positive(message = "O valor do carro deve ser maior que zero.")
    private double valor;

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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Long getDonoId() {
        return donoId;
    }

    public void setDonoId(Long donoId) {
        this.donoId = donoId;
    }
}
