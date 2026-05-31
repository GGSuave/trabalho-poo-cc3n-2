package com.codebank.extrato;

import com.codebank.enums.TipoOperacao;

public class Extrato {
    private String[] operacoes;
    private int totalOperacoes;

    public Extrato() {
        this.operacoes = new String[50];
        this.totalOperacoes = 0;
    }

    public void registrarOperacao(TipoOperacao tipo, double valor, double saldoResultante) {
        if (totalOperacoes < 50) {
            operacoes[totalOperacoes] = tipo + " | Valor: R$" + valor + " | Saldo: R$" + saldoResultante;
            totalOperacoes++;
        }
    }

    public void exibirExtrato() {
        System.out.println("===== EXTRATO =====");
        for (int i = 0; i < totalOperacoes; i++) {
            System.out.println((i + 1) + ". " + operacoes[i]);
        }
        System.out.println("===================");
    } 
}
