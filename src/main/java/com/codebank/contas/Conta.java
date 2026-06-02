package com.codebank.contas;

import com.codebank.cliente.Cliente;
import com.codebank.enums.TipoConta;
import com.codebank.enums.TipoOperacao;
import com.codebank.excecoes.SaldoInsuficienteException;
import com.codebank.excecoes.ValorInvalidoException;
import com.codebank.extrato.Extrato;

public abstract class Conta {
	protected String numero;
	protected Extrato extrato;
	protected double saldo;
	protected TipoConta tipoConta;
	private Cliente titular;

	// #region Construtor

	/**
	 * Contrutor mais básico. O saldo é iniciado zerado.
	 * 
	 * @param numero    String - Número da conta.
	 * @param tipoConta TipoConta (Enum) - Tipo da conta.
	 * @param titular   Cliente - Cliente titular da conta.
	 */
	public Conta(String numero, TipoConta tipoConta, Cliente titular) {
		this(numero, 0, tipoConta, titular);
	}

	/**
	 * 
	 * @param numero    String - Número da conta.
	 * @param saldo     Double - Saldo inicial da conta.
	 * @param tipoConta TipoConta (Enum) - Tipo da conta.
	 * @author Guilherme
	 */
	public Conta(String numero, double saldo, TipoConta tipoConta, Cliente titular) {
		this.saldo = saldo;
		this.numero = numero;
		this.tipoConta = tipoConta;
		this.titular = titular;

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

	/**
	 * Getter com algumas informações da conta.
	 * 
	 * @return String - Retorna o tipo da conta, o nome do titular e o Número da
	 *         conta.
	 * @author Guilherme
	 */
	public String sobreConta() {

		return """
				===| %s |===
					Titular: %s
					Número da conta: %s
				========================
				""".formatted(tipoConta.getNome(), titular.getNome(), this.getNumero());
	}
	// #endregion Getters

	// #region Funções

	// #region Operações Bancárias

	/**
	 * 
	 * @param valor Double - Valor a ser debitado.
	 * @throws ValorInvalidoException Caso o valor seja negativo.
	 * @return Boolean - Retorna se o deposito foi um sucesso.
	 * @author Guilherme
	 */
	public boolean depositar(double valor) {
		try {
			validaValor(valor);

			this.saldo += valor;

			this.extrato.registrarOperacao(TipoOperacao.CREDITO, valor, this.getSaldo());

			return true;

		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());

			return false;
		}

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
	 * Metodo abstrato a ser tratado de forma individual. Deve-se descontar do
	 * saldo.
	 * 
	 * @author Guilherme
	 */
	public abstract boolean sacar(double valor);

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

	// #region Funções de Validação (Try e Catch)

	/**
	 * Só fiz pq tava repetindo de mais o cod. Só despara o throw msm!
	 * 
	 * @param valor Double - Valor a ser validado
	 * @throws SaldoInsuficienteException Se o saldo da conta for menor que o
	 *                                    valor desejado.
	 * @author O maior preguiçoso do mundo (Little Suave)! :D
	 */
	protected void validarSaldo(double valor) throws SaldoInsuficienteException {
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
	protected void validaValor(double valor) throws ValorInvalidoException {
		if (valor <= 0)
			throw new ValorInvalidoException(valor);
	}

	// #endregion Funções de Validação (Try e Catch)

	// #endregion Funções
}
