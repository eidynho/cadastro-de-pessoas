# Aplicação de Cadastro de Pessoas

Essa aplicação utiliza o banco de dados PostgreSQL e a configuração é feita através do docker compose.

Certifique-se de que o Docker e o Docker Compose estão instalados e rodando em sua máquina. Em seguida, execute:
```
docker-compose up -d
```

Configure as variáveis de ambiente:
```
spring.application.name=cadastro-de-pessoas
spring.datasource.url=jdbc:postgresql://localhost:5432/cadastro_pessoas
spring.datasource.username=admin
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=update

cors.allowedOrigins=http://127.0.0.1:3000
```

Compile e execute a aplicação:
```
mvn clean install
mvn spring-boot:run
```

Após iniciar a aplicação, você pode acessá-la através do endereço configurado: http://localhost:8080