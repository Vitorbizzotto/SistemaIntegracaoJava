package com.vitorcardoso.familia.api.model;

public class Animal {

    private Long id;
    private String nome;
    private int idade;
    private String especie;
    private Pessoa dono;

    public Animal() {
    }

    public Animal(String nome, int idade, String especie, Pessoa dono) {
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Pessoa getDono() {
        return dono;
    }

    public void setDono(Pessoa dono) {
        this.dono = dono;
    }
}
