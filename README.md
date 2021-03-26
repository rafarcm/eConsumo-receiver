# eConsumo API
API para integração de dados para sistema de medição de consumo de energia doméstico.

Esta API é responsável por receber e disponibilizar as informações do sistema de medição de energia.

- **Tecnologias utilizadas:**
  * Java 1.8
  * Spring Boot
  * Spring Integration
  * MongoDB
  * Mosquitto

# Como enviar os dados dos sensores de tensão e corrente ao sistema
Parte da API, responsável por receber os dados dos sensores de corrente e tensão e armazenar as informações em um banco de dados MongoDB (NoSQL).

O envio dos dados dos sensores é feito através de protocolo Mqtt.

**1. Padrão do Payload:** tensão;corrente;data de leitura

**2. Padrão para a data de leitura:** dd-MM-yyyy HH:mm:ss

**3. Exemplo Payload:** 110.25;56.74;20/05/2020 12:30:15

É possível enviar mais de uma leitura em um mesmo payload. Basta separar as leituras com "%"

**4. Exemplo Payload com três leituras:** 110.15;50.74;20/05/2020 12:30:15%110.20;52.00;20/05/2020 12:30:16%110.25;56.74;20/05/2020 12:30:17

As informações são armazenadas em um banco de dados MongoDB.


