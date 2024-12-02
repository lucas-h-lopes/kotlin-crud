# kotlin-crud

Este projeto implementa um sistema CRUD simples em Kotlin para gerenciar usuários e seus telefones em um banco de dados relacional. A aplicação utiliza JDBC para conectar-se ao banco de dados e oferece funcionalidades como criação, leitura, atualização e exclusão de registros.

## Funcionalidades

- **Cadastro de Usuário**: Permite registrar um novo usuário com nome e email, além de associar um ou mais telefones.
- **Listagem de Usuário**: Permite visualizar um usuário específico ou todos os usuários cadastrados.
- **Alteração de Dados**: Permite editar informações do usuário, incluindo nome, email e telefones associados.
- **Exclusão de Usuário**: Permite excluir um usuário, desde que não tenha telefones associados.
- **Cadastro e Exclusão de Telefone**: Permite cadastrar novos números de telefone para usuários existentes, além de excluir telefones.

## Estrutura do Projeto

- **`application/`**: Contém a classe principal (`Main.kt`) que gerencia o fluxo de operações no terminal.
- **`model/`**: Contém as classes de modelo de dados como `User`, `Phone`, além das interfaces de DAO.
- **`model/dao/`**: Contém as interfaces e implementações de DAOs para interação com o banco de dados (`UserDao`, `PhoneDao`).
- **`db/`**: Gerencia a conexão com o banco de dados (classe `Database`).
- **`exception/`**: Contém exceções personalizadas para casos como `EntityNotFoundException`, `UniqueEmailViolationException` e `DatabaseException`.
- **`util/`**: Contém utilitários para a interação com o console (`ConsoleUtils`).

## Como Rodar

### 1. Clonar o Repositório

Clone este repositório para a sua máquina local:

```bash
git clone https://github.com/lucas-h-lopes/kotlin-crud.git
cd kotlin-crud
```

### 2. Configurar o Banco de Dados
Você pode configurar o banco de dados criando um arquivo com nome **.env** em **src/main/resources** e configurando as variáveis de ambiente DBNAME, URL, USER e PASSWORD.

### 3. Executar o Projeto

Abra o arquivo Main.kt e execute o programa para iniciar o CRUD.

## Estrutura do Banco de Dados

![db-image](/images/kotlin-crud-relacionamento.png)

## Visualização do Projeto 👀

Visualização inicial:

![inicio](/images/preview.png)

Inserção de registros:
![insert-user](/images/insert.png)

![insert-phone](/images/insert-phone.png)

Listagem de registros:

![get-one-user](/images/get-by-id.png)

![get-all-user](/images/get-all.png)

Atualização:

![insert-update](/images/update-by-id.png)

![insert-update-phone](/images/update-by-id-phone.png)

Exclusão:

![delete-user](/images/delete-by-id.png)

![delete-user-phone](/images/delete-by-id-phone.png)