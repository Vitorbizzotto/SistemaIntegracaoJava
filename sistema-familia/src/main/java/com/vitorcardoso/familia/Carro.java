package com.vitorcardoso.familia;

public class Carro {

    String nome;
    int idade;
    String marca;
    double valor;
    Pessoa dono;

    public Carro(String nome, int idade, String marca, double valor, Pessoa dono) {
        this.nome = nome;
        this.idade = idade;
        this.marca = marca;
        this.valor = valor;
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
        System.out.println("Marca: " + marca);
        System.out.printf("Valor: R$ %.2f%n", valor);

        if (dono != null) {
            System.out.println("Proprietário: " + dono.nome);
        } else {
            System.out.println("Proprietário: (sem proprietário)");
        }

        if (idade >= 5) {
            System.out.println("Status: Usado");
        } else {
            System.out.println("Status: Seminovo");
        }

        System.out.println("-------------------------");
    }
}
