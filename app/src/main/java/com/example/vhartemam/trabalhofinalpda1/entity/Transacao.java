package com.example.vhartemam.trabalhofinalpda1.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Transacao {

    // attributes
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id_transacao")
    private int idTransacao;

    @ColumnInfo(name = "id_modelo_transacao")
    private int idModeloTransacao;

    @ColumnInfo (name = "id_conta")
    private int idConta;
    // end attributes

    public Transacao() {    }

    public Transacao(int idModeloTransacao, int idConta) {
        this.idModeloTransacao = idModeloTransacao;
        this.idConta = idConta;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }

    public int getIdModeloTransacao() {
        return idModeloTransacao;
    }

    public void setIdModeloTransacao(int idModeloTransacao) {
        this.idModeloTransacao = idModeloTransacao;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }
}
