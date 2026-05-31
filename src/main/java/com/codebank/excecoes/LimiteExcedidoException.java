package com.codebank.excecoes;

public class LimiteExcedidoException extends Exception {
    public LimiteExcedidoException(double saldo, double limite, double valor) {
        super("Limite excedido. Saldo: R$" + saldo + " | Limite: R$" + limite + " | Valor solicitado: R$" + valor);
    }
}
