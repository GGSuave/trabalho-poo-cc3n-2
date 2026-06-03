package com.codebank.contas;

import com.codebank.cliente.Cliente;
import com.codebank.enums.TipoConta;
import com.codebank.enums.TipoOperacao;
import com.codebank.excecoes.SaldoInsuficienteException;
import com.codebank.excecoes.SaldoNegativoException;
import com.codebank.excecoes.ValorInvalidoException;

public class ContaPoupanca extends Conta {

    // #region Construtores

    /**
     * Construtor da classe com saldo zerado.
     * 
     * @param numero  String - Número da conta.
     * @param titular Cliente - Titular dono da conta.
     * 
     * @author Guilherme
     */
    public ContaPoupanca(String numero, Cliente titular) {
        super(numero, TipoConta.POUPANCA, titular);
    }

    /**
     * Construtor da classe.
     * 
     * @param numero  String - Número da conta.
     * @param saldo   Double - Valor inicial da conta.
     * @param titular Cliente - Titular dono da conta.
     * 
     * @author Guilherme
     */
    public ContaPoupanca(String numero, double saldo, Cliente titular) {
        super(numero, saldo, TipoConta.POUPANCA, titular);
    }

    // #endregion Construtores

    // #region Funções

    /**
     * Calcula e aplica automaticamente, se possivel, o rendimento no saldo atual.
     * 
     * @throws SaldoNegativoException Se não foi possivel aplicar o rendimento na
     *                                conta.
     * @return Boolean - Se a operação foi um sucesso.
     * @author Guilherme
     */
    public boolean aplicarRendimento() {
        try {
            this.verificarPossibilidadeRendimento(super.getSaldo());

            double saldoAtual = super.getSaldo();
            super.saldo *= 0.005;
            super.extrato.registrarOperacao(TipoOperacao.RENDIMENTO, saldoAtual, saldo);
        } catch (SaldoNegativoException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // #region Metodos reescritos

    /**
     * Função responsavel por sacar um valor da conta.
     * 
     * @throws ValorInvalidoException     Se o valor for invalido.
     * @throws SaldoInsuficienteException Se o saldo foi insuficiênte.
     * @param valor Double - Valor que deseja sacar.
     * @author Guilherme
     */
    @Override
    public boolean sacar(double valor) {
        try {
            super.validaValor(valor);
            super.validarSaldo(valor);

            super.saldo -= valor;
            super.extrato.registrarOperacao(TipoOperacao.DEBITO, valor, super.getSaldo());

            return true;
        } catch (ValorInvalidoException e) {
            System.out.println(e.getMessage());
        } catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // #endregion Metodos reescritos

    // #region Verificações

    public void verificarPossibilidadeRendimento(double saldo) throws SaldoNegativoException {
        if (super.getSaldo() == 0)
            throw new SaldoNegativoException(super.getSaldo());
    }

    // #endregion Verificações

    // #endregion Funções

}
