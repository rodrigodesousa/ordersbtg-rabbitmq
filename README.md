## Como interagir com o banco de dados?
- Foi utilizado o [MongoDB Compass](https://www.mongodb.com/products/tools/compass)

## Como interagir com a API?
- Foi utilizado o [Bruno](https://github.com/usebruno/bruno)

## :rocket: Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Data MongoDB
* RabbitMQ
* Docker

Exemplo da mensagem que deve ser consumida:

```
   {
       "codigoPedido": 1001,
       "codigoCliente":1,
       "itens": [
           {
               "produto": "lápis",
               "quantidade": 100,
               "preco": 1.10
           },
           {
               "produto": "caderno",
               "quantidade": 10,
               "preco": 1.00
           }
       ]
   }
```
