package com.example.vhartemam.trabalhofinalpda1.ui;

public class TransacaoModeloTransacaoUi {
    private double valor;
    private String descricao;

    public TransacaoModeloTransacaoUi(double valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
