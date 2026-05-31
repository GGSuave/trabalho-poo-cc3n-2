package com.codebank.banco;

import com.codebank.cliente.Cliente;

public class Banco {
    private Cliente[][] agencias;

    // #region Construtor

    /**
     * Inicializa o banco com 3 agências, cada uma com capacidade para 10 clientes.
     * 
     * @author Tainá
     */
    public Banco() {
        this.agencias = new Cliente[3][10];
    }

    // #endregion Construtor

    // #region Funções

    // #region Operações do banco

    /**
     * Adiciona um cliente a uma agência específica.
     * 
     * @param agencia int - Índice da agência (0 a 2).
     * @param cliente Cliente - Cliente a ser adicionado.
     * @author Tainá
     */
    public void adicionarCliente(int agencia, Cliente cliente) {
        for (int i = 0; i < agencias[agencia].length; i++) {
            if (agencias[agencia][i] == null) {
                agencias[agencia][i] = cliente;
                return;
            }
        }
        System.out.println("Limite de clientes atingido na agência " + agencia);
    }

    // #endregion Operações do banco

    // #region Relatórios

    /**
     * Lista todos os clientes de uma agência específica,
     * exibindo nome, CPF e total de contas de cada um.
     * 
     * @param agencia int - Índice da agência (0 a 2).
     * @author Tainá
     */
    public void clientesPorAgencia(int agencia) {
        System.out.println("=== Agência " + agencia + " ===");
        for (Cliente cliente : agencias[agencia]) {
            if (cliente != null) {
                System.out.println("Nome: " + cliente.getNome() +
                        " | CPF: " + cliente.getCpf() +
                        " | Contas: " + cliente.getTotalContas());
            }
        }
    }

    /**
     * Percorre todas as agências e clientes, exibindo o total de clientes por agência e o saldo total consolidado de todo o banco.
     * 
     * @author Tainá
     */
    public void relatorioGeral() {
        double saldoTotal = 0;

        System.out.println("=== Relatório Geral do Banco ===");
        for (int i = 0; i < agencias.length; i++) {
            int totalClientes = 0;

            for (int j = 0; j < agencias[i].length; j++) {
                if (agencias[i][j] != null) {
                    totalClientes++;

                    for (int k = 0; k < agencias[i][j].getTotalContas(); k++) {
                        saldoTotal += agencias[i][j].getContas()[k].getSaldo();
                    }
                }
            }

            System.out.println("Agência " + i + ": " + totalClientes + " cliente(s)");
        }

        System.out.printf("Saldo total custodiado: R$ %.2f%n", saldoTotal);
    }

    // #endregion Relatórios

    // #region Getters

    public Cliente[][] getAgencias() { return agencias; }

    // #endregion Getters

    // #endregion Funções
}