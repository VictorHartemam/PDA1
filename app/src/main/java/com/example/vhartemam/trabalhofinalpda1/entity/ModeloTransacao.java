package com.example.vhartemam.trabalhofinalpda1.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "modelo_transacao",
foreignKeys = {@ForeignKey(entity = TipoTransacao.class, parentColumns = "id_tipo_transacao", childColumns = "id_tipo_transacao"),
               @ForeignKey(entity = CategoriaTransacao.class, parentColumns = "id_categoria_transacao", childColumns = "id_categoria_transacao")})
public class ModeloTransacao {

    // attributes
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id_modelo_transacao")
    private int idModeloTransacao;

    @ColumnInfo (name = "id_tipo_transacao")
    private int idTipoTransacao;

    @ColumnInfo (name = "id_categoria_transacao")
    private int idCategoriaTransacao;

    private double valor;
    private String descricao;

    @ColumnInfo (name = "dia_ini")
    private int diaIni;

    @ColumnInfo (name = "mes_ini")
    private int mesIni;

    @ColumnInfo (name = "ano_ini")
    private int anoIni;

    @ColumnInfo (name = "dia_fim")
    private int diaFim;

    @ColumnInfo (name = "mes_fim")
    private int mesFim;

    @ColumnInfo (name = "ano_fim")
    private int anoFim;
    // end attributes


    public ModeloTransacao() {    }

    public ModeloTransacao(int idModeloTransacao, int idTipoTransacao, int idCategoriaTransacao, double valor, String descricao, int diaIni, int mesIni, int anoIni, int diaFim, int mesFim, int anoFim) {
        this.idModeloTransacao = idModeloTransacao;
        this.idTipoTransacao = idTipoTransacao;
        this.idCategoriaTransacao = idCategoriaTransacao;
        this.valor = valor;
        this.descricao = descricao;
        this.diaIni = diaIni;
        this.mesIni = mesIni;
        this.anoIni = anoIni;
        this.diaFim = diaFim;
        this.mesFim = mesFim;
        this.anoFim = anoFim;
    }

    public ModeloTransacao(int idTipoTransacao, int idCategoriaTransacao, double valor, String descricao, int diaIni, int mesIni, int anoIni, int diaFim, int mesFim, int anoFim) {
        this.idTipoTransacao = idTipoTransacao;
        this.idCategoriaTransacao = idCategoriaTransacao;
        this.valor = valor;
        this.descricao = descricao;
        this.diaIni = diaIni;
        this.mesIni = mesIni;
        this.anoIni = anoIni;
        this.diaFim = diaFim;
        this.mesFim = mesFim;
        this.anoFim = anoFim;
    }

    public int getIdModeloTransacao() {
        return idModeloTransacao;
    }

    public void setIdModeloTransacao(int idModeloTransacao) {
        this.idModeloTransacao = idModeloTransacao;
    }

    public int getIdTipoTransacao() {
        return idTipoTransacao;
    }

    public void setIdTipoTransacao(int idTipoTransacao) {
        this.idTipoTransacao = idTipoTransacao;
    }

    public int getIdCategoriaTransacao() {
        return idCategoriaTransacao;
    }

    public void setIdCategoriaTransacao(int idCategoriaTransacao) {
        this.idCategoriaTransacao = idCategoriaTransacao;
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

    public int getDiaIni() {
        return diaIni;
    }

    public void setDiaIni(int diaIni) {
        this.diaIni = diaIni;
    }

    public int getMesIni() {
        return mesIni;
    }

    public void setMesIni(int mesIni) {
        this.mesIni = mesIni;
    }

    public int getAnoIni() {
        return anoIni;
    }

    public void setAnoIni(int anoIni) {
        this.anoIni = anoIni;
    }

    public int getDiaFim() {
        return diaFim;
    }

    public void setDiaFim(int diaFim) {
        this.diaFim = diaFim;
    }

    public int getMesFim() {
        return mesFim;
    }

    public void setMesFim(int mesFim) {
        this.mesFim = mesFim;
    }

    public int getAnoFim() {
        return anoFim;
    }

    public void setAnoFim(int anoFim) {
        this.anoFim = anoFim;
    }
}
