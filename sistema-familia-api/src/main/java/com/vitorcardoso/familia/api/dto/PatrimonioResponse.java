package com.vitorcardoso.familia.api.dto;

import java.util.List;

public class PatrimonioResponse {

    private Long pessoaId;
    private String nome;
    private List<AnimalResponse> animais;
    private List<CasaResponse> casas;
    private List<CarroResponse> carros;
    private double totalEmBens;
    private double carteira;
    private double divida;
    private double patrimonioLiquido;

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<AnimalResponse> getAnimais() {
        return animais;
    }

    public void setAnimais(List<AnimalResponse> animais) {
        this.animais = animais;
    }

    public List<CasaResponse> getCasas() {
        return casas;
    }

    public void setCasas(List<CasaResponse> casas) {
        this.casas = casas;
    }

    public List<CarroResponse> getCarros() {
        return carros;
    }

    public void setCarros(List<CarroResponse> carros) {
        this.carros = carros;
    }

    public double getTotalEmBens() {
        return totalEmBens;
    }

    public void setTotalEmBens(double totalEmBens) {
        this.totalEmBens = totalEmBens;
    }

    public double getCarteira() {
        return carteira;
    }

    public void setCarteira(double carteira) {
        this.carteira = carteira;
    }

    public double getDivida() {
        return divida;
    }

    public void setDivida(double divida) {
        this.divida = divida;
    }

    public double getPatrimonioLiquido() {
        return patrimonioLiquido;
    }

    public void setPatrimonioLiquido(double patrimonioLiquido) {
        this.patrimonioLiquido = patrimonioLiquido;
    }
}
