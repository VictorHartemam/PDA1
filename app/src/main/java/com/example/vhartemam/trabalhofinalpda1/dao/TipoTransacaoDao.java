package com.example.vhartemam.trabalhofinalpda1.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vhartemam.trabalhofinalpda1.entity.TipoTransacao;

import java.util.List;

@Dao
public interface TipoTransacaoDao {

    @Insert
    void insert(TipoTransacao... tipoTransacaos);

    @Update
    void update(TipoTransacao... tipoTransacaos);

    @Delete
    void delete(TipoTransacao... tipoTransacaos);

    @Query("delete from tipo_transacao")
    void deleteNoWhere();

    @Query("select * from tipo_transacao")
    List<TipoTransacao> select();

    @Query("select * from tipo_transacao where descricao = :descricao")
    TipoTransacao selectOneDesc(String descricao);
}
