package com.codebank.enums;

public enum TipoConta {
	CORRENTE(1, "Conta Corrente"),
	POUPANCA(2, "Conta Poupança");

	private final int id;
	private final String nome;

	TipoConta(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
