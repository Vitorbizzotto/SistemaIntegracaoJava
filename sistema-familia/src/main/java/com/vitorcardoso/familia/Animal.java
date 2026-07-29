package com.vitorcardoso.familia;

public class Animal {

    String nome;
    int idade;
    String especie;
    Pessoa dono;

    public Animal(String nome, int idade, String especie, Pessoa dono) {
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.dono = dono;
    }

    public void setDono(Pessoa dono) {
        this.dono = dono;
    }

    public String nomeDono() {
        return dono != null ? dono.nome : null;
    }

    public void exibirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Espécie: " + especie);

        if (dono != null) {
            System.out.println("Proprietário: " + dono.nome);
        } else {
            System.out.println("Proprietário: (sem proprietário)");
        }

        if (idade >= 1) {
            System.out.println("Status: Adulto");
        } else {
            System.out.println("Status: Filhote");
        }

        System.out.println("-------------------------");
    }
}
