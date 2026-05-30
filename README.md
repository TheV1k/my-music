# 🎵 My Music

Aplicação backend desenvolvida com Java e Spring Boot para consulta e gerenciamento de informações musicais utilizando a API do iTunes.

O projeto foi criado com foco em aprendizado prático de desenvolvimento backend, explorando integração com APIs externas, persistência de dados e construção de uma API REST seguindo boas práticas de arquitetura em camadas.

---

## 🚀 Funcionalidades

### Integração com API Externa

* Busca de artistas através da API do iTunes
* Consulta de álbuns relacionados ao artista
* Consulta de músicas pertencentes aos álbuns encontrados

### Persistência de Dados

* Armazenamento de artistas, álbuns e músicas em banco PostgreSQL
* Relacionamentos entre entidades utilizando JPA/Hibernate

### Consultas Avançadas

* Derived Queries
* Consultas JPQL
* Consultas SQL Nativas
* Paginação com Pageable

### Tratamento de Erros

* Exception Handler Global
* Respostas padronizadas para erros da API

---

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas:

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
PostgreSQL
```

Estrutura principal:

```text
src/main/java
├── controller
├── service
├── repository
├── model
├── dto
├── exception
└── config
```

---

## 🛠️ Tecnologias Utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* Jackson
* REST API
* iTunes Search API

---

## 📡 Exemplos de Endpoints

### Buscar artistas

```http
GET /artistas
```

### Buscar artista por ID

```http
GET /artistas/{id}
```

### Listar álbuns

```http
GET /albuns
```

### Listar músicas

```http
GET /musicas
```

---

## ⚙️ Configuração do Ambiente

### Clonar o repositório

```bash
git clone https://github.com/TheV1k/my-music.git
```

### Entrar na pasta do projeto

```bash
cd my-music
```

### Configurar banco PostgreSQL

No arquivo `application.properties` configure:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mymusic
spring.datasource.username=postgres
spring.datasource.password=senha
```

### Executar aplicação

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

---

## 📚 Aprendizados

Durante o desenvolvimento deste projeto foram praticados conceitos como:

* Consumo de APIs REST
* Serialização e desserialização JSON
* Spring Data JPA
* Relacionamentos entre entidades
* DTOs
* Paginação
* Tratamento global de exceções
* Arquitetura em camadas
* PostgreSQL
* Consultas JPQL e SQL Nativas

---

## 🎯 Próximos Passos

* Implementar cache de consultas
* Documentação com Swagger/OpenAPI
* Testes unitários com JUnit e Mockito
* Autenticação com Spring Security e JWT
* API REST completa para consumo externo
* Interface Web para consulta dos dados

---

## 👨‍💻 Autor

Desenvolvido por Victor Moreira como projeto de estudos em Java e Spring Boot.
