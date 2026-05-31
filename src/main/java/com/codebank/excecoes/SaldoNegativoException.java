package com.codebank.excecoes;

public class SaldoNegativoException extends Exception {
    public SaldoNegativoException(double saldo) {
        super("Operação negada. Saldo atual: R$" + saldo + ". O saldo deve ser maior que zero para aplicar rendimento.");
    }
}
