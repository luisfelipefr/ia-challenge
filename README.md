# IA Challenge Backend

API REST do desafio tecnico. O backend disponibiliza cadastro e autenticacao de usuarios e gestao de produtos,
usando Spring Boot 3, PostgreSQL e JWT. O repositorio inclui a configuracao Docker para levantar banco e aplicacao
rapidamente.

## Visao Geral

- Cadastro e login de usuarios com senha criptografada via BCrypt.
- Geracao de token JWT com validade de 8 horas e autorizacao por papel (ROLE_*).
- CRUD simplificado de produtos (listar, criar, remover) restrito a usuarios autenticados.
- CORS configuravel por variavel de ambiente para integracao com front-ends.
- Dockerfile multi-stage e docker compose para ambiente padrao com PostgreSQL 16.

## Tecnologias

- Java 21 / Spring Boot 3
- Maven 3.9
- PostgreSQL 16
- Spring Security + JWT
- Docker e Docker Compose

## Estrutura do repositorio

```
.
|-- docker-compose.yml
|-- .env
|-- ia-challenge-backend/
    |-- pom.xml
    |-- Dockerfile
    |-- src/main/java/br/com/saamauditoria/
        |-- controller/    # Endpoints REST
        |-- dto/           # Modelos para login
        |-- model/         # Entidades JPA
        |-- repository/    # Acesso a dados
        |-- security/      # Configuracao de JWT, filtros e CORS
    |-- src/main/resources/application.properties
```

## Pre-requisitos

- Docker e Docker Compose
- Git
- Opcional: Java 21 e Maven 3.9+ (para rodar sem Docker)
- Opcional: PostgreSQL 16 local (caso nao use os containers)

## Variaveis de ambiente

O arquivo `.env` na raiz eh lido automaticamente pelo Docker Compose. Ajuste conforme necessario.

| Variavel          | Descricao                                           | Padrao      |
|-------------------|-----------------------------------------------------|-------------|
| POSTGRES_DB       | Nome do banco PostgreSQL                            | appdb       |
| POSTGRES_USER     | Usuario do banco                                    | app         |
| POSTGRES_PASSWORD | Senha do banco                                      | app         |
| DB_HOST           | Host acessado pelo backend                          | db          |
| DB_PORT           | Porta do PostgreSQL                                 | 5432        |
| BACKEND_PORT      | Porta HTTP exposta pela API                         | 8080        |
| JWT_SECRET        | Segredo usado para assinar o JWT (>= 32 caracteres) | obrigatorio |
| FRONTEND_PORT     | Porta esperada pelo front para CORS (opcional)      | 4173        |

A lista completa de variaveis usadas pelo Spring esta em `src/main/resources/application.properties`. Caso precise
liberar outro dominio para CORS, ajuste `CORS_ALLOWED_ORIGINS` (ou defina `app.cors.allowed-origins` como variavel de
ambiente).

## Configuracao rapida

1. Clone o repositorio.
   ```
   git clone <url>
   cd ia-challenge
   ```
2. Revise o arquivo `.env` e substitua senhas ou segredos conforme politicas internas. Gere um novo valor seguro para
   `JWT_SECRET`:
   ```
   # PowerShell
   $bytes = New-Object Byte[] 32
   (New-Object System.Security.Cryptography.RNGCryptoServiceProvider).GetBytes($bytes)
   ($bytes | ForEach-Object { $_.ToString("x2") }) -join ""
   ```
3. (Opcional) Atualize `CORS_ALLOWED_ORIGINS` com as URLs do seu front-end.

## Executar com Docker Compose

```
docker compose up --build
```

- API: http://localhost:8080
- PostgreSQL: localhost:5432

Para rodar em background: `docker compose up --build -d`. Para interromper e remover volumes: `docker compose down -v`.

## Referencia da API

| Metodo | Caminho             | Descricao                    | Autenticacao |
|--------|---------------------|------------------------------|--------------|
| GET    | /ping               | Verifica disponibilidade     | Nao          |
| POST   | /api/users/register | Cria um novo usuario         | Nao          |
| POST   | /api/auth/login     | Retorna um JWT valido por 8h | Nao          |
| GET    | /api/products       | Lista produtos cadastrados   | Bearer token |
| POST   | /api/products       | Cria produto                 | Bearer token |
| DELETE | /api/products/{id}  | Remove produto pelo id       | Bearer token |

### Registrar usuario

```
POST /api/users/register
Content-Type: application/json

{
  "username": "admin",
  "email": "admin@local",
  "password": "suaSenhaSegura",
  "roles": "ROLE_ADMIN"   // opcional, padrao ROLE_USER
}
```

endpoint não adicionado ao projeto

Respostas:

- 200 OK: "User created successfully"
- 400 Bad Request: email ja cadastrado ou validacao falhou.

### Login

```
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@local",
  "password": "suaSenhaSegura"
}
```

Resposta (200 OK):

```
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Use o token no cabecalho `Authorization: Bearer <token>`.

### Listar produtos

```
GET /api/products
Authorization: Bearer <token>
```

Resposta:

```
[
  {
    "id": 1,
    "name": "Mouse",
    "description": "Mouse gamer RGB",
    "price": 150.0,
    "quantity": 10
  }
]
```

### Criar produto

```
POST /api/products
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Mouse",
  "description": "Mouse gamer RGB",
  "price": 150.0,
  "quantity": 10
}
```

Regras de validacao:

- `name` nao pode ser vazio.
- `description` deve ser enviado (o servico faz `trim`).
- `price` e `quantity` devem ser >= 0.

Resposta:

```
{
  "id": 1,
  "name": "Mouse",
  "description": "Mouse gamer RGB",
  "price": 150.0,
  "quantity": 10
}
```

### Remover produto

```
DELETE /api/products/1
Authorization: Bearer <token>
```

Resposta: 200 OK sem corpo. Se o id nao existir, o delete nao gera erro mas nenhum item e removido.

## 🤖 Uso de IA no projeto

Durante todo o desenvolvimento, utilizei o Codex como ferramenta de apoio estratégico, atuando em diferentes etapas do
projeto para acelerar o desenvolvimento e garantir qualidade no resultado final.
<br>
O Codex foi essencial para:

- **Definição da arquitetura inicial**: sugeriu a estrutura base do projeto em Spring Boot.
- **Configuração e infraestrutura**: ajudou na criação do Dockerfile e docker-compose, garantindo a correta configuração
  do PostgreSQL e variáveis de ambiente seguras
- **Resolução de erros e problemas de configuração**: todos os erros mais comuns durante a integração entre Spring Boot,
  PostgreSQL e Docker foram solucionados com o auxílio do Codex, o que reduziu significativamente o tempo de depuração.
- **Documentação e padronização**: auxiliou na escrita do README e também na padronização de commits

O uso do Codex acelerou o desenvolvimento, eliminou gargalos de configuração, garantiu uma estrutura sólida e bem
organizada.
Lembrando que todas as sugestões foram revisadas, testadas e adaptadas para o projeto.