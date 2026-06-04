package com.codebank.enums;

public enum TipoOperacao {
    CREDITO(1, "Crédito"),
    DEBITO(2, "Débito"),
    DEBITO_CHEQUE(3, "Débito com Cheque Especial"),
    TRANSFERENCIA_ENVIADA(4, "Transferência Enviada"),
    TRANSFERENCIA_ENVIADA_LIMITE(5, "Transferência Enviada Com Limite"),
    TRANSFERENCIA_RECEBIDA(6, "Transferência Recebida"),
    RENDIMENTO(7, "Rendimento"),
    TARIFA(8, "Tarifa"),
    PAGAR_DEBITO_CHEQUE(9, "Débito Após Pagar Débito de Cheque Especial");

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
