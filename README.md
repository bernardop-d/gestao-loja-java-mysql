# Gestão de Loja — Java + MySQL

Sistema de gestão de loja via terminal. Desenvolvido em Java puro com acesso a banco de dados MySQL usando JDBC, sem frameworks — o objetivo foi aplicar os fundamentos de POO, padrão DAO e SQL em um fluxo completo de negócio.

## O problema

Projetos Java iniciantes costumam operar só em memória (ArrayList) e não persistem nada. Queria um projeto que fechasse o ciclo completo: entrada do usuário → lógica de negócio → banco relacional → resposta — sem depender de Spring Boot para esconder o que está acontecendo.

## Decisões técnicas

| Decisão | Motivo |
|---|---|
| JDBC direto (sem ORM) | Entender a camada de persistência sem abstração |
| Padrão DAO | Separar responsabilidade de acesso a dados das regras de negócio |
| Variáveis de ambiente para credenciais | Nunca hardcodar senha — boas práticas desde o início |
| Java 21 + Maven | Versão LTS atual; Maven para gerenciar dependências de forma padrão de mercado |
| Switch expressions (Java 14+) | Sintaxe moderna e mais legível que if/else encadeado |

## Stack

- **Java 21**
- **MySQL 8**
- **JDBC** — `mysql-connector-j 8.3.0`
- **Maven** — gerenciamento de dependências e build

## Funcionalidades

- Cadastrar produtos (nome, descrição, preço, estoque)
- Listar produtos cadastrados
- Registrar venda com controle automático de estoque
- Validação de estoque insuficiente antes de concluir venda

## Estrutura de pastas

```
src/main/java/com/bernardo/loja/
├── Main.java                    # Ponto de entrada
├── Menu.java                    # Interface de terminal (loop de opções)
├── config/
│   └── DatabaseConnection.java  # Conexão JDBC via variáveis de ambiente
├── dao/
│   ├── ProdutoDAO.java          # CRUD de produtos + controle de estoque
│   ├── VendaDAO.java            # Registro de vendas com validação
│   ├── ClienteDAO.java          # CRUD de clientes
│   └── UsuarioDAO.java          # CRUD de usuários
└── model/
    ├── Produto.java
    ├── Venda.java
    ├── ItemVenda.java
    ├── Cliente.java
    └── Usuario.java
```

## Como rodar

### Pré-requisitos

- Java 21+
- MySQL 8+
- Maven 3.6+

### 1. Clone o repositório

```bash
git clone https://github.com/bernardop-d/gestao-loja-java-mysql.git
cd gestao-loja-java-mysql
```

### 2. Configure o banco de dados

Crie o banco e as tabelas no MySQL:

```sql
CREATE DATABASE loja_db;
USE loja_db;

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DOUBLE NOT NULL,
    estoque INT NOT NULL
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    usuario_id INT,
    data_venda DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
```

### 3. Configure as variáveis de ambiente

```bash
cp .env.example .env
```

Edite o `.env` com suas credenciais:

```
DB_URL=jdbc:mysql://localhost:3306/loja_db?useSSL=false&serverTimezone=UTC
DB_USER=root
DB_PASSWORD=sua_senha
```

> As variáveis são lidas via `System.getenv()` em `DatabaseConnection.java` — nenhuma credencial fica no código.

### 4. Compile e execute

```bash
mvn compile
mvn exec:java
```

## Exemplo de uso

```
=== SISTEMA DE GESTÃO DE LOJA ===
1 - Cadastrar produto
2 - Listar produtos
3 - Registrar venda
4 - Sair
Escolha: 1

Nome: Notebook
Descrição: Notebook Dell 16GB RAM
Preço: 3500.00
Estoque inicial: 10
✅ Produto cadastrado!

Escolha: 3
ID do produto: 1
Quantidade: 2
✅ Venda realizada com sucesso!
```

## Autor

**Bernardo P. D.** — [LinkedIn](https://www.linkedin.com/in/bernardop-d/) · [GitHub](https://github.com/bernardop-d)
