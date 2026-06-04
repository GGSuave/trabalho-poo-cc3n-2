package com.codebank.contas;

import java.text.NumberFormat;
import java.util.Locale;

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

	public Extrato getExtrato() {
	return this.extrato;
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
	 * Acrecenta um valor na conta. Esse valor só é acrecentado se for maior que 0.
	 * 
	 * @param valor Double - Valor a ser debitado.
	 * @throws ValorInvalidoException Caso o valor seja negativo.
	 * @return Boolean - Retorna se o deposito foi um sucesso.
	 * @author Guilherme
	 */
	public boolean creditar(double valor) {
		boolean sucesso = false;
		try {
			validaValor(valor);

			this.saldo += valor;

			this.extrato.registrarOperacao(TipoOperacao.CREDITO, valor, this.getSaldo());

			sucesso = true;

		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sucesso)
				exibirSaldo(TipoOperacao.CREDITO);
			else
				exibirSaldo();
		}

		return sucesso;

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
			this.extrato.registrarOperacao(TipoOperacao.TRANSFERENCIA_RECEBIDA, valorReceber, this.getSaldo());

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
	public abstract boolean debitar(double valor);

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
		try {
			validaValor(valor);
			validarSaldo(valor);

			this.saldo -= valor;
			this.extrato.registrarOperacao(TipoOperacao.TRANSFERENCIA_ENVIADA, valor, this.getSaldo());

			contaDestino.receberTransferencia(valor);

			sucesso = true;
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sucesso)
				exibirSaldo(TipoOperacao.TRANSFERENCIA_ENVIADA);
			else
				exibirSaldo();
		}

		return sucesso;
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

	// #region Auxiliares

	/**
	 * Recebe um valor e formata para R$.
	 * 
	 * @param valor Double - Valor a ser formatado
	 * @return String:
	 *         <ul>
	 *         <li>Se retornar "Erro", quer dizer que ocorreu algum erro na
	 *         formatação</li>
	 *         <li>R$ X, quer dizer que deu certo.</li>
	 *         </ul>
	 * @author Adivinha
	 */
	protected String formatarValor(double valor) {
		try {
			// * Explicando um pouco oq eu faço aqui, pq eu dei uma pesquisada pra aprender.
			Locale localBrasil = new Locale("pt", "BR"); // * Cria uma variavel que representa uma região. Nesse caso,
															// *falando que essa variavel representaria o Brasil

			NumberFormat formatador = NumberFormat.getCurrencyInstance(localBrasil); // * Aqui usa o NumberFormat para
																						// * formar algum valor para uma
																						// * moeda.
																						// * Passando a região para
																						// formatar
																						// * para aquela região, nesse
																						// caso,

			String saldoAtualFormatado = formatador.format(valor); // * Pega qual é o padrão de formatação e
																	// * formata.

			return saldoAtualFormatado;
		} catch (NullPointerException e) {
			System.out.println("Ocorreu algum tentar formatar o valor!");
		} catch (ArithmeticException e) {
			System.out.println("Ocorreu algum erro desconhecido ao receber o valor.");
		}

		return "Erro"; // Sim. péssimo jeito de validar, mas acontece.
	}

	/**
	 * Metodo para formatar o saldo atual.
	 * 
	 * @return Boolean - Retorna se a operação foi um sucesso
	 * @author Guilherme
	 */
	protected boolean exibirSaldo() {
		String saldoAtualFormatado = formatarValor(this.getSaldo());

		if (saldoAtualFormatado.equalsIgnoreCase("erro"))
			return false; // fiquei com preguiça de pensar em algo melhor, e é isso kkkk.

		System.out.println("Saldo: %s".formatted(saldoAtualFormatado));

		return true;

	}

	/**
	 * Metodo para formatar o saldo atual do usuário após uma operação X.
	 * 
	 * @param tipoOperacao TipoOperacao - Recebe a operação que foi executada antes
	 *                     de alterar o saldo.
	 * @return Boolean - Retorna se a operação foi um sucesso
	 * @author Guilherme
	 */
	protected boolean exibirSaldo(TipoOperacao tipoOperacao) {
		String saldoAtualFormatado = formatarValor(this.getSaldo());

		if (saldoAtualFormatado.equalsIgnoreCase("erro"))
			return false; // fiquei com preguiça de pensar em algo melhor, e é isso kkkk.

		System.out.println("Saldo após %s: %s".formatted(tipoOperacao.getDescricao(), saldoAtualFormatado));

		return true;

	}

	// #endregion Auxiliares

	// #endregion Funções
}
