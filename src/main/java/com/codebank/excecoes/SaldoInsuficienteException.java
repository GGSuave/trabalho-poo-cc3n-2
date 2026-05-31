package com.codebank.excecoes;

public class SaldoInsuficienteException extends Exception {
    /***
     * @throws SaldoInsuficienteException Erro ao tentar sacar um valor maior que o
     *                                    disponivel.
     * 
     * @param saldo disponível na conta.
     * @param valor solicitado para saque.
     * @author Taina
     */
    public SaldoInsuficienteException(double saldo, double valor) {
        super("Saldo insuficiente. Saldo disponível: R$" + saldo + " | Valor solicitado: R$" + valor);
    }
}
