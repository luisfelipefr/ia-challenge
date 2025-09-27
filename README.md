
# 📘 IA Challenge — Backend

Este repositório contém o **backend** do desafio técnico.  
A aplicação foi desenvolvida em **Spring Boot**, com **PostgreSQL** como banco de dados, **Docker** para containerização e autenticação via **JWT**.

---

## 🚀 Tecnologias utilizadas
- **Java 21** + **Spring Boot 3**  
- **Maven** (build e dependências)  
- **PostgreSQL 16** (banco de dados)  
- **Docker & Docker Compose** (containerização)  
- **JWT** (autenticação e autorização)  
- **Hibernate/JPA** (ORM)

---

## 📂 Estrutura do projeto
```
.
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .dockerignore
├── .gitignore
├── .env.example
└── src/
    └── main/java/br/com/saamauditoria
        ├── controller/   # Controllers (APIs REST)
        ├── model/        # Entidades do banco
        ├── repository/   # Repositórios (JPA)
        └── security/     # Autenticação JWT
```

---

## ⚙️ Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Git](https://git-scm.com/)

> **Opcional**: Java 21 e Maven, caso queira rodar sem Docker.

---

## 🔑 Configuração do ambiente

1. **Clone este repositório**
   ```bash
   git clone https://github.com/luisfelipefr/ia-challenge.git
   cd ia-challenge
   ```

2. **Crie o arquivo `.env` a partir do exemplo**
   ```bash
   cp .env.example .env
   ```

3. **Edite o `.env` e configure os valores**
   - Gere uma chave segura para o `JWT_SECRET`:
     ```powershell
     # PowerShell
     $bytes = New-Object Byte[] 32
     (New-Object System.Security.Cryptography.RNGCryptoServiceProvider).GetBytes($bytes)
     ($bytes | ForEach-Object { $_.ToString("x2") }) -join ""
     ```
   - Ou no Linux/macOS/WSL:
     ```bash
     openssl rand -hex 32
     ```

   - Exemplo final do `.env`:
     ```
     # postgres
     POSTGRES_DB=ia_challenge
     POSTGRES_USER=postgres
     POSTGRES_PASSWORD=postgres
     DB_HOST=db
     DB_PORT=5432
     
     # backend
     BACKEND_PORT=8080
     JWT_SECRET=c0a1e3948d01c8df74e49e9d28b79cda20959a3e2ff47f8cc7c8e9236baf3c72
     ```

---

## 🐳 Rodando com Docker Compose

Suba a aplicação com um único comando:

```bash
docker compose up --build
```

- O backend estará disponível em: **http://localhost:8080**
- O PostgreSQL em: **localhost:5432**

> O Docker vai criar automaticamente um volume para persistência dos dados do banco.

---

## 🧪 Testando a API

### 1. Health check
```bash
curl http://localhost:8080/ping
```
**Resposta esperada:**
```
pong
```

---

### 2. Registrar usuário
**Endpoint:**
```
POST /api/users/register
```

**Body:**
```json
{
  "username": "admin",
  "email": "admin@local",
  "password": "admin"
}
```

**Resposta esperada:**
```
User created successfully
```

---

### 3. Login (gera JWT)
**Endpoint:**
```
POST /api/auth/login
```

**Body:**
```json
{
  "email": "admin@local",
  "password": "admin"
}
```

**Resposta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1..."
}
```

---

### 4. Criar produto
**Endpoint:**
```
POST /api/products
```

**Headers:**
```
Authorization: Bearer SEU_TOKEN
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Mouse",
  "description": "Mouse Gamer RGB",
  "price": 150.0,
  "quantity": 10
}
```

**Resposta esperada:**
```json
{
  "id": 1,
  "name": "Mouse",
  "description": "Mouse Gamer RGB",
  "price": 150.0,
  "quantity": 10
}
```

---

### 5. Listar produtos
**Endpoint:**
```
GET /api/products
```

**Headers:**
```
Authorization: Bearer SEU_TOKEN
```

**Resposta esperada:**
```json
[
  {
    "id": 1,
    "name": "Mouse",
    "description": "Mouse Gamer RGB",
    "price": 150.0,
    "quantity": 10
  }
]
```

---

## 🧰 Endpoints principais

| Método | Endpoint           | Descrição                     | Autenticação |
|--------|--------------------|--------------------------------|--------------|
| GET    | `/ping`            | Health check                   | ❌ Não |
| POST   | `/api/auth/login`  | Login e geração de JWT (email + senha) | ❌ Não |
| POST   | `/api/users/register` | Cria usuário (username, email, senha) | ❌ Não |
| GET    | `/api/products`    | Lista produtos                 | ✅ Sim |
| POST   | `/api/products`    | Cria um novo produto            | ✅ Sim |

---

## 🧹 Limpeza de containers e volumes

Se precisar parar e limpar tudo:

```bash
docker compose down -v
```

> `-v` remove volumes (inclusive dados do banco).

---

## 🤖 Uso de IA neste projeto
Durante o desenvolvimento, a **IA foi utilizada** para:

- Sugerir estrutura inicial do projeto.
- Auxiliar na escrita de Dockerfile e docker-compose.
- Otimizar configurações do Spring Boot.
- Gerar exemplos de testes com Postman e curl.
- Melhorar a legibilidade de commits e documentação.

**Benefício:** agilizou o desenvolvimento, manteve boas práticas e garantiu clareza na entrega final.

---

## 📜 Licença
Este projeto é apenas para **avaliação técnica** e não possui licença para uso comercial.

---
