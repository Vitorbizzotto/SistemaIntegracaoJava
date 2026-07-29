# Sistema Família — Vitor Cardoso

> Projeto desenvolvido por **Vitor Cardoso**. Contém dois projetos Java que implementam o mesmo domínio — gestão patrimonial de uma família — em duas abordagens progressivas: primeiro como aplicação console, depois como API REST.

> **Artigo de apresentação:** [ARTIGO.md](./ARTIGO.md) — visão geral dos projetos, como funcionam e como evoluem do console à API.

---

## Visão Geral

O objetivo é mostrar, na prática, como um mesmo conjunto de regras de negócio pode ser estruturado de formas diferentes conforme a arquitetura evolui. O projeto parte de um programa simples de linha de comando e evolui até uma API REST com Spring Boot, demonstrando passo a passo onde cada peça do código antigo foi parar.

```
SistemaIntegracaoJava/
├── ARTIGO.md               # Apresentação dos projetos e como funcionam
├── sistema-familia/        # Aplicação console (Java puro + Maven)
└── sistema-familia-api/    # API REST (Spring Boot 3 + Maven)
```

---

## Os Dois Projetos

### 1. [sistema-familia](./sistema-familia/README.md) — Aplicação Console

Ponto de partida. Um programa Java que roda no terminal e oferece um menu interativo para:

- Cadastrar pessoas, animais, casas e carros.
- Vincular bens a proprietários e transferi-los entre membros da família.
- Controlar carteira, dívidas e calcular patrimônio líquido.

**Foco didático:** Programação Orientada a Objetos (encapsulamento, composição, regras de negócio em métodos), sem frameworks externos.

> Documentação completa: [sistema-familia/README.md](./sistema-familia/README.md)

---

### 2. [sistema-familia-api](./sistema-familia-api/README.md) — API REST

Evolução do projeto console. As mesmas regras de negócio, agora servidas via HTTP com Spring Boot 3. Cada opção do menu virou um endpoint REST, e a interação pelo terminal foi substituída por payloads JSON.

**Foco didático:** Arquitetura em camadas (Controller → Service → Repository), DTOs, validação com Jakarta Validation, tratamento de erros padronizado e documentação via Swagger UI.

> Documentação completa: [sistema-familia-api/README.md](./sistema-familia-api/README.md)

---

## Como os Projetos se Relacionam

| Conceito | Console (`sistema-familia`) | API (`sistema-familia-api`) |
|---|---|---|
| Ponto de entrada | `Main.java` com `while` + `if/else` | Controllers (`@RestController`) |
| Armazenamento | `ArrayList` locais no `main()` | Repositories (singleton em memória) |
| Lógica de negócio | `GestorPatrimonio` (métodos estáticos) | Services (`@Service`) |
| Modelos | `Pessoa`, `Animal`, `Casa`, `Carro` | Mesmas classes + `id` + atributos `private` |
| Validação | `if (nome.isBlank())` manual | Anotações Jakarta (`@NotBlank`, `@Positive`) |
| Resposta ao usuário | `System.out.println(...)` | JSON (DTOs `*Response`) |
| Confirmação de ação | `confirmarAcao("S/N")` no terminal | Próprio envio da requisição HTTP |
| Erros | Mensagem impressa no console | `BusinessException` / `NotFoundException` → JSON padronizado |

---

## Ordem de Estudo Recomendada

1. **Leia o artigo** [ARTIGO.md](./ARTIGO.md) para uma visão geral dos projetos, do domínio e da evolução console → API.
2. **Leia e execute** o projeto console (`sistema-familia`) para entender as regras de negócio sem a complexidade de um framework.
3. **Compare** o `GestorPatrimonio.java` console com o `GestorPatrimonioService.java` da API — a lógica é a mesma, só o canal mudou.
4. **Explore** a API via Swagger UI (`http://localhost:8080/api/swagger-ui.html`) reproduzindo os mesmos fluxos que você testou no console.
5. **Consulte a tabela** "Onde foi cada pedaço do código antigo?" no README da API para rastrear cada linha do console até seu equivalente REST.

---

## Pré-requisitos (ambos os projetos)

- JDK **17+** configurado no `PATH`
- Maven **3.6+** instalado

---

## Licença

Projeto desenvolvido por **Vitor Cardoso**.
