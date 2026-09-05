# Backend - Doces com Amor

API REST do sistema de gerenciamento da doceria **Doces com Amor**, desenvolvida com Java e Spring Boot.

## Tecnologias

- Java 17
- Spring Boot
- Spring Security e JWT
- Spring Data JPA
- MySQL
- Swagger/OpenAPI
- Google Calendar API

## Requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- Java 17 ou superior
- MySQL 8

O projeto inclui o Maven Wrapper, portanto não é necessário instalar o Maven globalmente.

## Configuração

Crie o banco e o usuário local utilizando os scripts do repositório `Banco-de-Dados`. A configuração padrão espera:

```text
Banco: doces_com_amor
Usuário: docescomamor
Porta: 3306
```

Para ambientes externos, configure as variáveis:

```text
JWT_SECRET
JWT_VALIDITY
```

A integração com Google Calendar requer o arquivo `google-calendar-key.json` em `src/main/resources`. Esse arquivo contém credenciais e não deve ser versionado.

## Executando o projeto

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A API será disponibilizada em:

```text
http://localhost:8080
```

Documentação Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

## Funcionalidades principais

- Autenticação administrativa com JWT em cookie HttpOnly
- Gestão de clientes e endereços
- Cadastro, edição, status, cancelamento e reagendamento de pedidos
- Integração de pedidos com o Google Calendar
- Relatórios para a Dashboard

## Testes

```bash
.\mvnw.cmd test
```

Os testes utilizam banco H2 em memória e não dependem do MySQL local.

## Estrutura básica

```text
src/main/java/.../
├── Config/
├── controller/
├── dto/
├── mapper/
├── model/
├── repository/
└── service/
```
