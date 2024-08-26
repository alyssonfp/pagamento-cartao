<<<<<<< HEAD
# pagamento-cartao
=======
# Pagamento com Cartão - Microsserviço em Java Quarkus

Este projeto é um microsserviço desenvolvido em Java utilizando o framework Quarkus, que realiza o recebimento, validação e armazenamento de pagamentos realizados com cartões de crédito. A aplicação expõe endpoints RESTful para processar pagamentos e consultar transações armazenadas.

## Clonando o Repositório

Para obter uma cópia do projeto, clone o repositório utilizando o seguinte comando:

```bash
git clone https://github.com/alyssonfp/pagamento-cartao.git
```

## Rodar o Docker Compose

Na raiz do projeto, execute o seguinte comando para iniciar a aplicação com Docker Compose:

```bash
docker-compose up -d
```

## Swagger

A interface do Swagger UI está disponível no endpoint:

```bash
http://localhost:8080/swagger-ui/
```

## Métricas Prometheus

As métricas de desempenho da aplicação podem ser acessadas no endpoint:

```bash
http://localhost:8080/q/metrics
```

## Funcionalidades Implementadas

- **Recebimento de Dados de Pagamento**: O serviço recebe dados de pagamento em formato JSON, valida as informações e as armazena em um banco de dados relacional.
- **Consulta de Pagamentos**: Disponibiliza endpoints para a consulta de pagamentos armazenados no banco de dados.
- **Validação de Dados**: Implementação de validações para o número do cartão, nome do titular, data de validade, CVV e valor da transação.
- **Swagger UI**: A documentação dos endpoints está disponível através do Swagger.

## Regras de Negócio Atendidas

1. **Validação do Número do Cartão**: O número do cartão deve conter 16 dígitos.
2. **Validação do Nome do Titular**: O nome do titular não pode ser nulo ou vazio.
3. **Validação da Data de Validade**: A data de validade do cartão não pode estar no passado.
4. **Validação do CVV**: O CVV deve conter 3 ou 4 dígitos.
5. **Validação do Valor**: O valor do pagamento deve ser positivo.
>>>>>>> 94ab7b3 (first commit)
