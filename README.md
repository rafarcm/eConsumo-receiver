# eConsumo Receiver
Projeto para IoT, responsável por receber os dados dos sensores de corrente e tensão e armazenar as informações em um banco de dados MongoDB (NoSQL).

A leitura dos dados dos sensores é feita através de protocolo Mqtt.

**Padrão do Payload:** tensão;corrente;data de leitura

**Padrão para a data de leitura:** dd-MM-yyyy HH:mm:ss

**Exemplo Payload:** 110.25;56.74;20/05/2020 12:30:15

É possível enviar mais de uma leitura em um mesmo payload. Basta separar as leituras com "%"

**Exemplo Payload com três leituras:** 110.15;50.74;20/05/2020 12:30:15%110.20;52.00;20/05/2020 12:30:16%110.25;56.74;20/05/2020 12:30:17

As informações são armazenadas em um banco de dados MongoDB.

**Tecnologias utilizadas:**
  * Java 1.8
  * Spring Boot
  * Spring Integration
  * MongoDB
  * Mosquitto
