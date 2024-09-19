# Teste para Desenvolvedor

## Requisitos

- Quarkus ✅
- Java ou Kotlin ✅
- Rest ✅
- Banco de dados SQL ✅

## Diferencial

- Testes Unitários ❌
- Testes de Integração ❌
- Criação de container com a API desenvolvida ✅

## Funcionalidades

### Estados

### # Endpoints

#### /api/v1/states (GET) - (pesquisa com paginação)

Permite obter todos os Estados com paginação e filtros, 
**filtros**:

- **like_filters**=attribute1=value,attribute2=value
- **pageNumber**=value
- **pageSize**=value
- **pageOrder**=**ASC** or **DESC**

#### /api/v1/states (POST) - (Inserir)

**Payload**

```json
{
    "abbreviation": "CA",
    "name": "California"
}
```

**Rules:** 
- Não é permitido a duplicagem da abreviação.
- A abreviação deve ter entre 2 a 10 caracteres no máximo.

#### /api/v1/states/{id} (PUT) - (Atualizar)

**Payload**

```json
{
    "abbreviation": "CA",
    "name": "California"
}
```

**Rules:** 
- Não é permitido a duplicagem da abreviação.
- A abreviação deve ter entre 2 a 10 caracteres no máximo.


#### /api/v1/states/{id} (DELETE) - (Deletar)

Permite deletar um Estado

### Cidades

### # Endpoints

#### /api/v1/cities (GET) - (pesquisa com paginação)

Permite obter todas as Cidades com paginação e filtros, 
**filtros**:

- **like_filters**=attribute1=value,attribute2=value
- **pageNumber**=value
- **pageSize**=value
- **pageOrder**=**ASC** or **DESC**

#### /api/v1/cities (POST) - (inserir)

Permite inserir uma nova cidade.

**Payload**

```json
    {
        "name": "Castelo",
        "population": 4000000,
        "stateAbbreviation": "ES"
    }
```

**Rules:** 
- Só pode existir uma cidade com determinado nome por Estado.
- A abreviação do Estado deve ser uma abreviação válida.

#### /api/v1/cities/{id} (PUT) - (Atualizar)

**Payload:**

```json
    {
        "name": "Castelo",
        "population": 4000000,
        "stateAbbreviation": "ES"
    }
```

**Rules:** 
- Só pode existir uma cidade com determinado nome por Estado.
- A abreviação do Estado deve ser uma abreviação válida.

#### /api/v1/cities/{id} (DELETE) - (Deletar)

Permite deletar uma Cidade.

#### /api/v1/cities/state/{id} (GET) - (Obter)

Permite obter as cidades relacionadas a um Estado.

#### /api/v1/cities/search-by-name/{name} (GET) - (Obter)

Permite pesquisar cidades pelo nome.

**Rules:** 
- É necessário que o nome tenha no mínimo 3 caracteres, caso contrário será retornado uma listagem vazia.

#### /api/v1/cities/count (GET) - (Obter)

Permite obter a quantidade de cidades cadastradas.

## Como executar o projeto?

Se o Docker estiver instalado em sua máquina, você pode iniciar os serviços do projeto com o Docker Compose usando o seguinte comando na raiz do projeto:

```bash
    docker compose up -d
```

Caso não tenha o Docker disponível, siga estas etapas alternativas:

- Execute uma instância do PostgreSQL na porta 5432 em seu ambiente local. Certifique-se de configurar o banco de dados conforme necessário para a sua aplicação.


Execute a aplicação em modo desenvolvimento:

- Utilize o comando Maven para rodar a aplicação Quarkus em modo desenvolvimento:

```bash
    mvn quarkus:dev
```

A aplicação irá rodar no endereço: `http://localhost:8080`

## Tecnologias

- Java
- Quarkus
- Hibernate
- Spring Data JPA