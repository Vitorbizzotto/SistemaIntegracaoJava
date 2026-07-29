package com.vitorcardoso.familia.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CasaRequest {

    @NotBlank(message = "O nome nao pode ser vazio.")
    private String nome;

    @Min(value = 0, message = "A idade nao pode ser negativa.")
    private int idade;

    @NotBlank(message = "O endereco nao pode ser vazio.")
    private String endereco;

    @Positive(message = "O valor da casa deve ser maior que zero.")
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
