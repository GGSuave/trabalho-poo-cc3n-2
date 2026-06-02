package com.codebank.excecoes;

public class DebitoChequeInexistente extends Exception {
	public DebitoChequeInexistente() {
		super("Nenhum valor existente a ser pago");
	}
}