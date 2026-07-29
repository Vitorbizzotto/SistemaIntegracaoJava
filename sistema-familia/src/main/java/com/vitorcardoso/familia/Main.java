package com.vitorcardoso.familia;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Pessoa> familia = new ArrayList<>();
        ArrayList<Animal> animais = new ArrayList<>();
        ArrayList<Casa> casas = new ArrayList<>();
        ArrayList<Carro> carros = new ArrayList<>();

        boolean continuar = true;

        while (continuar) {

            System.out.println("\n=== SISTEMA FAMÍLIA — ECOSSISTEMA ===");
            System.out.println("--- Cadastros ---");
            System.out.println("1 - Cadastrar pessoa");
            System.out.println("2 - Cadastrar animal");
            System.out.println("3 - Cadastrar casa");
            System.out.println("4 - Cadastrar carro");
            System.out.println("--- Consultas ---");
            System.out.println("5 - Listar família");
            System.out.println("6 - Listar animais");
            System.out.println("7 - Listar casas");
            System.out.println("8 - Listar carros");
            System.out.println("9 - Ver patrimônio de uma pessoa");
            System.out.println("--- Propriedade ---");
            System.out.println("10 - Atribuir proprietário a um bem");
            System.out.println("11 - Transferir casa");
            System.out.println("12 - Transferir carro");
            System.out.println("13 - Transferir animal");
            System.out.println("--- Financeiro ---");
            System.out.println("14 - Pagar dívida");
            System.out.println("15 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiroPositivo(scanner, "Opção inválida.");
            if (opcao < 0) {
                continue;
            }

            if (opcao == 1) {

                System.out.print("Digite o nome: ");
                String nome = scanner.nextLine();

                System.out.print("Digite a idade: ");
                int idade = lerInteiroPositivo(scanner, "Idade inválida. Cadastro cancelado.");
                if (idade < 0) {
                    continue;
                }

                System.out.print("Digite o parentesco: ");
                String parentesco = scanner.nextLine();

                System.out.print("Saldo inicial da carteira: ");
                double carteira = lerDoubleNaoNegativo(scanner, "Saldo inválido. Cadastro cancelado.");
                if (carteira < 0) {
                    continue;
                }

                if (nome.isBlank()) {
                    System.out.println("O nome não pode ser vazio.");
                    continue;
                }

                familia.add(new Pessoa(nome, idade, parentesco, carteira));
                System.out.println("Pessoa cadastrada com sucesso!");

            } else if (opcao == 2) {

                System.out.print("Digite o nome: ");
                String nomeAnimal = scanner.nextLine();

                System.out.print("Digite a idade: ");
                int idadeAnimal = lerInteiroPositivo(scanner, "Idade inválida. Cadastro cancelado.");
                if (idadeAnimal < 0) {
                    continue;
                }

                System.out.print("Digite a espécie: ");
                String especie = scanner.nextLine();

                if (nomeAnimal.isBlank()) {
                    System.out.println("O nome não pode ser vazio.");
                    continue;
                }

                Pessoa donoAnimal = GestorPatrimonio.escolherProprietarioNoCadastro(scanner, familia, "animal");
                animais.add(new Animal(nomeAnimal, idadeAnimal, especie, donoAnimal));
                System.out.println("Animal cadastrado com sucesso!");

            } else if (opcao == 3) {

                System.out.print("Digite o nome: ");
                String nomeCasa = scanner.nextLine();

                System.out.print("Digite a idade: ");
                int idadeCasa = lerInteiroPositivo(scanner, "Idade inválida. Cadastro cancelado.");
                if (idadeCasa < 0) {
                    continue;
                }

                System.out.print("Digite o endereço: ");
                String endereco = scanner.nextLine();

                System.out.print("Valor da casa: ");
                double valorCasa = lerDoubleNaoNegativo(scanner, "Valor inválido. Cadastro cancelado.");
                if (valorCasa <= 0) {
                    System.out.println("O valor da casa deve ser maior que zero. Cadastro cancelado.");
                    continue;
                }

                if (nomeCasa.isBlank()) {
                    System.out.println("O nome não pode ser vazio.");
                    continue;
                }

                Pessoa donoCasa = GestorPatrimonio.escolherProprietarioNoCadastro(scanner, familia, "casa");
                casas.add(new Casa(nomeCasa, idadeCasa, endereco, valorCasa, donoCasa));
                System.out.println("Casa cadastrada com sucesso!");

            } else if (opcao == 4) {

                System.out.print("Digite o nome: ");
                String nomeCarro = scanner.nextLine();

                System.out.print("Digite a idade: ");
                int idadeCarro = lerInteiroPositivo(scanner, "Idade inválida. Cadastro cancelado.");
                if (idadeCarro < 0) {
                    continue;
                }

                System.out.print("Digite a marca: ");
                String marca = scanner.nextLine();

                System.out.print("Valor do carro: ");
                double valorCarro = lerDoubleNaoNegativo(scanner, "Valor inválido. Cadastro cancelado.");
                if (valorCarro <= 0) {
                    System.out.println("O valor do carro deve ser maior que zero. Cadastro cancelado.");
                    continue;
                }

                if (nomeCarro.isBlank()) {
                    System.out.println("O nome não pode ser vazio.");
                    continue;
                }

                Pessoa donoCarro = GestorPatrimonio.escolherProprietarioNoCadastro(scanner, familia, "carro");
                carros.add(new Carro(nomeCarro, idadeCarro, marca, valorCarro, donoCarro));
                System.out.println("Carro cadastrado com sucesso!");

            } else if (opcao == 5) {

                if (familia.isEmpty()) {
                    System.out.println("Nenhuma pessoa cadastrada.");
                } else {
                    System.out.println("\n=== LISTA DE FAMÍLIA ===");
                    for (Pessoa p : familia) {
                        p.exibirDados();
                    }
                }

            } else if (opcao == 6) {

                if (animais.isEmpty()) {
                    System.out.println("Nenhum animal cadastrado.");
                } else {
                    System.out.println("\n=== LISTA DE ANIMAIS ===");
                    for (Animal a : animais) {
                        a.exibirDados();
                    }
                }

            } else if (opcao == 7) {

                if (casas.isEmpty()) {
                    System.out.println("Nenhuma casa cadastrada.");
                } else {
                    System.out.println("\n=== LISTA DE CASAS ===");
                    for (Casa c : casas) {
                        c.exibirDados();
                    }
                }

            } else if (opcao == 8) {

                if (carros.isEmpty()) {
                    System.out.println("Nenhum carro cadastrado.");
                } else {
                    System.out.println("\n=== LISTA DE CARROS ===");
                    for (Carro cr : carros) {
                        cr.exibirDados();
                    }
                }

            } else if (opcao == 9) {

                Pessoa pessoa = GestorPatrimonio.escolherPessoa(scanner, familia, "=== VER PATRIMÔNIO ===");
                if (pessoa != null) {
                    pessoa.exibirPatrimonio(animais, casas, carros);
                }

            } else if (opcao == 10) {

                GestorPatrimonio.atribuirProprietario(scanner, familia, animais, casas, carros);

            } else if (opcao == 11) {

                GestorPatrimonio.transferirCasa(scanner, familia, casas);

            } else if (opcao == 12) {

                GestorPatrimonio.transferirCarro(scanner, familia, carros);

            } else if (opcao == 13) {

                GestorPatrimonio.transferirAnimal(scanner, familia, animais);

            } else if (opcao == 14) {

                GestorPatrimonio.pagarDivida(scanner, familia);

            } else if (opcao == 15) {

                if (GestorPatrimonio.confirmarAcao(scanner, "Deseja realmente encerrar o sistema")) {
                    continuar = false;
                    System.out.println("Encerrando o sistema...");
                } else {
                    System.out.println("Continuando...");
                }

            } else {

                System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    private static int lerInteiroPositivo(Scanner scanner, String mensagemErro) {
        if (!scanner.hasNextInt()) {
            System.out.println(mensagemErro);
            scanner.nextLine();
            return -1;
        }
        int valor = scanner.nextInt();
        scanner.nextLine();

        if (valor < 0) {
            System.out.println("O valor não pode ser negativo.");
            return -1;
        }
        return valor;
    }

    private static double lerDoubleNaoNegativo(Scanner scanner, String mensagemErro) {
        if (!scanner.hasNextDouble()) {
            System.out.println(mensagemErro);
            scanner.nextLine();
            return -1;
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor < 0) {
            System.out.println("O valor não pode ser negativo.");
            return -1;
        }
        return valor;
    }
}
