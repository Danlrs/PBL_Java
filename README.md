# VendaIngressos

## Descrição

**VendaIngressos** é um sistema desenvolvido para a venda de ingressos para eventos. Ele permite o cadastro de usuários e eventos, compra de ingressos, e o gerenciamento das avaliações dos eventos. A interface gráfica foi implementada utilizando o **JavaFX**, proporcionando uma experiência de usuário interativa e visualmente agradável. Entre as funcionalidades principais, destacam-se:

- Cadastro de usuários e eventos.
- Compra e cancelamento de ingressos.
- Listagem de eventos disponíveis e ingressos comprados.
- Avaliação de eventos, limitando cada usuário a uma única avaliação por evento.
- Suporte a pagamentos via **cartão de crédito** e **boleto**.

## Tecnologias Utilizadas

- **Java 23.0.1**: A versão do Java utilizada no desenvolvimento é a 23.0.1.
- **JavaFX 23.0.1**: Para a criação da interface gráfica do sistema, foi utilizada a versão 23.0.1 do JavaFX.
- **IntelliJ IDEA**: O projeto foi desenvolvido utilizando o ambiente de desenvolvimento integrado **IntelliJ IDEA**.

## Estrutura do Projeto

O projeto está dividido em diversas classes, entre elas:

- **Usuario**: Representa o usuário do sistema, contendo informações como login, senha, uma lista de cartões de pagamento e métodos para o gerenciamento dos mesmos.
- **Evento**: Define um evento com atributos como nome, descrição, data e uma lista de avaliações.
- **Ingresso**: Representa o ingresso para um evento específico, incluindo um identificador único.
- **Cartao**: Armazena informações sobre o cartão de pagamento do usuário, incluindo o número do cartão, CVV, data de validade, e o nome do titular. Essas informações são utilizadas para realizar pagamentos no sistema.
- **Avaliacao**: Permite que o usuário faça uma avaliação de um evento, contendo um comentário e uma nota de avaliação (rating). Cada usuário pode fazer apenas uma avaliação por evento.
- **Compra**: Registra as informações de compra, como dados do evento, usuário, ingresso, método de pagamento (cartão ou boleto) e preço.

## Desenvolvimento

Este projeto foi desenvolvido utilizando **Java 23.0.1** e **JavaFX 23.0.1** para a construção de uma interface gráfica moderna e funcional. O ambiente de desenvolvimento utilizado foi o **IntelliJ IDEA**, que facilitou a organização do código e o gerenciamento das dependências.
