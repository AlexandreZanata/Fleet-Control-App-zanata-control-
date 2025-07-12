package com.alexandre.controledefrota.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.alexandre.controledefrota.database.AppDatabase
import com.alexandre.controledefrota.model.Historico
import com.alexandre.controledefrota.repository.HistoricoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoricoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HistoricoRepository
    val todosHistoricos: LiveData<List<Historico>>

    init {
        val historicoDao = AppDatabase.getDatabase(application).historicoDao()
        repository = HistoricoRepository(historicoDao)
        todosHistoricos = repository.obterTodos()

        // Log para debug
        android.util.Log.d("HistoricoViewModel", "ViewModel inicializado")
    }

    fun obterHistoricoVeiculo(placa: String): LiveData<List<Historico>> {
        return repository.obterHistoricoVeiculo(placa)
    }

    fun inserir(historico: Historico) = viewModelScope.launch(Dispatchers.IO) {
        repository.inserir(historico)
    }

    fun atualizar(historico: Historico) = viewModelScope.launch(Dispatchers.IO) {
        repository.atualizar(historico)
    }

    fun deletar(historico: Historico) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletar(historico)
    }

    suspend fun obterPorId(id: Long): Historico? {
        return repository.obterPorId(id)
    }

    fun importarHistoricos(historicos: List<Historico>) = viewModelScope.launch(Dispatchers.IO) {
        android.util.Log.d("HistoricoViewModel", "Importando ${historicos.size} históricos")
        repository.importarHistoricos(historicos)
    }
}