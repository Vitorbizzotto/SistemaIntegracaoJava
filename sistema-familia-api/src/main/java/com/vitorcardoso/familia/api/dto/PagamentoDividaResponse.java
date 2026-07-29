package com.vitorcardoso.familia.api.dto;

public class PagamentoDividaResponse {

    private Long pessoaId;
    private String nome;
    private double valorPago;
    private double carteiraApos;
    private double dividaApos;

    public PagamentoDividaResponse() {
    }

    public PagamentoDividaResponse(Long pessoaId, String nome, double valorPago,
                                   double carteiraApos, double dividaApos) {
        this.pessoaId = pessoaId;
        this.nome = nome;
        this.valorPago = valorPago;
        this.carteiraApos = carteiraApos;
        this.dividaApos = dividaApos;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public String getNome() {
        return nome;
    }

    public double getValorPago() {
        return valorPago;
    }

    public double getCarteiraApos() {
        return carteiraApos;
    }

    public double getDividaApos() {
        return dividaApos;
    }
}
