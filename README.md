# Sistema de Gestão de Loja (Java + MySQL)

Projeto de estudo em Java com integração ao MySQL, simulando um cenário real de gestão de produtos, controle de estoque e registro de vendas. O foco foi praticar organização em camadas, acesso a dados com JDBC e regras básicas de negócio.

## Funcionalidades
- Cadastro e listagem de produtos  
- Controle de estoque  
- Registro de vendas com validação de quantidade disponível  
- Persistência em banco de dados MySQL  

## Tecnologias
- Java 21  
- Maven  
- MySQL  
- JDBC  

```plaintext
src/main/java/com/bernardo/loja
├── config        # Conexão com banco de dados
├── dao           # Acesso a dados (ProdutoDAO, VendaDAO)
├── model         # Modelos de domínio
├── Menu.java     # Interface de interação via terminal
└── Main.java     # Ponto de entrada da aplicação



pgsql
Copiar código

## Banco de Dados
```sql
CREATE DATABASE loja_db;
USE loja_db;

CREATE TABLE produtos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  descricao TEXT,
  preco DECIMAL(10,2),
  estoque INT
);

CREATE TABLE vendas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  produto_id INT,
  quantidade INT,
  data_venda TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
Configuração de Conexão
As credenciais do banco não ficam no código. O projeto utiliza variáveis de ambiente para conexão com o MySQL.

powershell
Copiar código
setx DB_URL "jdbc:mysql://localhost:3306/loja_db?useSSL=false&serverTimezone=UTC"
setx DB_USER "root"
setx DB_PASSWORD "sua_senha"
Reabra o terminal antes de executar o projeto.

Execução
bash
Copiar código
mvn clean package
mvn exec:java -Dexec.mainClass="com.bernardo.loja.Main"
