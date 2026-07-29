package com.vitorcardoso.familia;

public class Casa {

    String nome;
    int idade;
    String endereco;
    double valor;
    Pessoa dono;

    public Casa(String nome, int idade, String endereco, double valor, Pessoa dono) {
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
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
        System.out.println("Endereço: " + endereco);
        System.out.printf("Valor: R$ %.2f%n", valor);

        if (dono != null) {
            System.out.println("Proprietário: " + dono.nome);
        } else {
            System.out.println("Proprietário: (sem proprietário)");
        }

        if (idade >= 20) {
            System.out.println("Status: Casa antiga");
        } else {
            System.out.println("Status: Casa recente");
        }

        System.out.println("-------------------------");
    }
}
