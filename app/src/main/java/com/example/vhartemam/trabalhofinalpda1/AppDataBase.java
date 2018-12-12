package com.example.vhartemam.trabalhofinalpda1;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.vhartemam.trabalhofinalpda1.dao.CategoriaTransacaoDao;
import com.example.vhartemam.trabalhofinalpda1.dao.ContaDao;
import com.example.vhartemam.trabalhofinalpda1.dao.ModeloTransacaoDao;
import com.example.vhartemam.trabalhofinalpda1.dao.TipoTransacaoDao;
import com.example.vhartemam.trabalhofinalpda1.dao.TransacaoDao;
import com.example.vhartemam.trabalhofinalpda1.entity.CategoriaTransacao;
import com.example.vhartemam.trabalhofinalpda1.entity.Conta;
import com.example.vhartemam.trabalhofinalpda1.entity.ModeloTransacao;
import com.example.vhartemam.trabalhofinalpda1.entity.TipoTransacao;
import com.example.vhartemam.trabalhofinalpda1.entity.Transacao;

@Database(entities = {CategoriaTransacao.class, Conta.class, ModeloTransacao.class, TipoTransacao.class, Transacao.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public static final String DB_NAME = "DB_PDA1_FINAL";

    public abstract CategoriaTransacaoDao categoriaTransacaoDao();

    public abstract ContaDao contaDao();

    public abstract ModeloTransacaoDao modeloTransacaoDao();

    public abstract TipoTransacaoDao tipoTransacaoDao();

    public abstract TransacaoDao transacaoDao();
}
