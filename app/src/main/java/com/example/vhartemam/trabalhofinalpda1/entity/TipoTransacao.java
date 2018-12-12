package com.example.vhartemam.trabalhofinalpda1.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "tipo_transacao")
public class TipoTransacao {

    // attributes
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id_tipo_transacao")
    private int idTipoTransacao;

    @NonNull
    private String descricao;
    // end attribute


    public TipoTransacao() {    }

    public TipoTransacao(@NonNull String descricao) {
        this.descricao = descricao;
    }

    public TipoTransacao(int idTipoTransacao, @NonNull String descricao) {
        this.idTipoTransacao = idTipoTransacao;
        this.descricao = descricao;
    }

    public int getIdTipoTransacao() {
        return idTipoTransacao;
    }

    public void setIdTipoTransacao(int idTipoTransacao) {
        this.idTipoTransacao = idTipoTransacao;
    }

    @NonNull
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(@NonNull String descricao) {
        this.descricao = descricao;
    }
}
