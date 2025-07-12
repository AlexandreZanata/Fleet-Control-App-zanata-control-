package com.alexandre.controledefrota.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alexandre.controledefrota.model.Historico
import java.util.Date

@Dao
interface HistoricoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(historico: Historico): Long

    @Update
    suspend fun atualizar(historico: Historico)

    @Delete
    suspend fun deletar(historico: Historico)

    @Query("SELECT * FROM historicos WHERE id = :id")
    suspend fun obterPorId(id: Long): Historico?

    @Query("SELECT * FROM historicos WHERE placaVeiculo = :placa ORDER BY data DESC")
    fun obterHistoricoVeiculo(placa: String): LiveData<List<Historico>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTodos(historicos: List<Historico>)

    @Query("SELECT * FROM historicos ORDER BY placaVeiculo, data DESC")
    fun obterTodos(): LiveData<List<Historico>>
}