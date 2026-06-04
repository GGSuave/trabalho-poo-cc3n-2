package com.codebank.excecoes;

/***
 * @throws SaldoInsuficienteException Erro ao tentar sacar um valor que seja
 *                                    maior que a soma do saldo com o limite da
 *                                    conta.
 * 
 * @param saldo  Double - Saldo disponível na conta.
 * @param limite Double - Limite disponível na conta.
 * @param valor  Double - Valor solicitado para saque.
 * @author Taina
 */
public class LimiteExcedidoException extends Exception {
    public LimiteExcedidoException(double saldo, double limite, double valor) {
        super("Limite excedido. Saldo: R$" + saldo + " | Limite: R$" + limite + " | Valor solicitado: R$" + valor);
    }
}
