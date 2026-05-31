package com.codebank.excecoes;

public class ValorInvalidoException extends Exception{
    public ValorInvalidoException(double valor) {
        super("Valor inválido: " + valor + ". O valor deve ser maior que zero.");
    }
}
