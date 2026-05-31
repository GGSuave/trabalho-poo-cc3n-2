package com.codebank.extrato;

import com.codebank.enums.TipoOperacao;

public class Extrato {
    private String[] operacoes;
    private int totalOperacoes;

    /**
     * Construtor inicia automaticamente o extrato.
     * ! OBS: É possivel cadastrar até 50 operações.
     * 
     * @author Taina
     */
    public Extrato() {
        this.operacoes = new String[50];
        this.totalOperacoes = 0;
    }

    /**
     * Função responsavel pelo cadastro do Extrato de uma conta.
     * 
     * @param tipo            Tipo da operação. Use o Enum do tipo como base.
     * @param valor           Valor da operação.
     * @param saldoResultante Saldo restante.
     * @author Taina
     */
    public void registrarOperacao(TipoOperacao tipo, double valor, double saldoResultante) {
        if (totalOperacoes < 50) {
            operacoes[totalOperacoes] = tipo.getDescricao() + " | Valor: R$" + valor + " | Saldo: R$" + saldoResultante;
            totalOperacoes++;
        }
    }

    /**
     * Função que exibe todas as operações realizadas na conta.
     * 
     * @author Taina
     */
    public void exibirExtrato() {
        System.out.println("===== EXTRATO =====");
        for (int i = 0; i < totalOperacoes; i++) {
            System.out.println((i + 1) + ". " + operacoes[i]);
        }
        System.out.println("===================");
    }
}
