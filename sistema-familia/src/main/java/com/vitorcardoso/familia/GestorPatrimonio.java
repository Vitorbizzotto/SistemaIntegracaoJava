package com.vitorcardoso.familia;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorPatrimonio {

    public static void listarPessoas(ArrayList<Pessoa> familia) {
        for (int i = 0; i < familia.size(); i++) {
            Pessoa pessoa = familia.get(i);
            System.out.printf("%d - %s (%s) | Carteira: R$ %.2f | Dívida: R$ %.2f%n",
                    (i + 1), pessoa.nome, pessoa.parentesco, pessoa.carteira, pessoa.divida);
        }
    }

    public static Pessoa escolherPessoa(Scanner scanner, ArrayList<Pessoa> familia, String titulo) {
        if (familia.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return null;
        }

        System.out.println("\n" + titulo);
        listarPessoas(familia);
        System.out.print("Número da pessoa (0 = cancelar): ");

        int numero = lerIntSeguro(scanner);
        if (numero == Integer.MIN_VALUE) {
            return null;
        }

        if (numero == 0) {
            System.out.println("Operação cancelada.");
            return null;
        }

        if (numero < 1 || numero > familia.size()) {
            System.out.println("Número de pessoa inválido.");
            return null;
        }

        return familia.get(numero - 1);
    }

    public static Pessoa escolherProprietarioNoCadastro(Scanner scanner, ArrayList<Pessoa> familia, String tipoBem) {
        if (familia.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada. O " + tipoBem + " será cadastrado sem proprietário.");
            return null;
        }

        System.out.println("\nProprietário do " + tipoBem + " (0 = sem proprietário):");
        listarPessoas(familia);
        System.out.print("Número: ");

        int numero = lerIntSeguro(scanner);
        if (numero == Integer.MIN_VALUE) {
            System.out.println("Entrada inválida. O bem será cadastrado sem proprietário.");
            return null;
        }

        if (numero == 0) {
            return null;
        }

        if (numero < 1 || numero > familia.size()) {
            System.out.println("Número inválido. O bem será cadastrado sem proprietário.");
            return null;
        }

        Pessoa escolhida = familia.get(numero - 1);

        if (("casa".equals(tipoBem) || "carro".equals(tipoBem)) && !escolhida.podeSerProprietarioDeBemImovelOuVeiculo()) {
            System.out.println("Atenção: " + escolhida.nome + " é menor de idade e não pode ser proprietário de "
                    + tipoBem + ". O bem ficará sem proprietário.");
            return null;
        }

        return escolhida;
    }

    public static boolean confirmarAcao(Scanner scanner, String mensagem) {
        System.out.print(mensagem + " (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S") || resposta.equals("SIM");
    }

    public static boolean validarTransferencia(Pessoa donoAtual, Pessoa novoDono, String tipoBem, String nomeBem) {
        if (donoAtual == null) {
            System.out.println("Este " + tipoBem + " não possui proprietário. Use a opção de atribuir proprietário antes.");
            return false;
        }

        if (novoDono == donoAtual) {
            System.out.println("O novo proprietário deve ser diferente do atual (" + donoAtual.nome + ").");
            return false;
        }

        if (("casa".equals(tipoBem) || "carro".equals(tipoBem)) && !novoDono.podeSerProprietarioDeBemImovelOuVeiculo()) {
            System.out.println(novoDono.nome + " é menor de idade e não pode receber a propriedade de um " + tipoBem + ".");
            return false;
        }

        return true;
    }

    public static void transferirCasa(Scanner scanner, ArrayList<Pessoa> familia, ArrayList<Casa> casas) {
        if (casas.isEmpty()) {
            System.out.println("Nenhuma casa cadastrada.");
            return;
        }
        if (familia.size() < 2) {
            System.out.println("É necessário ter pelo menos duas pessoas cadastradas para transferir uma casa.");
            return;
        }

        System.out.println("\n=== TRANSFERIR CASA ===");
        for (int i = 0; i < casas.size(); i++) {
            Casa c = casas.get(i);
            String donoTxt = c.nomeDono() != null ? c.nomeDono() : "sem proprietário";
            System.out.printf("%d - %s (dono atual: %s) - R$ %.2f%n", (i + 1), c.nome, donoTxt, c.valor);
        }

        System.out.print("Número da casa (0 = cancelar): ");
        int numCasa = lerIntSeguro(scanner);
        if (numCasa == Integer.MIN_VALUE) {
            return;
        }

        if (numCasa == 0) {
            System.out.println("Transferência cancelada.");
            return;
        }
        if (numCasa < 1 || numCasa > casas.size()) {
            System.out.println("Número de casa inválido.");
            return;
        }

        Casa casa = casas.get(numCasa - 1);
        Pessoa donoAtual = casa.dono;

        Pessoa novoDono = escolherPessoa(scanner, familia, "Novo proprietário da casa \"" + casa.nome + "\":");
        if (novoDono == null) {
            return;
        }

        if (!validarTransferencia(donoAtual, novoDono, "casa", casa.nome)) {
            return;
        }

        if (executarVenda(scanner, "casa", casa.nome, casa.valor, donoAtual, novoDono)) {
            casa.setDono(novoDono);
        }
    }

    public static void transferirCarro(Scanner scanner, ArrayList<Pessoa> familia, ArrayList<Carro> carros) {
        if (carros.isEmpty()) {
            System.out.println("Nenhum carro cadastrado.");
            return;
        }
        if (familia.size() < 2) {
            System.out.println("É necessário ter pelo menos duas pessoas cadastradas para transferir um carro.");
            return;
        }

        System.out.println("\n=== TRANSFERIR CARRO ===");
        for (int i = 0; i < carros.size(); i++) {
            Carro c = carros.get(i);
            String donoTxt = c.nomeDono() != null ? c.nomeDono() : "sem proprietário";
            System.out.printf("%d - %s (dono atual: %s) - R$ %.2f%n", (i + 1), c.nome, donoTxt, c.valor);
        }

        System.out.print("Número do carro (0 = cancelar): ");
        int numCarro = lerIntSeguro(scanner);
        if (numCarro == Integer.MIN_VALUE) {
            return;
        }

        if (numCarro == 0) {
            System.out.println("Transferência cancelada.");
            return;
        }
        if (numCarro < 1 || numCarro > carros.size()) {
            System.out.println("Número de carro inválido.");
            return;
        }

        Carro carro = carros.get(numCarro - 1);
        Pessoa donoAtual = carro.dono;

        Pessoa novoDono = escolherPessoa(scanner, familia, "Novo proprietário do carro \"" + carro.nome + "\":");
        if (novoDono == null) {
            return;
        }

        if (!validarTransferencia(donoAtual, novoDono, "carro", carro.nome)) {
            return;
        }

        if (executarVenda(scanner, "carro", carro.nome, carro.valor, donoAtual, novoDono)) {
            carro.setDono(novoDono);
        }
    }

    // Método privado e estático que executa a venda de um bem (casa ou carro) entre duas pessoas.
    // Recebe o Scanner para ler respostas do usuário, o tipo do bem (ex.: "casa"/"carro"),
    // o nome do bem, o valor do bem, o vendedor e o comprador.
    // Retorna true se a venda foi confirmada (cabe a quem chamou aplicar a troca de dono
    // no objeto correto, ex.: casa.setDono(comprador)) e false caso contrário.
    private static boolean executarVenda(Scanner scanner, String tipoBem, String nomeBem, double valorBem,
            Pessoa vendedor, Pessoa comprador) {

        // Guarda em uma variável local o saldo atual da carteira do comprador.
        double saldoComprador = comprador.carteira;
        // Calcula quanto está faltando para o comprador pagar o bem à vista.
        // Usa Math.max para garantir que nunca fique negativo (se sobrar dinheiro, "faltante" é 0).
        double faltante = Math.max(0, valorBem - saldoComprador);

        // Imprime no console um cabeçalho separando visualmente o resumo da venda.
        System.out.println("\n--- Resumo da venda ---");
        // Mostra qual bem está sendo vendido, com tipo, nome e valor formatado em reais.
        System.out.printf("Bem: %s \"%s\" (R$ %.2f)%n", tipoBem, nomeBem, valorBem);
        // Mostra o nome do vendedor e quanto ele tem hoje na carteira.
        System.out.printf("Vendedor: %s (carteira atual: R$ %.2f)%n", vendedor.nome, vendedor.carteira);
        // Mostra o nome do comprador, sua carteira atual e a dívida atual dele.
        System.out.printf("Comprador: %s (carteira atual: R$ %.2f, dívida atual: R$ %.2f)%n",
                comprador.nome, comprador.carteira, comprador.divida);

        // Declara a variável que vai guardar o valor efetivamente pago do bolso do comprador.
        double valorPago;
        // Declara a variável que vai guardar o valor do empréstimo (se for necessário).
        double valorEmprestimo;

        // Se não falta nada, o comprador consegue pagar tudo à vista.
        if (faltante == 0) {
            // O valor pago é o valor cheio do bem.
            valorPago = valorBem;
            // Não há necessidade de empréstimo.
            valorEmprestimo = 0;
            // Informa ao usuário que a compra será feita à vista.
            System.out.printf("Pagamento à vista: R$ %.2f%n", valorPago);
        } else {
            // Caso contrário, avisa o usuário quanto falta para fechar a compra.
            System.out.printf("Saldo insuficiente. Faltam R$ %.2f para concluir a compra.%n", faltante);
            // Pergunta se o comprador deseja contratar um empréstimo para cobrir o que falta.
            // Se a resposta for "não" (confirmarAcao retorna false), a venda é cancelada.
            if (!confirmarAcao(scanner, String.format("Deseja contratar empréstimo de R$ %.2f (sem juros) para cobrir o restante?", faltante))) {
                // Informa o cancelamento da operação.
                System.out.println("Venda cancelada por saldo insuficiente.");
                // Sai do método sem efetuar a venda.
                return false;
            }
            // Se aceitou o empréstimo, ele paga tudo o que tem na carteira...
            valorPago = saldoComprador;
            // ...e o restante vira dívida.
            valorEmprestimo = faltante;
            // Mostra quanto vai ser debitado da carteira do comprador.
            System.out.printf("Será debitado da carteira: R$ %.2f%n", valorPago);
            // Mostra quanto vai ser somado na dívida do comprador.
            System.out.printf("Será adicionado à dívida: R$ %.2f%n", valorEmprestimo);
        }

        // Calcula como ficará a carteira do comprador depois da venda (preview).
        double saldoFinalComprador = comprador.carteira - valorPago;
        // Calcula como ficará a dívida do comprador depois da venda (preview).
        double dividaFinalComprador = comprador.divida + valorEmprestimo;
        // Calcula como ficará a carteira do vendedor depois da venda (preview).
        double saldoFinalVendedor = vendedor.carteira + valorBem;

        // Imprime cabeçalho mostrando o estado financeiro previsto após a venda.
        System.out.println("\n--- Após a venda ---");
        // Mostra a previsão de carteira e dívida do comprador.
        System.out.printf("%s: carteira R$ %.2f, dívida R$ %.2f%n",
                comprador.nome, saldoFinalComprador, dividaFinalComprador);
        // Mostra a previsão de carteira do vendedor.
        System.out.printf("%s: carteira R$ %.2f%n", vendedor.nome, saldoFinalVendedor);

        // Monta a mensagem final de confirmação, identificando o bem, vendedor e comprador.
        String msgConfirmacao = String.format("Confirmar venda do %s \"%s\" de %s para %s?",
                tipoBem, nomeBem, vendedor.nome, comprador.nome);

        // Pede a confirmação final ao usuário; se ele recusar, a venda não acontece.
        if (!confirmarAcao(scanner, msgConfirmacao)) {
            // Informa que a venda não foi realizada.
            System.out.println("Venda não realizada.");
            // Sai do método sem alterar nada nas carteiras nem nos bens.
            return false;
        }

        // A partir daqui a venda foi confirmada: aplica de fato as alterações.
        // Debita da carteira do comprador o valor efetivamente pago.
        comprador.debitar(valorPago);
        // Se houve empréstimo, soma esse valor na dívida do comprador.
        if (valorEmprestimo > 0 ) {
            comprador.aumentarDivida(valorEmprestimo);
        }
        // Credita ao vendedor o valor cheio do bem (ele recebe tudo, mesmo com empréstimo).
        vendedor.creditar(valorBem);

        // Mensagem final indicando o sucesso da venda. Character.toUpperCase + substring(1)
        // são usados apenas para deixar a primeira letra do tipo do bem em maiúsculo.
        System.out.printf("Venda concluída! %s \"%s\" agora pertence a %s.%n",
                Character.toUpperCase(tipoBem.charAt(0)) + tipoBem.substring(1),
                nomeBem, comprador.nome);

        // Sinaliza para quem chamou que a venda foi efetuada e a troca de dono pode ser aplicada.
        return true;
    }

    public static void transferirAnimal(Scanner scanner, ArrayList<Pessoa> familia, ArrayList<Animal> animais) {
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }
        if (familia.size() < 2) {
            System.out.println("É necessário ter pelo menos duas pessoas cadastradas para transferir um animal.");
            return;
        }

        System.out.println("\n=== TRANSFERIR ANIMAL ===");
        for (int i = 0; i < animais.size(); i++) {
            Animal animal = animais.get(i);
            String donoTxt = animal.nomeDono() != null ? animal.nomeDono() : "sem proprietário";
            System.out.println((i + 1) + " - " + animal.nome + " (dono atual: " + donoTxt + ")");
        }

        System.out.print("Número do animal (0 = cancelar): ");
        int numAnimal = lerIntSeguro(scanner);
        if (numAnimal == Integer.MIN_VALUE) {
            return;
        }

        if (numAnimal == 0) {
            System.out.println("Transferência cancelada.");
            return;
        }
        if (numAnimal < 1 || numAnimal > animais.size()) {
            System.out.println("Número de animal inválido.");
            return;
        }

        Animal animal = animais.get(numAnimal - 1);
        Pessoa donoAtual = animal.dono;

        Pessoa novoDono = escolherPessoa(scanner, familia, "Novo proprietário do animal \"" + animal.nome + "\":");
        if (novoDono == null) {
            return;
        }

        if (!validarTransferencia(donoAtual, novoDono, "animal", animal.nome)) {
            return;
        }

        String msgConfirmacao = "Tem certeza que deseja transferir o animal \"" + animal.nome + "\" de "
                + donoAtual.nome + " para " + novoDono.nome + "?";

        if (!confirmarAcao(scanner, msgConfirmacao)) {
            System.out.println("Transferência não realizada.");
            return;
        }

        animal.setDono(novoDono);
        System.out.println("Animal \"" + animal.nome + "\" transferido com sucesso para " + novoDono.nome + ".");
    }

    public static void atribuirProprietario(Scanner scanner, ArrayList<Pessoa> familia,
            ArrayList<Animal> animais, ArrayList<Casa> casas, ArrayList<Carro> carros) {

        if (familia.isEmpty()) {
            System.out.println("Cadastre pelo menos uma pessoa antes de atribuir proprietário.");
            return;
        }

        System.out.println("\n=== ATRIBUIR PROPRIETÁRIO ===");
        System.out.println("1 - Animal");
        System.out.println("2 - Casa");
        System.out.println("3 - Carro");
        System.out.println("0 - Cancelar");
        System.out.print("Tipo de bem: ");

        int tipo = lerIntSeguro(scanner);
        if (tipo == Integer.MIN_VALUE) {
            return;
        }

        if (tipo == 0) {
            System.out.println("Operação cancelada.");
            return;
        }

        Pessoa novoDono = escolherPessoa(scanner, familia, "Escolha o proprietário:");
        if (novoDono == null) {
            return;
        }

        if (tipo == 1) {
            atribuirDonoAnimal(scanner, animais, novoDono);
        } else if (tipo == 2) {
            atribuirDonoCasa(scanner, casas, novoDono);
        } else if (tipo == 3) {
            atribuirDonoCarro(scanner, carros, novoDono);
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    public static void pagarDivida(Scanner scanner, ArrayList<Pessoa> familia) {
        if (familia.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }

        Pessoa pessoa = escolherPessoa(scanner, familia, "=== PAGAR DÍVIDA ===");
        if (pessoa == null) {
            return;
        }

        if (!pessoa.temDivida()) {
            System.out.println(pessoa.nome + " não possui dívida.");
            return;
        }

        if (pessoa.carteira <= 0) {
            System.out.printf("%s não possui saldo na carteira para pagar a dívida (Dívida: R$ %.2f).%n",
                    pessoa.nome, pessoa.divida);
            return;
        }

        double maximoPagavel = Math.min(pessoa.divida, pessoa.carteira);
        System.out.printf("%nCarteira: R$ %.2f | Dívida: R$ %.2f%n", pessoa.carteira, pessoa.divida);
        System.out.printf("Valor máximo que pode ser pago agora: R$ %.2f%n", maximoPagavel);
        System.out.print("Valor a pagar (0 = cancelar): ");

        if (!scanner.hasNextDouble()) {
            System.out.println("Valor inválido. Operação cancelada.");
            scanner.nextLine();
            return;
        }

        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor == 0) {
            System.out.println("Operação cancelada.");
            return;
        }

        if (valor < 0) {
            System.out.println("O valor não pode ser negativo.");
            return;
        }

        if (valor > maximoPagavel) {
            System.out.printf("Valor maior que o permitido. Máximo: R$ %.2f.%n", maximoPagavel);
            return;
        }

        double saldoFinal = pessoa.carteira - valor;
        double dividaFinal = pessoa.divida - valor;
        String msg = String.format(
                "Confirmar pagamento de R$ %.2f da dívida de %s? (Após: carteira R$ %.2f, dívida R$ %.2f)",
                valor, pessoa.nome, saldoFinal, dividaFinal);

        if (!confirmarAcao(scanner, msg)) {
            System.out.println("Pagamento não realizado.");
            return;
        }

        pessoa.pagarDivida(valor);
        System.out.printf("Pagamento realizado! Carteira: R$ %.2f | Dívida: R$ %.2f%n",
                pessoa.carteira, pessoa.divida);
    }

    private static void atribuirDonoAnimal(Scanner scanner, ArrayList<Animal> animais, Pessoa novoDono) {
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }

        Animal animal = escolherAnimal(scanner, animais);
        if (animal == null) {
            return;
        }

        if (animal.dono == novoDono) {
            System.out.println(novoDono.nome + " já é o proprietário deste animal.");
            return;
        }

        String de = animal.dono != null ? animal.dono.nome : "ninguém";
        String msg = "Atribuir o animal \"" + animal.nome + "\" de " + de + " para " + novoDono.nome + "?";

        if (!confirmarAcao(scanner, msg)) {
            System.out.println("Atribuição não realizada.");
            return;
        }

        animal.setDono(novoDono);
        System.out.println("Proprietário do animal \"" + animal.nome + "\" atualizado para " + novoDono.nome + ".");
    }

    private static void atribuirDonoCasa(Scanner scanner, ArrayList<Casa> casas, Pessoa novoDono) {
        if (casas.isEmpty()) {
            System.out.println("Nenhuma casa cadastrada.");
            return;
        }

        if (!novoDono.podeSerProprietarioDeBemImovelOuVeiculo()) {
            System.out.println(novoDono.nome + " é menor de idade e não pode ser proprietário de casa.");
            return;
        }

        Casa casa = escolherCasa(scanner, casas);
        if (casa == null) {
            return;
        }

        if (casa.dono == novoDono) {
            System.out.println(novoDono.nome + " já é o proprietário desta casa.");
            return;
        }

        String de = casa.dono != null ? casa.dono.nome : "ninguém";
        String msg = "Atribuir a casa \"" + casa.nome + "\" de " + de + " para " + novoDono.nome + "?";

        if (!confirmarAcao(scanner, msg)) {
            System.out.println("Atribuição não realizada.");
            return;
        }

        casa.setDono(novoDono);
        System.out.println("Proprietário da casa \"" + casa.nome + "\" atualizado para " + novoDono.nome + ".");
    }

    private static void atribuirDonoCarro(Scanner scanner, ArrayList<Carro> carros, Pessoa novoDono) {
        if (carros.isEmpty()) {
            System.out.println("Nenhum carro cadastrado.");
            return;
        }

        if (!novoDono.podeSerProprietarioDeBemImovelOuVeiculo()) {
            System.out.println(novoDono.nome + " é menor de idade e não pode ser proprietário de carro.");
            return;
        }

        Carro carro = escolherCarro(scanner, carros);
        if (carro == null) {
            return;
        }

        if (carro.dono == novoDono) {
            System.out.println(novoDono.nome + " já é o proprietário deste carro.");
            return;
        }

        String de = carro.dono != null ? carro.dono.nome : "ninguém";
        String msg = "Atribuir o carro \"" + carro.nome + "\" de " + de + " para " + novoDono.nome + "?";

        if (!confirmarAcao(scanner, msg)) {
            System.out.println("Atribuição não realizada.");
            return;
        }

        carro.setDono(novoDono);
        System.out.println("Proprietário do carro \"" + carro.nome + "\" atualizado para " + novoDono.nome + ".");
    }

    private static Animal escolherAnimal(Scanner scanner, ArrayList<Animal> animais) {
        for (int i = 0; i < animais.size(); i++) {
            Animal a = animais.get(i);
            String donoTxt = a.nomeDono() != null ? a.nomeDono() : "sem proprietário";
            System.out.println((i + 1) + " - " + a.nome + " (" + donoTxt + ")");
        }
        System.out.print("Número (0 = cancelar): ");
        int n = lerIntSeguro(scanner);
        if (n == Integer.MIN_VALUE) {
            return null;
        }
        if (n == 0) {
            System.out.println("Operação cancelada.");
            return null;
        }
        if (n < 1 || n > animais.size()) {
            System.out.println("Número inválido.");
            return null;
        }
        return animais.get(n - 1);
    }

    private static Casa escolherCasa(Scanner scanner, ArrayList<Casa> casas) {
        for (int i = 0; i < casas.size(); i++) {
            Casa c = casas.get(i);
            String donoTxt = c.nomeDono() != null ? c.nomeDono() : "sem proprietário";
            System.out.printf("%d - %s (%s) - R$ %.2f%n", (i + 1), c.nome, donoTxt, c.valor);
        }
        System.out.print("Número (0 = cancelar): ");
        int n = lerIntSeguro(scanner);
        if (n == Integer.MIN_VALUE) {
            return null;
        }
        if (n == 0) {
            System.out.println("Operação cancelada.");
            return null;
        }
        if (n < 1 || n > casas.size()) {
            System.out.println("Número inválido.");
            return null;
        }
        return casas.get(n - 1);
    }

    private static Carro escolherCarro(Scanner scanner, ArrayList<Carro> carros) {
        for (int i = 0; i < carros.size(); i++) {
            Carro c = carros.get(i);
            String donoTxt = c.nomeDono() != null ? c.nomeDono() : "sem proprietário";
            System.out.printf("%d - %s (%s) - R$ %.2f%n", (i + 1), c.nome, donoTxt, c.valor);
        }
        System.out.print("Número (0 = cancelar): ");
        int n = lerIntSeguro(scanner);
        if (n == Integer.MIN_VALUE) {
            return null;
        }
        if (n == 0) {
            System.out.println("Operação cancelada.");
            return null;
        }
        if (n < 1 || n > carros.size()) {
            System.out.println("Número inválido.");
            return null;
        }
        return carros.get(n - 1);
    }

    private static int lerIntSeguro(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
            return Integer.MIN_VALUE;
        }
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }
}
