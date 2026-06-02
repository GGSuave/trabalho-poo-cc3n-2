package com.codebank.contas;

import com.codebank.cliente.Cliente;
import com.codebank.enums.TipoConta;
import com.codebank.enums.TipoOperacao;
import com.codebank.excecoes.DebitoChequeInexistente;
import com.codebank.excecoes.SaldoInsuficienteException;
import com.codebank.excecoes.ValorInvalidoException;
import com.codebank.interfaces.Tributavel;

public class ContaCorrente extends Conta implements Tributavel {
	private double limiteChequeEspecial;
	private double debitoChequeEspecial;

	// #region Construtores

	/**
	 * Construtor da classe. Saldo inicial e limite zerados.
	 * 
	 * @param numero  String - Número da conta.
	 * @param titular Cliente - Cliente titular da conta.
	 * @author Guilherme
	 */
	public ContaCorrente(String numero, Cliente titular) {
		this(numero, 0, 0, titular);
	}

	/**
	 * Construtor da classe. Com o limite zerado.
	 * 
	 * @param numero  String- Número da conta
	 * @param saldo   Double - Valor inicial do saldo.
	 * @param titular Cliente - Cliente titular da conta.
	 * @author Guilherme
	 */
	public ContaCorrente(String numero, double saldo, Cliente titular) {
		this(numero, saldo, 0, titular);
	}

	/**
	 * Construtor da classe, com o limite.
	 * 
	 * @param numero               String- Número da conta
	 * @param saldo                Double - Valor inicial do saldo.
	 * @param limiteChequeEspecial Double - Limite do cheque especial.
	 * @param titular              Cliente - Cliente titular da
	 *                             conta.
	 * @author Guilherme
	 */
	public ContaCorrente(String numero, double saldo, double limiteChequeEspecial,
			Cliente titular) {
		super(numero, saldo, TipoConta.CORRENTE, titular);
		this.limiteChequeEspecial = limiteChequeEspecial;
	}

	// #endregion Construtores

	// #region Métodos

	public double getDebitoDeChequeEspecial() {
		return this.debitoChequeEspecial;
	}

	/**
	 * Método que paga o débito do uso do Cheque Especial utilizando o saldo.
	 * 
	 * @return Boolean - Se a operação ocorreu corretamente.
	 * @throws DebitoChequeInexistente    Se não existir nenhum débito a ser pago.
	 * @throws SaldoInsuficienteException Se o Saldo for insuficiente.
	 * @author Guilherme
	 */
	public boolean pagarDebitoChequeComSaldo() {
		try {
			this.validarExistenciaDeDebito();
			super.validarSaldo(this.debitoChequeEspecial);

			super.saldo -= this.debitoChequeEspecial;
			this.debitoChequeEspecial = 0;

			super.extrato.registrarOperacao(TipoOperacao.DEBITO, debitoChequeEspecial, saldo);

			return true;
		} catch (DebitoChequeInexistente e) {
			System.out.println(e.getMessage());
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	// #region Overrided

	/**
	 * Método que calcula o saldo total disponivel para o cliente.
	 * A soma se basea no Limite do Cheque especial + saldo da conta.
	 * 
	 * @return Double - Saldo Total (Saldo + Cheque Especial)
	 * @author Guilherme
	 */
	public double getSaldoLimite() {
		return super.saldo + this.limiteChequeEspecial;
	}

	@Override
	public double calcularTarifa() {
		return 15;
	}

	/**
	 * Cobrança de tarifa para a conta corrente.
	 * 
	 * @param valorCobrar Double - Valor da tarifa a ser cobrada.
	 * @author Guilherme
	 */
	@Override
	public void cobrarTarifa() {
		super.saldo -= calcularTarifa();
	}

	/**
	 * Método responsavel pelo saque de um valor da conta. <br>
	 * <h4>Casos de uso:</h4>
	 * <ul>
	 * <li>Se o valor desejado for menor que o saldo, débita apenas do saldo.</li>
	 * <li>Se o valor desejado for maior que o saldo, débita do saldo e adiciona o
	 * necessario na divida do cheque especial.</li>
	 * </ul>
	 * 
	 * @param valor Double - Valor que deseja ser sacado.
	 * @throws ValorInvalidoException Se o valor desejado for inferior a 0.
	 * @throws validarSaldoComLimite  Se o valor desejado for inferior a soma do
	 *                                saldo com o cheque especial.
	 * @author Little Suave.
	 */
	@Override
	public boolean sacar(double valor) {
		try {
			super.validaValor(valor);
			this.validarSaldoComLimite(valor);

			boolean precisaUsarCheque = valor > super.getSaldo();

			if (precisaUsarCheque) {
				double limiteNecessario = valor - super.getSaldo();
				super.saldo = 0;

				this.debitoChequeEspecial += limiteNecessario;
			} else {
				super.saldo -= valor;
			}

			super.extrato.registrarOperacao(precisaUsarCheque
					? TipoOperacao.DEBITO_CHEQUE
					: TipoOperacao.DEBITO,
					valor, super.getSaldo());

			return true;
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}

		return false;

	}

	// #endregion Overrided

	// #region Validação

	/**
	 * Dispara o erro se o saldo + limite ser inferior ao valor
	 * desejado.
	 * 
	 * @throws SaldoInsuficienteException
	 * @author Guilherme
	 */
	protected void validarSaldoComLimite(double valor) throws SaldoInsuficienteException {
		if (this.getSaldoLimite() < valor)
			throw new SaldoInsuficienteException(this.getSaldoLimite(), valor);
	}

	protected void validarExistenciaDeDebito() throws DebitoChequeInexistente {
		if (this.debitoChequeEspecial == 0)
			throw new DebitoChequeInexistente();
	}

	// #endregion Validação

	// #endregion Métodos
}
