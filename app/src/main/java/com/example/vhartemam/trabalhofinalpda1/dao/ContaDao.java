package com.example.vhartemam.trabalhofinalpda1.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.vhartemam.trabalhofinalpda1.entity.Conta;
import java.util.List;

@Dao
public interface ContaDao {

    @Insert
    void insert(Conta... conta);

    @Update
    void update(Conta... conta);

    @Delete
    void delete(Conta... conta);

    @Query("delete from conta")
    void deleteNoWhere();

    @Query("select * from conta")
    List<Conta> select();

    @Query("select * from conta where id_conta = :idConta")
    List<Conta> select(int idConta);

    @Query("select * from conta where descricao = :descricao")
    List<Conta> select(String descricao);

    @Query("select * from conta where id_conta = (select max(id_conta) from conta)")
    Conta selectLastId();

    @Query("select sum(mt.valor) from transacao t, modelo_transacao mt where t.id_modelo_transacao = mt.id_modelo_transacao and t.id_conta = :idConta")
    double selectSaldo(int idConta);

    @Query("select sum(mt.valor) from transacao t, modelo_transacao mt where t.id_modelo_transacao = mt.id_modelo_transacao")
    double selectSaldoTotal();
}
