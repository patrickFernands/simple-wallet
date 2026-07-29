# Simple Wallet API

API REST de carteira digital desenvolvida com Spring Boot, com autenticação via JWT e controle de acesso baseado em papéis (RBAC). Permite cadastro de usuários, depósitos, saques, transferências entre contas e bloqueio administrativo de contas.

## Funcionalidades

- **Autenticação e cadastro** — registro de usuário com senha criptografada (BCrypt) e login via JWT.
- **Carteira digital** — cada usuário possui uma wallet própria, criada automaticamente no cadastro.
- **Depósito e saque** — movimentação de saldo, bloqueada para contas suspensas (`isLocked`).
- **Transferência entre usuários** — valida se pagador e recebedor estão com a conta ativa, e impede que contas do tipo `SELLER` (pessoa jurídica) enviem dinheiro.
- **Administração de contas** — endpoints restritos a `ADMIN` para bloquear/desbloquear contas de usuários.
- **Tratamento global de exceções** — respostas de erro padronizadas via `@RestControllerAdvice`.

## Stack técnica

- **Java 25** / **Spring Boot** (`spring-boot-starter-parent`)
- **Spring Security** + **JWT** (`auth0/java-jwt`) para autenticação stateless
- **Spring Data JPA** / **Hibernate**
- **H2 Database** (ambiente de desenvolvimento/testes)
- **Bean Validation** (`spring-boot-starter-validation`)
- **Maven**

## Arquitetura

O projeto segue uma separação em camadas:

```
resources/     → controllers REST (entrada HTTP)
services/      → regras de negócio
repositories/  → acesso a dados (Spring Data JPA)
entities/      → modelos JPA (User, Wallet, Transaction)
dtos/          → objetos de transferência de dados (entrada/saída da API)
infra/security/→ configuração de segurança, filtro JWT e geração/validação de token
exceptions/    → exceções de domínio e handler global
```

A autenticação é **stateless**: cada requisição autenticada carrega um token JWT no header `Authorization: Bearer <token>`, validado por um filtro (`SecurityFilter`) antes de chegar aos controllers.

## Endpoints principais

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| POST | `/auth/register` | Cadastra um novo usuário e cria sua wallet | Público |
| POST | `/auth/login` | Autentica e retorna um token JWT | Público |
| PUT | `/wallets/{id}/deposit` | Deposita valor na wallet | Autenticado |
| PUT | `/wallets/{id}/withdraw` | Saca valor da wallet | Autenticado |
| GET | `/wallets/{id}/balance` | Consulta saldo da wallet | Autenticado |
| POST | `/transactions/{id}/transfer` | Transfere valor entre usuários | Autenticado |
| PUT | `/admins/users/{id}/lock` | Bloqueia a conta de um usuário | `ADMIN` |
| PUT | `/admins/users/{id}/unlock` | Desbloqueia a conta de um usuário | `ADMIN` |

## Regras de negócio

- Contas bloqueadas (`isLocked`) não podem transferir, depositar nem receber valores.
- Usuários do tipo `SELLER` não podem atuar como pagadores em transferências.
- Apenas usuários com papel `ADMIN` podem bloquear ou desbloquear contas.

## Como rodar

```bash
git clone https://github.com/patrickFernands/simple-wallet.git
cd simple-wallet
./mvnw spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`, usando um banco H2 em memória para desenvolvimento.

## Melhorias futuras

- Migrar de H2 para um banco relacional persistente (MySQL/PostgreSQL) em produção
- Testes automatizados de integração para os fluxos de transferência
- Paginação nos endpoints de listagem
