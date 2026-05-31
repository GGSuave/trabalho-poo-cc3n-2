package com.codebank.cliente;

import com.codebank.contas.Conta;

public class Cliente {
    private String nome;
    private String cpf;
    private Conta[] contas;
    private int totalContas;

    // #region Construtor

    /**
     * 
     * @param nome String - Nome do cliente.
     * @param cpf  String - CPF do cliente.
     * @author Tainá
     */
    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.contas = new Conta[5];
        this.totalContas = 0;
    }

    // #endregion Construtor

    // #region Funções

    // #region Operações de contas do cliente

    /**
     * Adiciona uma conta ao cadastro do cliente, respeitando o limite de 5 contas.
     * 
     * @param conta Conta - Conta a ser adicionada.
     * @author Tainá
     */
    public void adicionarConta(Conta conta) {
        if (totalContas < 5) {
            contas[totalContas] = conta;
            totalContas++;
        } else {
            System.out.println("Limite de 5 contas atingido para o cliente " + nome);
        }
    }

    /**
     * Remove uma conta do cadastro do cliente pelo número da conta.
     * O cliente permanece cadastrado no sistema após o encerramento da conta.
     * 
     * @param numero String - Número da conta a ser removida.
     * @author Tainá
     */
    public void removerConta(String numero) {
        for (int i = 0; i < totalContas; i++) {
            if (contas[i].getNumero().equals(numero)) {
                contas[i] = contas[totalContas - 1];
                contas[totalContas - 1] = null;
                totalContas--;
                System.out.println("Conta " + numero + " encerrada. Cliente " + nome + " permanece cadastrado.");
                return;
            }
        }
        System.out.println("Conta " + numero + " não encontrada.");
    }

    // #endregion Operações de contas do cliente

    // #region Getters

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public Conta[] getContas() { return contas; }
    public int getTotalContas() { return totalContas; }

    // #endregion Getters

    // #endregion Funções
}