package com.vitorcardoso.familia;

import java.util.ArrayList;

public class Pessoa {

    String nome;
    int idade;
    String parentesco;
    double carteira;
    double divida;

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

    public void exibirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Parentesco: " + parentesco);
        System.out.printf("Carteira: R$ %.2f%n", carteira);
        System.out.printf("Dívida: R$ %.2f%n", divida);

        if (idade >= 18) {
            System.out.println("Status: Maior de idade");
        } else {
            System.out.println("Status: Menor de idade");
        }

        System.out.println("-------------------------");
    }

    public void exibirPatrimonio(ArrayList<Animal> animais, ArrayList<Casa> casas, ArrayList<Carro> carros) {
        System.out.println("\n=== PATRIMÔNIO DE " + nome.toUpperCase() + " ===");

        boolean temAlgo = false;
        double totalBens = 0;

        System.out.println("\n--- Animais ---");
        boolean temAnimal = false;
        for (Animal animal : animais) {
            if (animal.dono == this) {
                System.out.println("• " + animal.nome + " (" + animal.especie + ")");
                temAnimal = true;
                temAlgo = true;
            }
        }
        if (!temAnimal) {
            System.out.println("(nenhum)");
        }

        System.out.println("\n--- Casas ---");
        boolean temCasa = false;
        for (Casa c : casas) {
            if (c.dono == this) {
                System.out.printf("• %s - %s (R$ %.2f)%n", c.nome, c.endereco, c.valor);
                totalBens += c.valor;
                temCasa = true;
                temAlgo = true;
            }
        }
        if (!temCasa) {
            System.out.println("(nenhuma)");
        }

        System.out.println("\n--- Carros ---");
        boolean temCarro = false;
        for (Carro cr : carros) {
            if (cr.dono == this) {
                System.out.printf("• %s (%s) (R$ %.2f)%n", cr.nome, cr.marca, cr.valor);
                totalBens += cr.valor;
                temCarro = true;
                temAlgo = true;
            }
        }
        if (!temCarro) {
            System.out.println("(nenhum)");
        }

        if (!temAlgo) {
            System.out.println("\nEsta pessoa não possui bens cadastrados no sistema.");
        }

        double patrimonioLiquido = totalBens + carteira - divida;

        System.out.println("\n--- Resumo Financeiro ---");
        System.out.printf("Total em bens (casas + carros): R$ %.2f%n", totalBens);
        System.out.printf("Carteira: R$ %.2f%n", carteira);
        System.out.printf("Dívida: R$ %.2f%n", divida);
        System.out.printf("Patrimônio líquido: R$ %.2f%n", patrimonioLiquido);

        System.out.println("-------------------------");
    }
}
