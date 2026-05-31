package com.codebank.enums;

public enum TipoOperacao {
    CREDITO(1, "Crédito"),
    DEBITO(2, "Débito"),
    TRANSFERENCIA_ENVIADA(3, "Transferência Enviada"),
    TRANSFERENCIA_RECEBIDA(4, "Transferência Recebida"),
    RENDIMENTO(5, "Rendimento"),
    TARIFA(6, "Tarifa");

    private final int codigo;
    private final String descricao;

    TipoOperacao(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
