package com.codebank.contas;

import com.codebank.cliente.Cliente;
import com.codebank.enums.TipoConta;
import com.codebank.enums.TipoOperacao;
import com.codebank.excecoes.DebitoChequeInexistente;
import com.codebank.excecoes.LimiteExcedidoException;
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

	public String getDebitoDeChequeEspecial() {
		return "R$ %s".formatted(super.formatarValor(this.debitoChequeEspecial));
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
		boolean sucesso = false;
		try {
			this.validarExistenciaDeDebito();
			super.validarSaldo(this.debitoChequeEspecial);

			double valorPagar = this.debitoChequeEspecial;
			this.debitoChequeEspecial = 0;

			super.saldo -= valorPagar;

			super.extrato.registrarOperacao(TipoOperacao.DEBITO_CHEQUE, valorPagar, super.saldo);

			sucesso = true;
		} catch (DebitoChequeInexistente e) {
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
		super.extrato.registrarOperacao(TipoOperacao.TARIFA, calcularTarifa(), super.getSaldo());
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
	public boolean debitar(double valor) {
		boolean sucesso = false;
		boolean precisaUsarCheque = valor > super.getSaldo();

		try {
			super.validaValor(valor);
			this.validarSaldoComLimite(valor);

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

			sucesso = true;
		} catch (LimiteExcedidoException e) {
			System.out.println(e.getMessage());
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sucesso)
				super.exibirSaldo(precisaUsarCheque
						? TipoOperacao.DEBITO_CHEQUE
						: TipoOperacao.DEBITO);
			else
				super.exibirSaldo();
		}

		return sucesso;

	}

	/**
	 * Função responsável pela transferência do valor de uma conta para outra.
	 * 
	 * @param contaDestino Conta - Conta que receberá o valor.
	 * @param valor        Double - Valor a ser transferido.
	 * 
	 * @return boolean - Se a operação foi um sucesso
	 * @author Guilherme
	 */
	public boolean transferir(Conta contaDestino, double valor) {
		boolean sucesso = false;
		boolean precisaUsarCheque = valor > super.getSaldo();

		try {
			validaValor(valor);
			validarSaldoComLimite(valor);

			if (precisaUsarCheque) {
				double limiteNecessario = valor - super.getSaldo();
				super.saldo = 0;

				this.debitoChequeEspecial += limiteNecessario;
				this.extrato.registrarOperacao(TipoOperacao.TRANSFERENCIA_ENVIADA_LIMITE, valor, this.getSaldo());

				contaDestino.receberTransferencia(valor);
			} else {
				sucesso = super.transferir(contaDestino, valor);

				return sucesso;
			}

			sucesso = true;
		} catch (LimiteExcedidoException e) {
			System.out.println(e.getMessage());
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sucesso)
				exibirSaldo(TipoOperacao.TRANSFERENCIA_ENVIADA_LIMITE);
			else
				exibirSaldo();
		}

		return sucesso;
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
	private void validarSaldoComLimite(double valor) throws LimiteExcedidoException {
		if (this.getSaldoLimite() < valor)
			throw new LimiteExcedidoException(this.getSaldoLimite(), this.limiteChequeEspecial, valor);
	}

	private void validarExistenciaDeDebito() throws DebitoChequeInexistente {
		if (this.debitoChequeEspecial == 0)
			throw new DebitoChequeInexistente();
	}

	// #endregion Validação

	// #endregion Métodos
}
