# eConsumo Receiver
Projeto para IoT, responsável por receber os dados dos sensores de corrente e tensão e armazenar as informações em um banco de dados MongoDB (NoSQL).

A leitura dos dados dos sensores é feita através de protocolo Mqtt.

**Padrão da Mensagem:** tensão;corrente

**Exemplo de uma mensagem recebida válida:** 110.25;56.74

As informações são armazenadas em um banco de dados MongoDB.

**Tecnologias utilizadas:**
  * Java 1.8
  * Spring Boot
  * Spring Integration
  * MongoDB
  * Mosquitto
