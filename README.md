# CodeBank

## Sobre o Projeto

O **CodeBank** é um sistema bancário desenvolvido em Java como trabalho acadêmico da disciplina de **Programação Orientada a Objetos (POO)** do curso de **Ciência da Computação**.

O objetivo do projeto é aplicar os principais conceitos de orientação a objetos, incluindo:

* Classes e Objetos
* Encapsulamento
* Herança
* Polimorfismo
* Classes Abstratas
* Interfaces
* Tratamento de Exceções
* Agregação e Composição
* Estruturas de Dados utilizando Vetores e Matrizes

O sistema simula operações bancárias básicas, permitindo o gerenciamento de clientes, contas correntes, contas poupança e operações financeiras.

---

## Funcionalidades Implementadas

### Banco

* Cadastro de clientes em agências
* Consulta de clientes por agência
* Relatório geral do banco
* Controle de saldo total custodiado

### Cliente

* Associação de até 5 contas bancárias
* Cadastro de informações básicas

### Conta Corrente

* Crédito de valores
* Débito de valores
* Transferência entre contas
* Utilização de cheque especial
* Cobrança de tarifa mensal
* Controle de débito do cheque especial

### Conta Poupança

* Crédito de valores
* Débito de valores
* Transferência entre contas
* Aplicação automática de rendimento de 0,5%

### Extrato

* Registro das operações realizadas
* Histórico de movimentações
* Consulta de extrato da conta

### Exceções Personalizadas

* ValorInvalidoException
* SaldoInsuficienteException
* LimiteExcedidoException
* SaldoNegativoException
* DebitoChequeInexistenteException

---

## Estrutura do Projeto

O sistema foi desenvolvido utilizando conceitos de Programação Orientada a Objetos através de:

* Classe abstrata para modelagem das contas bancárias
* Interface para definição de comportamentos tributáveis
* Herança entre tipos de contas
* Composição para gerenciamento de extratos
* Tratamento de erros utilizando exceções personalizadas

---

## Tecnologias Utilizadas

* Java
* Programação Orientada a Objetos (POO)
* JavaDoc para documentação do código

---

## Participantes

* Guilherme Gastardi Suave
* Julia Oliveira do Espírito Santo Rangel
* Tainá Ramos de Jesus Meirelles
* João Pedro Marques Moreira

---

## Finalidade Acadêmica

Este projeto foi desenvolvido exclusivamente para fins acadêmicos como atividade avaliativa da disciplina de Programação Orientada a Objetos do curso de Ciência da Computação.

O objetivo principal foi consolidar os conhecimentos adquiridos em sala de aula por meio da implementação prática de um sistema orientado a objetos utilizando a linguagem Java.
