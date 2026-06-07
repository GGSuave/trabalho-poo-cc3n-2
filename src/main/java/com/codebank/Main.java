package com.codebank;

import com.codebank.banco.Banco;
import com.codebank.cliente.Cliente;
import com.codebank.contas.ContaCorrente;
import com.codebank.contas.ContaPoupanca;

public class Main {

    public static void main(String[] args) {

        // CONFIGURAÇÃO INICIAL DO BANCO

        Banco banco = new Banco();

        // Clientes
        Cliente cliente1 = new Cliente("Ana Paula Silva", "111.111.111-11");
        Cliente cliente2 = new Cliente("Bruno Costa Lima", "222.222.222-22");
        Cliente cliente3 = new Cliente("Carla Souza Mendes", "333.333.333-33");

        // Contas do Cliente 1
        ContaCorrente cc1 = new ContaCorrente("CC001", 1000.00, 500.00, cliente1);
        ContaPoupanca cp1 = new ContaPoupanca("CP001", 2000.00, cliente1);

        // Contas do Cliente 2
        ContaCorrente cc2 = new ContaCorrente("CC002", 300.00, 200.00, cliente2);
        ContaPoupanca cp2 = new ContaPoupanca("CP002", 150.00, cliente2);

        // Conta do Cliente 3
        ContaCorrente cc3 = new ContaCorrente("CC003", 5000.00, 1000.00, cliente3);

        // Associar contas aos clientes
        cliente1.adicionarConta(cc1);
        cliente1.adicionarConta(cp1);
        cliente2.adicionarConta(cc2);
        cliente2.adicionarConta(cp2);
        cliente3.adicionarConta(cc3);

        // Cadastrar clientes nas agências
        banco.adicionarCliente(0, cliente1);
        banco.adicionarCliente(0, cliente2);
        banco.adicionarCliente(1, cliente3);

        // OPERAÇÕES - CONTA CORRENTE (CC001 - Ana Paula)

        System.out.println("\n========================================");
        System.out.println("  OPERAÇÕES - CC001 (Ana Paula Silva)");
        System.out.println("========================================");

        System.out.println("\n--- Crédito de R$ 500,00 ---");
        cc1.creditar(500.00);

        System.out.println("\n--- Débito de R$ 200,00 ---");
        cc1.debitar(200.00);

        System.out.println("\n--- Débito usando cheque especial (R$ 1.400,00) ---");
        cc1.debitar(1400.00);

        System.out.println("\n--- Tentativa de débito com limite excedido (R$ 999,00) ---");
        cc1.debitar(999.00); // deve falhar: saldo + limite insuficiente

        System.out.println("\n--- Cobrança de tarifa mensal ---");
        cc1.cobrarTarifa();

        System.out.println("\n--- Tentativa de crédito com valor inválido (R$ -50,00) ---");
        cc1.creditar(-50.00); // deve falhar: valor inválido

        // OPERAÇÕES - CONTA POUPANÇA (CP001 - Ana Paula)

        System.out.println("\n========================================");
        System.out.println("  OPERAÇÕES - CP001 (Ana Paula Silva)");
        System.out.println("========================================");

        System.out.println("\n--- Crédito de R$ 300,00 ---");
        cp1.creditar(300.00);

        System.out.println("\n--- Aplicação de rendimento (0,5%) ---");
        cp1.aplicarRendimento();

        System.out.println("\n--- Débito de R$ 500,00 ---");
        cp1.debitar(500.00);

        System.out.println("\n--- Tentativa de débito com saldo insuficiente (R$ 5.000,00) ---");
        cp1.debitar(5000.00); // deve falhar

        // TRANSFERÊNCIAS

        System.out.println("\n========================================");
        System.out.println("  TRANSFERÊNCIAS");
        System.out.println("========================================");

        System.out.println("\n--- Transferência de CC003 para CP001 (R$ 700,00) ---");
        cc3.transferir(cp1, 700.00);

        System.out.println("\n--- Transferência usando cheque especial: CC002 para CC003 (R$ 450,00) ---");
        cc2.transferir(cc3, 450.00); // saldo de CC002 é 300, tem limite de 200

        System.out.println("\n--- Tentativa de transferência com valor zero ---");
        cc3.transferir(cp1, 0.00); // deve falhar: valor inválido

        // OPERAÇÕES - CONTA CORRENTE (CC002 - Bruno)

        System.out.println("\n========================================");
        System.out.println("  OPERAÇÕES - CC002 (Bruno Costa Lima)");
        System.out.println("========================================");

        System.out.println("\n--- Crédito de R$ 1.000,00 para cobrir saldo negativo ---");
        cc2.creditar(1000.00);

        System.out.println("\n--- Cobrança de tarifa mensal ---");
        cc2.cobrarTarifa();

        System.out.println("\n--- Aplicação de rendimento em CP002 (0,5%) ---");
        cp2.aplicarRendimento();

        // ERRO ESPERADO - Rendimento com saldo zero

        System.out.println("\n========================================");
        System.out.println("  ERRO ESPERADO - Rendimento sem saldo");
        System.out.println("========================================");

        ContaPoupanca cpVazia = new ContaPoupanca("CP999", 0.00, cliente3);
        System.out.println("\n--- Tentativa de rendimento com saldo zero ---");
        cpVazia.aplicarRendimento(); // deve falhar: SaldoNegativoException

        // RELATÓRIO: EXTRATOS POR CONTA

        System.out.println("\n\n============================================================");
        System.out.println("  EXTRATO - CC001 (Ana Paula Silva)");
        System.out.println("============================================================");
        cc1.getExtrato().exibirExtrato();

        System.out.println("\n============================================================");
        System.out.println("  EXTRATO - CP001 (Ana Paula Silva)");
        System.out.println("============================================================");
        cp1.getExtrato().exibirExtrato();

        System.out.println("\n============================================================");
        System.out.println("  EXTRATO - CC002 (Bruno Costa Lima)");
        System.out.println("============================================================");
        cc2.getExtrato().exibirExtrato();

        System.out.println("\n============================================================");
        System.out.println("  EXTRATO - CP002 (Bruno Costa Lima)");
        System.out.println("============================================================");
        cp2.getExtrato().exibirExtrato();

        System.out.println("\n============================================================");
        System.out.println("  EXTRATO - CC003 (Carla Souza Mendes)");
        System.out.println("============================================================");
        cc3.getExtrato().exibirExtrato();

        // RELATÓRIO: CLIENTES POR AGÊNCIA

        System.out.println("\n\n============================================================");
        System.out.println("  CLIENTES POR AGÊNCIA");
        System.out.println("============================================================");
        banco.clientesPorAgencia(0);
        System.out.println();
        banco.clientesPorAgencia(1);
        System.out.println();
        banco.clientesPorAgencia(2);

        // RELATÓRIO GERAL DO BANCO

        System.out.println("\n\n============================================================");
        System.out.println("  RELATÓRIO GERAL DO BANCO");
        System.out.println("============================================================");
        banco.relatorioGeral();
    }
}
