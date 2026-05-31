package com.codebank.cliente;

public class Cliente {
    private String nome;
    private String cpf;
    //private Conta[] contas;
    private int totalContas;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        //this.contas = new Conta[5];
        this.totalContas = 0;
    }
    //alterar parametro de int para Conta, após ter classe Conta
    public void adicionarConta(int conta) {
        if (totalContas < 5) {
            //contas[totalContas] = conta;
            totalContas++;
        } else {
            System.out.println("Limite de 5 contas atingido para o cliente " + nome);
        }
    }

    public void removerConta(int numero) {
        for (int i = 0; i < totalContas; i++) {
            // if (contas[i].getNumero() == numero) {
            //     contas[i] = contas[totalContas - 1];
            //     contas[totalContas - 1] = null;
            //     totalContas--;
            //     System.out.println("Conta " + numero + " encerrada. Cliente " + nome + " permanece cadastrado.");
            //     return;
            //}
        }
        //System.out.println("Conta " + numero + " não encontrada.");
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    //public Conta[] getContas() { return contas; }
    public int getTotalContas() { return totalContas; }
}
