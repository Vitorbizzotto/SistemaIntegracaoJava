package com.vitorcardoso.familia.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class PessoaRequest {

    @NotBlank(message = "O nome nao pode ser vazio.")
    private String nome;

    @Min(value = 0, message = "A idade nao pode ser negativa.")
    private int idade;

    @NotBlank(message = "O parentesco nao pode ser vazio.")
    private String parentesco;

    @PositiveOrZero(message = "O saldo inicial nao pode ser negativo.")
    private double carteira;

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

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public double getCarteira() {
        return carteira;
    }

    public void setCarteira(double carteira) {
        this.carteira = carteira;
    }
}
