package com.example.vhartemam.trabalhofinalpda1.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vhartemam.trabalhofinalpda1.entity.CategoriaTransacao;

import java.util.List;

@Dao
public interface CategoriaTransacaoDao {

    @Insert
    void insert(CategoriaTransacao... categoriaTransacaos);

    @Update
    void update(CategoriaTransacao... categoriaTransacaos);

    @Delete
    void delete(CategoriaTransacao... categoriaTransacaos);

    @Query("delete from categoria_transacao")
    void deleteNoWhere();

    @Query("select * from categoria_transacao")
    List<CategoriaTransacao> select();

    @Query("select * from categoria_transacao where id_categoria_transacao = :idCategoriaTransacao")
    List<CategoriaTransacao> select(int idCategoriaTransacao);

    @Query("select * from categoria_transacao where descricao = :descricao")
    List<CategoriaTransacao> select(String descricao);

    @Query("select * from categoria_transacao where descricao = :descricao limit 1")
    CategoriaTransacao selectOneDesc(String descricao);

}
