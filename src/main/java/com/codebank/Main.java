package com.codebank;
 
import com.codebank.banco.Banco;
import com.codebank.cliente.Cliente;
import com.codebank.contas.ContaCorrente;
import com.codebank.contas.ContaPoupanca;
 
public class Main {
 
    public static void main(String[] args) {
 
        Banco banco = new Banco();
 
        // Clientes
        Cliente cliente1 = new Cliente("Cliente 1", "111.111.111-11");
        Cliente cliente2 = new Cliente("Cliente 2", "222.222.222-22");
 
        // Contas
        ContaCorrente contaCorrente = new ContaCorrente("CC001", 1000, 500, cliente1);
        ContaPoupanca contaPoupanca = new ContaPoupanca("CP001", 500, cliente1);
        ContaCorrente contaDestino = new ContaCorrente("CC002", 800, 300, cliente2);
 
        // Associar contas aos clientes
        cliente1.adicionarConta(contaCorrente);
        cliente1.adicionarConta(contaPoupanca);
        cliente2.adicionarConta(contaDestino);
 
        // Cadastrar clientes no banco
        banco.adicionarCliente(0, cliente1);
        banco.adicionarCliente(1, cliente2);
 
        // Operações
        System.out.println("\n===== OPERAÇÕES =====");
        contaCorrente.creditar(250);
        contaCorrente.debitar(150);
        contaCorrente.transferir(contaDestino, 200);
        contaPoupanca.aplicarRendimento();
        contaCorrente.cobrarTarifa();
        contaDestino.debitar(1000);
        contaDestino.creditar(500);
 
        // Relatórios
        System.out.println("\n===== EXTRATO - Conta Corrente (CC001) =====");
        contaCorrente.getExtrato().exibirExtrato();
 
        System.out.println("\n===== EXTRATO - Conta Poupança (CP001) =====");
        contaPoupanca.getExtrato().exibirExtrato();
 
        System.out.println("\n===== EXTRATO - Conta Destino (CC002) =====");
        contaDestino.getExtrato().exibirExtrato();
 
        System.out.println("\n===== CLIENTES POR AGÊNCIA =====");
        banco.clientesPorAgencia(0);
        banco.clientesPorAgencia(1);
 
        System.out.println("\n===== RELATÓRIO GERAL DO BANCO =====");
        banco.relatorioGeral();
    }
}
