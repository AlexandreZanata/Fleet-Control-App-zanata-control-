package com.alexandre.controledefrota

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexandre.controledefrota.databinding.ActivityAdicionarEditarVeiculoBinding
import com.alexandre.controledefrota.model.Veiculo
import com.alexandre.controledefrota.viewmodel.VeiculoViewModel
import kotlinx.coroutines.launch

class AdicionarEditarVeiculoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdicionarEditarVeiculoBinding
    private lateinit var veiculoViewModel: VeiculoViewModel

    private var placaAtual: String? = null
    private var veiculoAtual: Veiculo? = null
    private var editando = false

    companion object {
        const val EXTRA_PLACA = "extra_placa"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarEditarVeiculoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        veiculoViewModel = ViewModelProvider(this)[VeiculoViewModel::class.java]

        placaAtual = intent.getStringExtra(EXTRA_PLACA)
        editando = placaAtual != null

        if (editando) {
            title = "Editar Veículo"
            carregarDadosVeiculo()
        } else {
            title = "Adicionar Veículo"
        }

        binding.btnSalvar.setOnClickListener {
            salvarVeiculo()
        }
    }

    private fun carregarDadosVeiculo() {
        lifecycleScope.launch {
            placaAtual?.let { placa ->
                veiculoAtual = veiculoViewModel.obterPorPlaca(placa)
                veiculoAtual?.let { preencherCampos(it) }
            }
        }
    }

    private fun preencherCampos(veiculo: Veiculo) {
        binding.apply {
            edtPlaca.setText(veiculo.placa)
            // Removido o código que desabilitava a edição da placa
            // edtPlaca.isEnabled = false
            edtTipoVeiculo.setText(veiculo.tipoVeiculo)
            edtMarca.setText(veiculo.marca)
            edtModelo.setText(veiculo.modelo)
            edtAno.setText(veiculo.ano)
            edtOnus.setText(veiculo.onus)
            edtValor.setText(veiculo.valor)
            edtChassi.setText(veiculo.chassi)
            edtRenavan.setText(veiculo.renavan)
            edtMotorista.setText(veiculo.motorista)
            edtCep.setText(veiculo.cep)
        }
    }

    private fun salvarVeiculo() {
        val placa = binding.edtPlaca.text.toString().trim()
        val tipoVeiculo = binding.edtTipoVeiculo.text.toString().trim()
        val marca = binding.edtMarca.text.toString().trim()
        val modelo = binding.edtModelo.text.toString().trim()
        val ano = binding.edtAno.text.toString().trim()
        val onus = binding.edtOnus.text.toString().trim()
        val valor = binding.edtValor.text.toString().trim()
        val chassi = binding.edtChassi.text.toString().trim()
        val renavan = binding.edtRenavan.text.toString().trim()
        val motorista = binding.edtMotorista.text.toString().trim()
        val cep = binding.edtCep.text.toString().trim()

        if (placa.isEmpty() || tipoVeiculo.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        val veiculo = Veiculo(
            placa = placa,
            tipoVeiculo = tipoVeiculo,
            marca = marca,
            modelo = modelo,
            ano = ano,
            onus = onus,
            valor = valor,
            chassi = chassi,
            renavan = renavan,
            motorista = motorista,
            cep = cep
        )

        if (editando) {
            // Se a placa foi alterada, precisamos excluir o veículo antigo e inserir um novo
            lifecycleScope.launch {
                if (placaAtual != placa && veiculoAtual != null) {
                    // Exclui o veículo antigo
                    veiculoViewModel.deletar(veiculoAtual!!)
                    // Insere o novo com a placa atualizada
                    veiculoViewModel.inserir(veiculo)
                } else {
                    // Atualiza normalmente se a placa não foi alterada
                    veiculoViewModel.atualizar(veiculo)
                }

                Toast.makeText(this@AdicionarEditarVeiculoActivity,
                    "Veículo atualizado com sucesso",
                    Toast.LENGTH_SHORT).show()

                finish()
            }
        } else {
            // Insere um novo veículo
            veiculoViewModel.inserir(veiculo)
            Toast.makeText(this, "Veículo adicionado com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // Adicionando opções de menu para incluir a exclusão
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Somente mostra a opção de excluir se estiver editando um veículo existente
        if (editando) {
            menuInflater.inflate(R.menu.menu_editar_veiculo, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_excluir_veiculo -> {
                confirmarExclusao()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun confirmarExclusao() {
        AlertDialog.Builder(this)
            .setTitle("Excluir Veículo")
            .setMessage("Você deseja excluir esse carro?")
            .setPositiveButton("Sim") { _, _ ->
                excluirVeiculo()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun excluirVeiculo() {
        veiculoAtual?.let { veiculo ->
            lifecycleScope.launch {
                veiculoViewModel.deletar(veiculo)
                Toast.makeText(this@AdicionarEditarVeiculoActivity,
                    "Veículo excluído com sucesso",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}