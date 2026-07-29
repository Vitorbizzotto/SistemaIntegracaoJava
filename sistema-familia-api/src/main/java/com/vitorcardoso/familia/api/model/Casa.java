package com.vitorcardoso.familia.api.model;

public class Casa {

    private Long id;
    private String nome;
    private int idade;
    private String endereco;
    private double valor;
    private Pessoa dono;

    public Casa() {
    }

    public Casa(String nome, int idade, String endereco, double valor, Pessoa dono) {
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.valor = valor;
        this.dono = dono;
    }

    public String nomeDono() {
        return dono != null ? dono.getNome() : null;
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

    public Pessoa getDono() {
        return dono;
    }

    public void setDono(Pessoa dono) {
        this.dono = dono;
    }
}
