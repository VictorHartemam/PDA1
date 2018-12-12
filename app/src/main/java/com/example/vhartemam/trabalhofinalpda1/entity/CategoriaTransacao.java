package com.example.vhartemam.trabalhofinalpda1.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "categoria_transacao")
public class CategoriaTransacao {

    // attributes
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id_categoria_transacao")
    private int idCategoriaTransacao;

    @NonNull
    private String descricao;
    //end attributes

    public CategoriaTransacao() { }

    public CategoriaTransacao(int idCategoriaTransacao, @NonNull String descricao) {
        this.idCategoriaTransacao = idCategoriaTransacao;
        this.descricao = descricao;
    }

    public CategoriaTransacao(@NonNull String descricao) {
        this.descricao = descricao;
    }

    public int getIdCategoriaTransacao() {
        return idCategoriaTransacao;
    }

    public void setIdCategoriaTransacao(int idCategoriaTransacao) {
        this.idCategoriaTransacao = idCategoriaTransacao;
    }

    @NonNull
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }
}
