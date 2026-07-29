# Sistema Família — Ecossistema

> Sistema de console em Java para cadastro e gerenciamento de uma família, seus animais, casas, carros, carteira e dívidas.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.13-blue.svg)](https://maven.apache.org/)

---

## Sumário

- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Como Executar](#como-executar)
- [Menu e Funcionalidades](#menu-e-funcionalidades)
- [Documentação das Classes](#documentação-das-classes)
  - [Pessoa](#classe-pessoa)
  - [Animal](#classe-animal)
  - [Casa](#classe-casa)
  - [Carro](#classe-carro)
  - [GestorPatrimonio](#classe-gestorpatrimonio)
  - [Main](#classe-main)
- [Regras de Negócio](#regras-de-negócio)
- [Fluxos Principais](#fluxos-principais)
- [Exemplos de Uso](#exemplos-de-uso)
- [Diagrama de Relacionamentos](#diagrama-de-relacionamentos)
- [Possíveis Melhorias](#possíveis-melhorias)

---

## Visão Geral

O **Sistema Família — Ecossistema** é uma aplicação Java (console) que simula o cadastro e a administração patrimonial de uma família. O usuário pode:

- Cadastrar **pessoas**, **animais**, **casas** e **carros**.
- Vincular bens (animais, casas e carros) a um proprietário.
- Acompanhar **carteira** (dinheiro disponível) e **dívida** de cada pessoa.
- **Transferir** bens entre membros, simulando uma compra/venda com dinheiro e até **empréstimo automático** quando o comprador não tem saldo total.
- **Pagar dívidas** parcialmente ou totalmente.
- Consultar o **patrimônio líquido** de qualquer pessoa (bens + carteira – dívida).

O projeto foi pensado como exercício didático de **Programação Orientada a Objetos (POO)** com foco em encapsulamento simples, composição de objetos e validação de regras de negócio em um menu CLI.

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Maven | 3.13.x (plugin compiler) |
| `exec-maven-plugin` | 3.5.0 |

Sem dependências externas (apenas a biblioteca padrão do Java: `java.util.ArrayList`, `java.util.Scanner`).

---

## Estrutura do Projeto

```
sistema-familia/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── vitorcardoso/
                    └── familia/
                        ├── Main.java              # Ponto de entrada e menu
                        ├── Pessoa.java            # Modelo: membros da família
                        ├── Animal.java            # Modelo: animais
                        ├── Casa.java              # Modelo: casas (imóveis)
                        ├── Carro.java             # Modelo: carros (veículos)
                        └── GestorPatrimonio.java  # Lógica de propriedade e financeiro
```

---

## Como Executar

### Pré-requisitos
- JDK **17+** instalado e configurado no `PATH`.
- Maven **3.6+** instalado.

### Compilar
```bash
mvn clean compile
```

### Executar (via Maven)
```bash
mvn exec:java
```
> A `mainClass` já está configurada como `com.vitorcardoso.familia.Main` no `pom.xml`.

### Executar (sem Maven)
```bash
javac -d target/classes src/main/java/com/vitorcardoso/familia/*.java
java -cp target/classes com.vitorcardoso.familia.Main
```

---

## Menu e Funcionalidades

Ao iniciar o sistema, o seguinte menu é exibido em loop até o usuário escolher sair:

```
=== SISTEMA FAMÍLIA — ECOSSISTEMA ===
--- Cadastros ---
 1 - Cadastrar pessoa
 2 - Cadastrar animal
 3 - Cadastrar casa
 4 - Cadastrar carro
--- Consultas ---
 5 - Listar família
 6 - Listar animais
 7 - Listar casas
 8 - Listar carros
 9 - Ver patrimônio de uma pessoa
--- Propriedade ---
10 - Atribuir proprietário a um bem
11 - Transferir casa
12 - Transferir carro
13 - Transferir animal
--- Financeiro ---
14 - Pagar dívida
15 - Sair
```

| # | Opção | Descrição |
|---|---|---|
| 1 | Cadastrar pessoa | Cria uma `Pessoa` com nome, idade, parentesco e saldo inicial. |
| 2 | Cadastrar animal | Cria um `Animal` opcionalmente vinculado a um dono. |
| 3 | Cadastrar casa | Cria uma `Casa` com valor maior que 0, vinculável a um dono **maior de 18 anos**. |
| 4 | Cadastrar carro | Cria um `Carro` com valor maior que 0, vinculável a um dono **maior de 18 anos**. |
| 5–8 | Listagens | Mostram todos os registros e suas informações detalhadas. |
| 9 | Ver patrimônio | Lista todos os bens de uma pessoa e calcula **patrimônio líquido**. |
| 10 | Atribuir proprietário | Define/atualiza o dono de um bem **sem movimentar dinheiro**. |
| 11 | Transferir casa | **Venda da casa** entre membros, com débito de carteira e possível empréstimo. |
| 12 | Transferir carro | **Venda do carro** entre membros, com débito de carteira e possível empréstimo. |
| 13 | Transferir animal | Transferência simples de dono (sem custo). |
| 14 | Pagar dívida | Quita parcial/totalmente a dívida usando a carteira da pessoa. |
| 15 | Sair | Encerra o sistema com confirmação. |

---

## Documentação das Classes

### Classe `Pessoa`

Representa um membro da família.

#### Atributos
| Campo | Tipo | Descrição |
|---|---|---|
| `nome` | `String` | Nome da pessoa. |
| `idade` | `int` | Idade em anos. |
| `parentesco` | `String` | Ex.: "Pai", "Mãe", "Filho(a)", etc. |
| `carteira` | `double` | Saldo disponível em reais. |
| `divida` | `double` | Dívida atual em reais. Inicializa em `0`. |

#### Métodos

```13:19:src/main/java/com/vitorcardoso/familia/Pessoa.java
public Pessoa(String nome, int idade, String parentesco, double carteira) {
    this.nome = nome;
    this.idade = idade;
    this.parentesco = parentesco;
    this.carteira = carteira;
    this.divida = 0;
}
```

| Método | Retorno | Descrição |
|---|---|---|
| `podeSerProprietarioDeBemImovelOuVeiculo()` | `boolean` | Retorna `true` se `idade >= 18`. |
| `temSaldo(double valor)` | `boolean` | Retorna `true` se `carteira >= valor`. |
| `temDivida()` | `boolean` | Retorna `true` se `divida > 0`. |
| `creditar(double valor)` | `void` | Adiciona um valor positivo à carteira. |
| `debitar(double valor)` | `void` | Subtrai um valor positivo da carteira. |
| `aumentarDivida(double valor)` | `void` | Soma valor positivo à dívida. |
| `pagarDivida(double valor)` | `void` | Paga o **menor** entre valor solicitado, dívida atual e carteira. |
| `exibirDados()` | `void` | Imprime dados e status (maior/menor de idade). |
| `exibirPatrimonio(animais, casas, carros)` | `void` | Lista todos os bens da pessoa e calcula **patrimônio líquido** = `totalBens + carteira − dívida`. |

> **Observação**: todos os métodos de movimentação financeira (`creditar`, `debitar`, `aumentarDivida`, `pagarDivida`) ignoram silenciosamente valores `<= 0`, protegendo contra entradas inválidas.

---

### Classe `Animal`

Representa um animal de estimação. Diferente de casas e carros, **não tem valor monetário**.

#### Atributos
| Campo | Tipo | Descrição |
|---|---|---|
| `nome` | `String` | Nome do animal. |
| `idade` | `int` | Idade em anos. |
| `especie` | `String` | Ex.: "Cachorro", "Gato". |
| `dono` | `Pessoa` | Referência ao proprietário (pode ser `null`). |

#### Métodos
| Método | Descrição |
|---|---|
| `setDono(Pessoa)` | Atualiza o proprietário. |
| `nomeDono()` | Retorna o nome do dono ou `null`. |
| `exibirDados()` | Imprime os dados e status (`Adulto` se `idade >= 1`, senão `Filhote`). |

> **Regra**: animais **não exigem maioridade** para o dono — qualquer pessoa cadastrada pode ser proprietária.

---

### Classe `Casa`

Representa um imóvel.

#### Atributos
| Campo | Tipo | Descrição |
|---|---|---|
| `nome` | `String` | Apelido/identificação. |
| `idade` | `int` | Idade da construção em anos. |
| `endereco` | `String` | Endereço completo. |
| `valor` | `double` | Valor monetário (deve ser > 0). |
| `dono` | `Pessoa` | Proprietário atual (pode ser `null`). |

#### Métodos
| Método | Descrição |
|---|---|
| `setDono(Pessoa)` | Atualiza o proprietário. |
| `nomeDono()` | Retorna o nome do dono ou `null`. |
| `exibirDados()` | Imprime os dados e status (`Casa antiga` se `idade >= 20`, senão `Casa recente`). |

> **Regra**: somente pessoas com **18 anos ou mais** podem ser proprietárias de casa.

---

### Classe `Carro`

Representa um veículo.

#### Atributos
| Campo | Tipo | Descrição |
|---|---|---|
| `nome` | `String` | Identificação/modelo. |
| `idade` | `int` | Anos de uso. |
| `marca` | `String` | Ex.: "Toyota", "Volkswagen". |
| `valor` | `double` | Valor monetário (deve ser > 0). |
| `dono` | `Pessoa` | Proprietário atual (pode ser `null`). |

#### Métodos
| Método | Descrição |
|---|---|
| `setDono(Pessoa)` | Atualiza o proprietário. |
| `nomeDono()` | Retorna o nome do dono ou `null`. |
| `exibirDados()` | Imprime os dados e status (`Usado` se `idade >= 5`, senão `Seminovo`). |

> **Regra**: somente pessoas com **18 anos ou mais** podem ser proprietárias de carro.

---

### Classe `GestorPatrimonio`

Classe utilitária (apenas métodos estáticos) que concentra a lógica de:
- listar e escolher pessoas/bens
- validar transferências
- executar venda de casa/carro (com possibilidade de empréstimo)
- atribuir proprietário sem transação financeira
- pagar dívida

#### Métodos públicos

| Método | Descrição |
|---|---|
| `listarPessoas(familia)` | Imprime a lista numerada de pessoas com carteira e dívida. |
| `escolherPessoa(scanner, familia, titulo)` | Pede ao usuário um número e retorna a `Pessoa` correspondente (ou `null` em caso de cancelamento/erro). |
| `escolherProprietarioNoCadastro(scanner, familia, tipoBem)` | Usada nos cadastros de animal/casa/carro. Permite `0` para "sem proprietário" e aplica regra de maioridade para casas/carros. |
| `confirmarAcao(scanner, mensagem)` | Pergunta `S/N` e retorna `true` para `S` ou `SIM` (case-insensitive). |
| `validarTransferencia(donoAtual, novoDono, tipoBem, nomeBem)` | Garante que: o bem tem dono, o novo dono é diferente do atual, e (para casa/carro) o novo dono é maior de idade. |
| `transferirCasa(scanner, familia, casas)` | Executa fluxo completo de venda de casa. |
| `transferirCarro(scanner, familia, carros)` | Executa fluxo completo de venda de carro. |
| `transferirAnimal(scanner, familia, animais)` | Transferência simples de animal (sem dinheiro). |
| `atribuirProprietario(scanner, familia, animais, casas, carros)` | Define dono de um bem **sem custo financeiro** (uso interno: regularização). |
| `pagarDivida(scanner, familia)` | Permite pagamento parcial/total da dívida de uma pessoa. |

#### Método privado central: `executarVenda`

```208:306:src/main/java/com/vitorcardoso/familia/GestorPatrimonio.java
private static boolean executarVenda(Scanner scanner, String tipoBem, String nomeBem, double valorBem,
        Pessoa vendedor, Pessoa comprador) {
    ...
}
```

Reutilizado por `transferirCasa` e `transferirCarro`. Fluxo:

1. Calcula `faltante = max(0, valorBem - carteiraComprador)`.
2. Se `faltante == 0` → pagamento **à vista**.
3. Se `faltante > 0` → pergunta se o comprador aceita **empréstimo sem juros** para cobrir a diferença. Recusou? Venda cancelada.
4. Mostra preview de como ficarão as carteiras e a dívida após a operação.
5. Confirma a venda (`S/N`).
6. Aplica: `comprador.debitar(carteiraDisponível)` + `comprador.aumentarDivida(faltante)` + `vendedor.creditar(valorBem)`.
7. Retorna `true` se o dono deve ser trocado, `false` caso contrário.

#### Métodos auxiliares privados
| Método | Descrição |
|---|---|
| `atribuirDonoAnimal` / `atribuirDonoCasa` / `atribuirDonoCarro` | Implementam a opção 10 do menu para cada tipo de bem. |
| `escolherAnimal` / `escolherCasa` / `escolherCarro` | Listam os bens e devolvem o escolhido. |
| `lerIntSeguro(scanner)` | Lê um `int` sem lançar exceção; retorna `Integer.MIN_VALUE` em entrada inválida. |

---

### Classe `Main`

Ponto de entrada. Responsável por:
- Criar as listas `familia`, `animais`, `casas`, `carros`.
- Manter o loop do menu.
- Validar entradas básicas (nomes não-vazios, idades e valores não-negativos).
- Delegar operações complexas para `GestorPatrimonio`.

#### Métodos auxiliares (privados)

```248:262:src/main/java/com/vitorcardoso/familia/Main.java
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
```

| Método | Descrição |
|---|---|
| `lerInteiroPositivo(scanner, msgErro)` | Lê um `int` ≥ 0; retorna `-1` em erro. |
| `lerDoubleNaoNegativo(scanner, msgErro)` | Lê um `double` ≥ 0; retorna `-1` em erro. |

---

## Regras de Negócio

| Regra | Aplicada em |
|---|---|
| Idade mínima de **18 anos** para ser dono de **casa** ou **carro**. | Cadastro, atribuição e transferência. |
| Animais podem pertencer a qualquer pessoa cadastrada (sem restrição de idade). | Cadastro, atribuição e transferência. |
| O nome de um cadastro **não pode ser vazio**. | Cadastros de pessoa, animal, casa e carro. |
| Idade e valores monetários **não podem ser negativos**. | Todos os cadastros. |
| Valor de **casa/carro** deve ser **> 0** (não pode ser zero). | Cadastros de casa e carro. |
| Em uma venda, o **vendedor sempre recebe o valor cheio**, mesmo que o comprador faça empréstimo. | `executarVenda`. |
| O **empréstimo automático é sem juros**; só vira dívida do comprador. | `executarVenda`. |
| Não é possível transferir um bem para o **mesmo dono atual**. | `validarTransferencia`. |
| Não é possível transferir um bem **sem dono** (use "atribuir proprietário" antes). | `validarTransferencia`. |
| Pagamento de dívida nunca excede o **menor valor entre dívida e carteira**. | `Pessoa.pagarDivida` e `GestorPatrimonio.pagarDivida`. |

---

## Fluxos Principais

### 1) Cadastro de uma pessoa
1. Usuário escolhe opção `1`.
2. Sistema solicita nome, idade, parentesco e saldo inicial.
3. Cada entrada inválida cancela o cadastro com mensagem clara.
4. Pessoa válida é adicionada à lista `familia`.

### 2) Transferência (venda) de casa/carro
1. Sistema lista os bens disponíveis.
2. Usuário escolhe o bem e o novo dono.
3. `validarTransferencia` verifica:
   - bem tem dono atual?
   - novo dono é diferente do atual?
   - novo dono é maior de idade (casa/carro)?
4. `executarVenda` calcula se há saldo suficiente:
   - **À vista**: debita a carteira do comprador, credita o vendedor.
   - **Com empréstimo**: usuário confirma → debita o que o comprador tem, soma o restante na dívida, credita o vendedor pelo valor cheio.
5. Após confirmação final, atualiza o `dono` do bem.

### 3) Pagamento de dívida
1. Sistema lista pessoas.
2. Usuário escolhe a pessoa e o valor a pagar.
3. Sistema garante que o valor está entre `0` e `min(dívida, carteira)`.
4. Após confirmação, debita carteira e reduz dívida no mesmo valor.

---

## Exemplos de Uso

### Cadastrando família + bem + transferência com empréstimo

```
1 (Cadastrar pessoa)  → Pai, 45, Pai, 5000
1 (Cadastrar pessoa)  → Mãe, 40, Mãe, 3000
1 (Cadastrar pessoa)  → Filho, 22, Filho, 1000

3 (Cadastrar casa)    → Casa de praia, 10, Av. Beira-Mar, 200000, dono = Pai

11 (Transferir casa)  → Casa de praia → Filho
   Saldo insuficiente. Faltam R$ 199000,00 para concluir a compra.
   Deseja contratar empréstimo de R$ 199000,00 (sem juros) para cobrir o restante? S
   Confirmar venda? S

   → Filho: carteira R$ 0,00, dívida R$ 199000,00
   → Pai:   carteira R$ 205000,00

14 (Pagar dívida)     → Filho, 500
   → Carteira R$ 0,00 (não tem para pagar a dívida)
```

### Patrimônio
```
9 → Pai
=== PATRIMÔNIO DE PAI ===
--- Animais ---
(nenhum)
--- Casas ---
(nenhuma — já transferiu)
--- Carros ---
(nenhum)

--- Resumo Financeiro ---
Total em bens: R$ 0,00
Carteira: R$ 205000,00
Dívida:   R$ 0,00
Patrimônio líquido: R$ 205000,00
```

---

## Diagrama de Relacionamentos

```
                  ┌──────────────┐
                  │    Pessoa    │
                  │--------------│
                  │ nome         │
                  │ idade        │
                  │ parentesco   │
                  │ carteira     │
                  │ divida       │
                  └──────┬───────┘
                         │ 1
              ┌──────────┼──────────┬──────────────┐
              │ 0..*     │ 0..*     │ 0..*         │
        ┌─────▼────┐ ┌───▼─────┐ ┌──▼──────┐
        │  Animal  │ │  Casa   │ │  Carro  │
        │----------│ │---------│ │---------│
        │ nome     │ │ nome    │ │ nome    │
        │ idade    │ │ idade   │ │ idade   │
        │ especie  │ │ endereco│ │ marca   │
        │ dono ────┘ │ valor   │ │ valor   │
                     │ dono ───┘ │ dono ───┘
                     └─────────┘  └────────┘
```

Cada bem (`Animal`, `Casa`, `Carro`) referencia **uma** `Pessoa` (ou `null` se sem proprietário). Uma `Pessoa` pode possuir **vários** bens (relação 1:N).

`GestorPatrimonio` é apenas **utilitária**: não armazena estado, apenas opera sobre as listas recebidas por parâmetro do `Main`.

---

## Possíveis Melhorias

Lista de ideias para evolução futura (não implementadas):

- [ ] Tornar os atributos das classes **privados** e expor via `getters/setters`.
- [ ] Criar uma **superclasse abstrata** `Bem` (com `nome`, `idade`, `dono`, `exibirDados`) para reduzir duplicação entre `Casa`, `Carro` e `Animal`.
- [ ] Persistência em **arquivo** (CSV/JSON) ou banco de dados para manter os dados entre execuções.
- [ ] Separar **camada de apresentação** (menu/IO) da **camada de domínio** (regras de negócio).
- [ ] Adicionar **testes unitários** com JUnit (especialmente para `executarVenda` e `pagarDivida`).
- [ ] Suportar **juros no empréstimo** e parcelamento de dívida.
- [ ] Internacionalização e formatação monetária com `NumberFormat`/`Locale`.
- [ ] Tratamento de exceções com mensagens centralizadas.
- [ ] Editar/remover pessoas e bens já cadastrados.

---

## Licença

Projeto educacional desenvolvido por **Vitor Cardoso**.
