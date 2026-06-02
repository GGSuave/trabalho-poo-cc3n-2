package com.codebank.enums;

public enum TipoOperacao {
    CREDITO(1, "Crédito"),
    DEBITO(2, "Débito"),
    DEBITO_CHEQUE(3, "Débito com Cheque Especial"),
    TRANSFERENCIA_ENVIADA(4, "Transferência Enviada"),
    TRANSFERENCIA_RECEBIDA(5, "Transferência Recebida"),
    RENDIMENTO(6, "Rendimento"),
    TARIFA(7, "Tarifa"),
    PAGAR_DEBITO_CHEQUE(8, "Débito Após Pagar Débito de Cheque Especial");

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
