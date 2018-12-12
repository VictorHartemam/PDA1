package com.example.vhartemam.trabalhofinalpda1.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Conta {

    // attributes
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id_conta")
    private int idConta;

    private String descricao;
    // end attributes


    public Conta() {    }

    public Conta(String descricao) {
        this.descricao = descricao;
    }

    public Conta(int idConta, String descricao) {
        this.idConta = idConta;
        this.descricao = descricao;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
