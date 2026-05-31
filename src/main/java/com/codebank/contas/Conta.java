package com.codebank.contas;

import com.codebank.enums.TipoConta;
import com.codebank.enums.TipoOperacao;
import com.codebank.excecoes.SaldoInsuficienteException;
import com.codebank.excecoes.ValorInvalidoException;
import com.codebank.extrato.Extrato;

public abstract class Conta {
	private String numero;
	private Extrato extrato;
	private double saldo;
	private TipoConta tipoConta;
	// private Cliente titular;

	// #region Construtor

	/**
	 * 
	 * @param numero    String - Número da conta.
	 * @param saldo     Double - Saldo inicial da conta.
	 * @param tipoConta TipoConta (Enum) - Tipo da conta.
	 * @author Guilherme
	 */
	public Conta(String numero, double saldo, TipoConta tipoConta) {
		this.saldo = saldo;
		this.numero = numero;
		this.tipoConta = tipoConta;

		this.extrato = new Extrato();
	}

	// #endregion Construtor

	// #region Getters

	public double getSaldo() {
		return this.saldo;
	}

	public TipoConta getTipoConta() {
		return this.tipoConta;
	}

	public String getNumero() {
		return this.numero;
	}

	// #endregion Getters

	// #region Funções

	// #region Operações Bancárias

	/**
	 * 
	 * @param valor Double - Valor a ser debitado.
	 * @throws ValorInvalidoException Caso o valor seja negativo.
	 * @author Guilherme
	 */
	public void depositar(double valor) throws ValorInvalidoException {
		if (valor < 0)
			throw new ValorInvalidoException(valor);

		this.saldo += valor;

		this.extrato.registrarOperacao(TipoOperacao.CREDITO, valor, this.getSaldo());
	}

	/**
	 * Função resposável por receber uma transferência de uma outra conta.
	 * 
	 * @param valorReceber Double - Valor a ser recebido.
	 * @throws ValorInvalidoException Caso o valor seja negativo.
	 * @author Guilherme
	 */
	public void receberTransferencia(double valorReceber) {
		try {
			validaValor(valorReceber);

			this.saldo += valorReceber;
			this.extrato.registrarOperacao(TipoOperacao.TRANSFERENCIA_RECEBIDA, saldo, this.getSaldo());

		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param valor Double - Valor a ser sacado da conta.
	 * @throws ValorInvalidoException     Se o valor desejado for menor que 0.
	 * @throws SaldoInsuficienteException Se o saldo da conta for menor que o
	 *                                    valor desejado.
	 * @author Guilherme
	 */
	public void sacar(double valor) throws ValorInvalidoException, SaldoInsuficienteException {
		if (valor < 0)
			throw new ValorInvalidoException(valor);

		if (this.getSaldo() < valor)
			throw new SaldoInsuficienteException(this.getSaldo(), valor);

		this.saldo -= valor;

		this.extrato.registrarOperacao(TipoOperacao.DEBITO, valor, this.getSaldo());
	}

	/**
	 * Função responsável pela transferência do valor de uma conta para outra.
	 * 
	 * @param contaDestino Conta - Conta que receberá o valor.
	 * @param valor        Double - Valor a ser transferido.
	 * @throws ValorInvalidoException     Se o valor desejado for menor que 0.
	 * @throws SaldoInsuficienteException Se o saldo da conta for menor que o
	 *                                    valor desejado.
	 * @author Guilherme
	 */
	public void transferir(Conta contaDestino, double valor) {
		try {
			validaValor(valor);
			validarSaldo(valor);

			this.saldo -= valor;
			this.extrato.registrarOperacao(TipoOperacao.TRANSFERENCIA_ENVIADA, valor, this.getSaldo());

			contaDestino.receberTransferencia(valor);

		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}

	}

	// #endregion Operações

	// #region Funções Abstratas

	@Override
	public abstract String toString();

	public abstract String sobreConta();

	// #endregion Funções Abstratas

	// #region Funções de Validação (Try e Catch)

	/**
	 * Só fiz pq tava repetindo de mais o cod. Só despara o throw msm!
	 * 
	 * @param valor Double - Valor a ser validado
	 * @throws SaldoInsuficienteException Se o saldo da conta for menor que o
	 *                                    valor desejado.
	 * @author O maior preguiçoso do mundo (Little Suave)! :D
	 */
	private void validarSaldo(double valor) throws SaldoInsuficienteException {
		if (this.getSaldo() < valor)
			throw new SaldoInsuficienteException(this.getSaldo(), valor);
	}

	/**
	 * Só fiz pq tava repetindo de mais o cod. Só despara o throw msm!
	 * 
	 * @param valor Double - Valor a ser validado
	 * @throws SaldoInsuficienteException Se o valor desejado for menor que 0.
	 * @author O maior preguiçoso do mundo (Little Suave)! :D
	 */
	private void validaValor(double valor) throws ValorInvalidoException {
		if (valor < 0)
			throw new ValorInvalidoException(valor);
	}

	// #endregion Funções de Validação (Try e Catch)

	// #endregion Funções
}
