# Gerenciamento dos pedidos

Comando para criação do container com o banco de dados PostgreSQL:

```
docker run -d --name DB-Gerenciamento-Pedidos -e POSTGRES_PASSWORD=gepos -e POSTGRES_USER=admin -e POSTGRES_DB=PG_DB001 -p 5432:5432 postgres
```

### Tecnologias:

- Java 21
- Spring Boot 3.4.0
- Swagger 3
- PostgreSQL 17.2
- Docker
