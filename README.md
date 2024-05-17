# Chat Application

## Descrição

Este projeto é uma aplicação de chat simples que inclui um servidor de chat e um cliente de chat. O servidor pode aceitar conexões de vários clientes simultaneamente, e todas as mensagens enviadas por um cliente são retransmitidas para todos os outros clientes conectados.

## Funcionalidades

- **Servidor de Chat**:
  - Inicia um servidor que pode aceitar conexões de múltiplos clientes.
  - Exibe mensagens recebidas de clientes e permite enviar mensagens para todos os clientes conectados.

- **Cliente de Chat**:
  - Conecta-se ao servidor e permite enviar e receber mensagens.
  - Exibe mensagens recebidas e enviadas na interface gráfica.

## Requisitos

- Java 8 ou superior
- IDE Java (como IntelliJ IDEA, Eclipse, NetBeans) ou terminal com ambiente configurado para compilar e executar aplicações Java.

## Como Executar

### Executar o Servidor

1. Clone o repositório:
    ```sh
    git clone https://github.com/seu_usuario/chat-application.git
    cd chat-application
    ```

2. Compile e execute o servidor:
    ```sh
    javac ChatServer.java
    java ChatServer
    ```

### Executar o Cliente

1. Em um terminal separado, navegue até o diretório do projeto e compile o cliente:
    ```sh
    javac ChatClient.java
    ```

2. Execute o cliente:
    ```sh
    java ChatClient
    ```

### Usando a Interface Gráfica

- **Servidor**:
  - Inicie o servidor. Ele aguardará conexões de clientes.
  - Use a área de entrada de texto e o botão "Send" para enviar mensagens a todos os clientes conectados.

- **Cliente**:
  - Inicie o cliente e ele tentará conectar-se ao servidor no endereço e porta especificados.
  - Use a área de entrada de texto e o botão "Send" para enviar mensagens ao servidor e a todos os outros clientes conectados.

## Exemplo de Uso

1. Inicie o servidor:
    - O servidor exibirá uma mensagem informando que está aguardando conexões.

2. Inicie um ou mais clientes:
    - Cada cliente exibirá uma mensagem informando que está conectado ao servidor.

3. Envie mensagens:
    - Digite mensagens no campo de entrada de texto e clique no botão "Send" para enviar.
    - As mensagens serão exibidas na área de texto do servidor e de todos os clientes conectados.

## Tratamento de Erros

- Se o cliente não conseguir se conectar ao servidor, uma mensagem de erro será exibida e a aplicação será fechada.
- O servidor exibe mensagens de erro no console se ocorrerem problemas durante a execução.


