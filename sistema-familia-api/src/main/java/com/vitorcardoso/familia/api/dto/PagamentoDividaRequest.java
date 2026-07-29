package com.vitorcardoso.familia.api.dto;

import jakarta.validation.constraints.Positive;

public class PagamentoDividaRequest {

    @Positive(message = "O valor a pagar deve ser maior que zero.")
    private double valor;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
