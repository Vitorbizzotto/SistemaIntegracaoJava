package com.vitorcardoso.familia.api.model;

public class Pessoa {

    private Long id;
    private String nome;
    private int idade;
    private String parentesco;
    private double carteira;
    private double divida;

    public Pessoa() {
    }

    public Pessoa(String nome, int idade, String parentesco, double carteira) {
        this.nome = nome;
        this.idade = idade;
        this.parentesco = parentesco;
        this.carteira = carteira;
        this.divida = 0;
    }

    public boolean podeSerProprietarioDeBemImovelOuVeiculo() {
        return idade >= 18;
    }

    public boolean temSaldo(double valor) {
        return carteira >= valor;
    }

    public boolean temDivida() {
        return divida > 0;
    }

    public void creditar(double valor) {
        if (valor <= 0) {
            return;
        }
        this.carteira += valor;
    }

    public void debitar(double valor) {
        if (valor <= 0) {
            return;
        }
        this.carteira -= valor;
    }

    public void aumentarDivida(double valor) {
        if (valor <= 0) {
            return;
        }
        this.divida += valor;
    }

    public void pagarDivida(double valor) {
        if (valor <= 0) {
            return;
        }
        double pagamento = Math.min(valor, Math.min(divida, carteira));
        this.carteira -= pagamento;
        this.divida -= pagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public double getDivida() {
        return divida;
    }

    public void setDivida(double divida) {
        this.divida = divida;
    }
}
