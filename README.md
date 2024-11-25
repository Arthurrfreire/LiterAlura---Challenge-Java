# LiterAlura 📚

O **LiterAlura** é uma API desenvolvida em **Java**, utilizando **Spring Boot** e **PostgreSQL**, para gerenciamento de um sistema de literatura. O projeto permite a interação com a API Gutendex, uma base de dados gratuita com informações sobre mais de 70 mil livros, além de armazenar e manipular dados localmente.

---

## Funcionalidades 🚀

1. **Buscar livro pelo título**:  
   Realiza a consulta do livro diretamente na API Gutendex e salva as informações no banco de dados.
   
2. **Listar livros registrados**:  
   Exibe todos os livros cadastrados no banco de dados.

3. **Listar autores registrados**:  
   Mostra todos os autores cadastrados no sistema.

4. **Listar autores vivos em um determinado ano**:  
   Retorna autores que estavam vivos em um ano específico.

5. **Listar livros em um determinado idioma**:  
   Filtra e exibe os livros cadastrados de acordo com o idioma.

6. **Persistência de dados**:  
   Utiliza o **Spring Data JPA** para gerenciar a persistência de autores e livros no banco de dados relacional **PostgreSQL**.

---

## Tecnologias Utilizadas 🛠️

- **Java 17**
- **Spring Boot 3.1.4**
  - Spring Data JPA
  - Spring Web
  - Spring Cache
- **PostgreSQL**
- **Gutendex API**
- **HikariCP** (gerenciador de conexão)
- **Maven** (gerenciador de dependências)
- **Dotenv** (manipulação de variáveis de ambiente)

---

## Pré-requisitos 📋

Antes de começar, certifique-se de ter instalado:

- **Java 17**
- **Maven**
- **PostgreSQL**

Além disso, configure as seguintes variáveis de ambiente:

- `DB_URL` - URL do banco de dados (ex.: `jdbc:postgresql://localhost:5432/literalura`)
- `DB_USERNAME` - Nome de usuário do banco de dados
- `DB_PASSWORD` - Senha do banco de dados

---

## Como Executar o Projeto ▶️

1. **Clone o repositório**:
   
       git clone https://github.com/seu-usuario/literalura.git
       cd literalura

2. **Configure o arquivo .env (opcional): Crie um arquivo .env na raiz do projeto com as informações do banco de dados**:

        DB_URL=jdbc:postgresql://localhost:5432/literalura
        DB_USERNAME=seu_usuario
        DB_PASSWORD=sua_senha

3. **Execute o projeto: Utilize o Maven para rodar a aplicação**:

       mvn spring-boot:run
   
5. Interaja com o sistema: A aplicação irá exibir um menu no terminal para realizar as seguintes operações:

       Escolha o número de sua opção:
        1- Buscar livro pelo título
        2- Listar livros registrados
        3- Listar autores registrados
        4- Listar autores vivos em um determinado ano
        5- Listar livros em um determinado idioma
        0- Sair
## Estrutura do Projeto 📂

`src/main/java/com/literalura/literatura`
- Controllers:
  - `AutorController.java`
  - `LivroController.java`
- Services:
  - `GutendexService.java`
  - `AutorService.java`
  - `LivroService.java`
- Models:
  - `Autor.java`
  - `Livro.java`
- Repositories:
  - `AutorRepository.java`
  - `LivroRepository.java`
- Application:
  - `LiterAluraApplication.java`
   
## Testes ✅

Para executar os testes:

      mvn test
      
Os testes estão localizados no pacote:

    src/test/java/com/literalura/literatura

## Contribuição 🤝

Sinta-se à vontade para contribuir com melhorias para o projeto:

1. Faça um fork do repositório.
2. Crie uma nova branch para sua feature `(git checkout -b feature/nome-da-feature)`.
3. Commit suas alterações `(git commit -m 'Adiciona nova funcionalidade')`.
4. Envie para o repositório remoto `(git push origin feature/nome-da-feature)`.
5. Crie um pull request.

## Licença 📄

Este projeto está sob a licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.
