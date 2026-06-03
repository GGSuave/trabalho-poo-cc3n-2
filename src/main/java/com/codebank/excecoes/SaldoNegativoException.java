package com.codebank.excecoes;

/**
 * Valida se é possivel aplicar o rendimento em uma conta Poupança.
 * 
 * @param saldo Double - saldo da conta para a ser validado.
 */
public class SaldoNegativoException extends Exception {
    public SaldoNegativoException(double saldo) {
        super("Operação negada. Saldo atual: R$" + saldo + ". O saldo deve ser maior que zero para aplicar rendimento.");
    }
}
