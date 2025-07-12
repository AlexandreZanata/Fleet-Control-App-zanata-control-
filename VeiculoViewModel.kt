package com.alexandre.controledefrota.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexandre.controledefrota.database.AppDatabase  // Certifique-se que este import está presente
import com.alexandre.controledefrota.model.Veiculo
import com.alexandre.controledefrota.repository.VeiculoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VeiculoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VeiculoRepository
    val todosVeiculos: LiveData<List<Veiculo>>

    // LiveData para os resultados da pesquisa
    private val _resultadosPesquisa = MutableLiveData<List<Veiculo>>()
    val resultadosPesquisa: LiveData<List<Veiculo>> = _resultadosPesquisa

    init {
        val veiculoDao = AppDatabase.getDatabase(application).veiculoDao()
        repository = VeiculoRepository(veiculoDao)
        todosVeiculos = repository.todosVeiculos
    }

    fun inserir(veiculo: Veiculo) = viewModelScope.launch(Dispatchers.IO) {
        repository.inserir(veiculo)
    }

    fun atualizar(veiculo: Veiculo) = viewModelScope.launch(Dispatchers.IO) {
        repository.atualizar(veiculo)
    }

    fun deletar(veiculo: Veiculo) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletar(veiculo)
    }

    suspend fun obterPorPlaca(placa: String): Veiculo? {
        return repository.obterPorPlaca(placa)
    }

    fun buscarPorMotorista(motorista: String): LiveData<List<Veiculo>> {
        return repository.buscarPorMotorista(motorista)
    }

    fun importarVeiculos(veiculos: List<Veiculo>) = viewModelScope.launch(Dispatchers.IO) {
        repository.importarVeiculos(veiculos)
    }

    // Nova função para pesquisa inteligente
    fun pesquisar(query: String) {
        if (query.isEmpty()) {
            _resultadosPesquisa.value = emptyList()
            return
        }

        viewModelScope.launch {
            val resultados = withContext(Dispatchers.IO) {
                repository.pesquisaInteligente(query)
            }
            _resultadosPesquisa.value = resultados
        }
    }
}