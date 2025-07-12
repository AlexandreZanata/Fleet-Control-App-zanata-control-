package com.alexandre.controledefrota.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alexandre.controledefrota.model.Veiculo

@Dao
interface VeiculoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(veiculo: Veiculo)

    @Update
    suspend fun atualizar(veiculo: Veiculo)

    @Delete
    suspend fun deletar(veiculo: Veiculo)

    @Query("SELECT * FROM veiculos ORDER BY placa ASC")
    fun obterTodos(): LiveData<List<Veiculo>>

    @Query("SELECT * FROM veiculos WHERE placa = :placa")
    suspend fun obterPorPlaca(placa: String): Veiculo?

    @Query("SELECT * FROM veiculos WHERE motorista LIKE '%' || :motorista || '%'")
    fun buscarPorMotorista(motorista: String): LiveData<List<Veiculo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTodos(veiculos: List<Veiculo>)

    // Nova query para pesquisa inteligente
    @Query("SELECT * FROM veiculos WHERE " +
            "placa LIKE '%' || :query || '%' OR " +
            "tipoVeiculo LIKE '%' || :query || '%' OR " +
            "marca LIKE '%' || :query || '%' OR " +
            "modelo LIKE '%' || :query || '%' OR " +
            "motorista LIKE '%' || :query || '%' OR " +
            "chassi LIKE '%' || :query || '%' OR " +
            "renavan LIKE '%' || :query || '%' " +
            "LIMIT 10")
    suspend fun pesquisaInteligente(query: String): List<Veiculo>
}