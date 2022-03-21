# GoldenRaspberryAwards

## Começando

Para executar o projeto, será necessário instalar os seguintes programas:

- [JDK 11: Necessário para executar o projeto Java](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)
- [Plugin do lombok, na plataforma escolhida](https://projectlombok.org/)
- [Postman - Para execução e validação do projeto](https://www.postman.com/downloads/)
- [Git - Para realizar o clone do projeto](https://git-scm.com/downloads)

## Desenvolvimento

Para iniciar, é necessário clonar o projeto do GitHub num diretório de sua preferência:

```shell
cd "diretório de sua preferencia"
git clone https://github.com/ellianm/goldenrasberryawards.git
```

### Construção

Para construir o projeto com o Maven, no diretório raiz executar os comando abaixo:

```shell
.\mvnw clean install
```

### Testes

Para executar os testes do projeto com o Maven, no diretório raiz executar os comando abaixo:

```shell
.\mvnw test
```

### Execução

O projeto roda na port 8080, certifique-se que a mesma esta disponível.

| Method   | URL                                      | Description                                                                                                                 |
| -------- | ---------------------------------------- | --------------------------------------------------------------------------------------------------------------------------- |
| `GET`    | `/api/movie`                             | Retorna os dados dos filmes.                                                                                                |
| `POST`   | `/api/movie`                             | Cria um novo filme.                                                                                                         |
| `GET`    | `/api/movie/28`                          | Retorna o filme de código 28.                                                                                               |
| `PUT`    | `/api/movie/28`                          | Altera o filme de código 28.                                                                                                |
| `GET`    | `/api/producer`                          | Retorna os dados dos produtores.                                                                                            |
| `POST`   | `/api/producer`                          | Cria um novo produtor.                                                                                                      |
| `GET`    | `/api/producer/28`                       | Retorna o produtor de código 28.                                                                                            |
| `PUT`    | `/api/producer/28`                       | Altera o produtor de código 28.                                                                                             |
| `GET`    | `/api/producer/fasterAndSlower`          | Retorna  o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápidos.          |
| `GET`    | `/api/studio`                            | Retorna os dados dos estúdios.                                                                                              |
| `POST`   | `/api/studio`                            | Cria um novo estúdio.                                                                                                       |
| `GET`    | `/api/studio/28`                         | Retorna o estúdio de código 28.                                                                                             |
| `PUT`    | `/api/studio/28`                         | Altera o estúdio de código 28.                                                                                              |

### Postman

Para ter acesso a validação da API, após a execução do projeto, abrir e consumir os dados de consulta da API.