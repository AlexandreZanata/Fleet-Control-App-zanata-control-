package com.alexandre.controledefrota.repository

import androidx.lifecycle.LiveData
import com.alexandre.controledefrota.dao.HistoricoDao
import com.alexandre.controledefrota.model.Historico

class HistoricoRepository(private val historicoDao: HistoricoDao) {

    fun obterHistoricoVeiculo(placa: String): LiveData<List<Historico>> {
        return historicoDao.obterHistoricoVeiculo(placa)
    }

    fun obterTodos(): LiveData<List<Historico>> {
        return historicoDao.obterTodos()
    }

    suspend fun inserir(historico: Historico): Long {
        return historicoDao.inserir(historico)
    }

    suspend fun atualizar(historico: Historico) {
        historicoDao.atualizar(historico)
    }

    suspend fun deletar(historico: Historico) {
        historicoDao.deletar(historico)
    }

    suspend fun obterPorId(id: Long): Historico? {
        return historicoDao.obterPorId(id)
    }

    suspend fun importarHistoricos(historicos: List<Historico>) {
        historicoDao.inserirTodos(historicos)
    }
}