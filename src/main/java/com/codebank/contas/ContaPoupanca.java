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
        boolean sucesso = false;

        try {
            this.verificarPossibilidadeRendimento();

            double rendimento = super.saldo * 0.005;
            super.saldo += rendimento;

            super.extrato.registrarOperacao(TipoOperacao.RENDIMENTO, rendimento, saldo);

            sucesso = true;
        } catch (SaldoNegativoException e) {
            System.out.println(e.getMessage());
        } finally {
            if (sucesso)
                super.exibirSaldo(TipoOperacao.RENDIMENTO);
            else
                super.exibirSaldo();
        }

        return sucesso;
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
    public boolean debitar(double valor) {
        boolean sucesso = false;

        try {
            super.validaValor(valor);
            super.validarSaldo(valor);

            super.saldo -= valor;
            super.extrato.registrarOperacao(TipoOperacao.DEBITO, valor, super.getSaldo());

            sucesso = true;
        } catch (ValorInvalidoException e) {
            System.out.println(e.getMessage());
        } catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        } finally {
            if (sucesso)
                super.exibirSaldo(TipoOperacao.DEBITO);
            else
                super.exibirSaldo();
        }

        return sucesso;
    }

    // #endregion Metodos reescritos

    // #region Verificações

    /**
     * Verifica a possibilidade de aplicar um rendimento no saldo atual da conta.
     * 
     * @throws SaldoNegativoException Dispara se o saldo <= 0.
     * @author Guilherme.
     */
    private void verificarPossibilidadeRendimento() throws SaldoNegativoException {
        if (super.getSaldo() == 0 || super.getSaldo() < 0)
            throw new SaldoNegativoException(super.getSaldo());
    }

    // #endregion Verificações

    // #endregion Funções

}
