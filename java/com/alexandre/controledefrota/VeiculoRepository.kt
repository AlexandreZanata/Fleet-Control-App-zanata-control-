package com.alexandre.controledefrota.repository

import androidx.lifecycle.LiveData
import com.alexandre.controledefrota.dao.VeiculoDao
import com.alexandre.controledefrota.model.Veiculo

class VeiculoRepository(private val veiculoDao: VeiculoDao) {

    val todosVeiculos: LiveData<List<Veiculo>> = veiculoDao.obterTodos()

    suspend fun inserir(veiculo: Veiculo) {
        veiculoDao.inserir(veiculo)
    }

    suspend fun atualizar(veiculo: Veiculo) {
        veiculoDao.atualizar(veiculo)
    }

    suspend fun deletar(veiculo: Veiculo) {
        veiculoDao.deletar(veiculo)
    }

    suspend fun obterPorPlaca(placa: String): Veiculo? {
        return veiculoDao.obterPorPlaca(placa)
    }

    fun buscarPorMotorista(motorista: String): LiveData<List<Veiculo>> {
        return veiculoDao.buscarPorMotorista(motorista)
    }

    suspend fun importarVeiculos(veiculos: List<Veiculo>) {
        veiculoDao.inserirTodos(veiculos)
    }

    // Nova função para pesquisa inteligente
    suspend fun pesquisaInteligente(query: String): List<Veiculo> {
        return veiculoDao.pesquisaInteligente(query)
    }
}