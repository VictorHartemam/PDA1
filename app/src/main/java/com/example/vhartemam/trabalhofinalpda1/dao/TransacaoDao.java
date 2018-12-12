package com.example.vhartemam.trabalhofinalpda1.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vhartemam.trabalhofinalpda1.entity.Transacao;
import com.example.vhartemam.trabalhofinalpda1.ui.TransacaoModeloTransacaoUi;

import java.util.List;

@Dao
public interface TransacaoDao {

    @Insert
    void insert(Transacao... transacaos);

    @Update
    void update(Transacao... transacaos);

    @Delete
    void delete(Transacao... transacaos);

    @Query("delete from transacao")
    void deleteNoWhere();

    @Query("select * from transacao")
    List<Transacao> select();

    @Query("select mt.descricao, mt.valor from transacao t, modelo_transacao mt where t.id_modelo_transacao = mt.id_modelo_transacao and t.id_conta = :idConta order by mt.ano_ini desc, mt.mes_ini desc, mt.dia_ini desc, t.id_transacao desc ")
    List<TransacaoModeloTransacaoUi> selectByIdConta(int idConta);

    @Query("select mt.descricao, mt.valor from transacao t, modelo_transacao mt where t.id_modelo_transacao = mt.id_modelo_transacao and t.id_conta = :idConta order by mt.ano_ini desc, mt.mes_ini desc, mt.dia_ini desc, t.id_transacao desc limit 3")
    List<TransacaoModeloTransacaoUi> selectLastThree(int idConta);

    @Query("select mt.descricao, mt.valor from transacao t, modelo_transacao mt where t.id_modelo_transacao = mt.id_modelo_transacao and t.id_conta = :idConta and mt.id_tipo_transacao = :idTipoTransacao order by mt.ano_ini desc, mt.mes_ini desc, mt.dia_ini desc, t.id_transacao desc ")
    List<TransacaoModeloTransacaoUi> selectDebit(int idConta, int idTipoTransacao);

    @Query("select mt.descricao, mt.valor " +
            "from transacao t, modelo_transacao mt \n" +
            "where t.id_modelo_transacao = mt.id_modelo_transacao \n" +
            "and t.id_conta = :idConta \n" +
            "and mt.ano_ini >= :anoIni and mt.ano_ini <= :anoFim \n" +
            "and mt.mes_ini >= :mesIni and mt.mes_ini <= :mesFim \n" +
            "and mt.dia_ini >= :diaIni and mt.dia_ini <= :diaFim \n" +
            "order by mt.ano_ini desc, mt.mes_ini desc, mt.dia_ini desc, t.id_transacao desc")
    List<TransacaoModeloTransacaoUi> selectByPeriod(int idConta, int anoIni, int mesIni, int diaIni, int anoFim, int mesFim, int diaFim);

    @Query("select mt.descricao, mt.valor from transacao t, modelo_transacao mt where t.id_modelo_transacao = mt.id_modelo_transacao and t.id_conta = :idConta and mt.id_categoria_transacao = :idCategoria order by mt.ano_ini desc, mt.mes_ini desc, mt.dia_ini desc, t.id_transacao desc ")
    List<TransacaoModeloTransacaoUi> selectByCategoria(int idConta, int idCategoria);
}

