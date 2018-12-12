package com.example.vhartemam.trabalhofinalpda1.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vhartemam.trabalhofinalpda1.entity.ModeloTransacao;

import java.util.List;

@Dao
public interface ModeloTransacaoDao {

    @Insert
    void insert(ModeloTransacao... modeloTransacaos);

    @Update
    void update(ModeloTransacao... modeloTransacaos);

    @Delete
    void delete(ModeloTransacao... modeloTransacaos);

    @Query("delete from modelo_transacao")
    void deleteNoWhere();

    @Query("select * from modelo_transacao")
    List<ModeloTransacao> select();


    @Query("select * from modelo_transacao where id_modelo_transacao = :idModeloTransacao")
    List<ModeloTransacao> selectByIdModeloTransacao(int idModeloTransacao);

    @Query("select * from modelo_transacao where id_tipo_transacao = :idTipoTransacao")
    List<ModeloTransacao> selectByIdTipoTransacao(int idTipoTransacao);

    @Query("select * from modelo_transacao where id_categoria_transacao = :idCategoriaTransacao")
    List<ModeloTransacao> selectByIdCategoriaTransacao(int idCategoriaTransacao);

    @Query("select * from modelo_transacao where id_tipo_transacao = :idTipoTransacao and id_categoria_transacao = :idCategoriaTransacao")
    List<ModeloTransacao> seelctByAllForeignKeys(int idTipoTransacao, int idCategoriaTransacao);

    @Query("select * from modelo_transacao where id_modelo_transacao = (select max(id_modelo_transacao) from modelo_transacao)")
    ModeloTransacao selectLastID();
}
