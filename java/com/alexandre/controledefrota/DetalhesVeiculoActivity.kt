package com.alexandre.controledefrota

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandre.controledefrota.adapter.HistoricoAdapter
import com.alexandre.controledefrota.databinding.ActivityDetalhesVeiculoBinding
import com.alexandre.controledefrota.model.Veiculo
import com.alexandre.controledefrota.viewmodel.HistoricoViewModel
import com.alexandre.controledefrota.viewmodel.VeiculoViewModel
import kotlinx.coroutines.launch


class DetalhesVeiculoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesVeiculoBinding
    private lateinit var veiculoViewModel: VeiculoViewModel
    private lateinit var historicoViewModel: HistoricoViewModel
    private lateinit var historicoAdapter: HistoricoAdapter

    private var placaAtual: String? = null

    companion object {
        const val EXTRA_PLACA = "extra_placa"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesVeiculoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        placaAtual = intent.getStringExtra(EXTRA_PLACA)
        if (placaAtual == null) {
            finish()
            return
        }

        setupViewModels()
        setupRecyclerView()
        setupListeners()
        carregarDadosVeiculo()
    }

    private fun setupViewModels() {
        veiculoViewModel = ViewModelProvider(this)[VeiculoViewModel::class.java]
        historicoViewModel = ViewModelProvider(this)[HistoricoViewModel::class.java]

        placaAtual?.let { placa ->
            historicoViewModel.obterHistoricoVeiculo(placa).observe(this) { historicos ->
                historicoAdapter.submitList(historicos)
                if (historicos.isEmpty()) {
                    binding.txtHistoricoVazio.visibility = View.VISIBLE
                    binding.recyclerViewHistorico.visibility = View.GONE
                } else {
                    binding.txtHistoricoVazio.visibility = View.GONE
                    binding.recyclerViewHistorico.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupRecyclerView() {
        historicoAdapter = HistoricoAdapter(
            onEditClick = { historico ->
                val intent = Intent(this, AdicionarEditarHistoricoActivity::class.java)
                intent.putExtra(AdicionarEditarHistoricoActivity.EXTRA_HISTORICO_ID, historico.id)
                intent.putExtra(AdicionarEditarHistoricoActivity.EXTRA_PLACA, historico.placaVeiculo)
                startActivity(intent)
            },
            onDeleteClick = { historico ->
                historicoViewModel.deletar(historico)
            }
        )
        binding.recyclerViewHistorico.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistorico.adapter = historicoAdapter
    }

    private fun setupListeners() {
        // Adicione este listener para o novo botão de edição
        binding.btnEditarVeiculo.setOnClickListener {
            abrirTelaEdicao()
        }

        // Mantenha o listener existente para o FAB
        binding.fabEditarVeiculo.setOnClickListener {
            abrirTelaEdicao()
        }

        binding.btnAdicionarHistorico.setOnClickListener {
            val intent = Intent(this, AdicionarEditarHistoricoActivity::class.java)
            intent.putExtra(AdicionarEditarHistoricoActivity.EXTRA_PLACA, placaAtual)
            startActivity(intent)
        }
    }

    // Método para evitar duplicação de código
    private fun abrirTelaEdicao() {
        val intent = Intent(this, AdicionarEditarVeiculoActivity::class.java)
        intent.putExtra(AdicionarEditarVeiculoActivity.EXTRA_PLACA, placaAtual)
        startActivity(intent)
    }

    private fun carregarDadosVeiculo() {
        lifecycleScope.launch {
            placaAtual?.let { placa ->
                val veiculo = veiculoViewModel.obterPorPlaca(placa)
                veiculo?.let { preencherDetalhes(it) }
            }
        }
    }

    private fun preencherDetalhes(veiculo: Veiculo) {
        binding.apply {
            txtPlaca.text = veiculo.placa
            txtVeiculoInfo.text = "${veiculo.marca} ${veiculo.modelo} (${veiculo.ano})"
            txtMotoristaInfo.text = "Motorista: ${veiculo.motorista}"
            txtValorInfo.text = "Valor: R$ ${veiculo.valor}"
            txtOnusInfo.text = "Ônus: ${veiculo.onus}"
            txtChassi.text = veiculo.chassi
            txtRenavan.text = veiculo.renavan
            txtCep.text = veiculo.cep
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        carregarDadosVeiculo() // Recarregar dados quando voltar da tela de edição
    }
}
