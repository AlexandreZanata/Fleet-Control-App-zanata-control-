package com.alexandre.controledefrota.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexandre.controledefrota.R
import com.alexandre.controledefrota.model.Historico
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class HistoricoAdapter(
    private val onEditClick: (Historico) -> Unit,
    private val onDeleteClick: (Historico) -> Unit
) : ListAdapter<Historico, HistoricoAdapter.HistoricoViewHolder>(HistoricoDiffCallback()) {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    private val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historico, parent, false)
        return HistoricoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoricoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtTipoServico: TextView = itemView.findViewById(R.id.txtTipoServico)
        private val txtData: TextView = itemView.findViewById(R.id.txtData)
        private val txtQuilometragem: TextView = itemView.findViewById(R.id.txtQuilometragem)
        private val txtCusto: TextView = itemView.findViewById(R.id.txtCusto)
        private val txtComentario: TextView = itemView.findViewById(R.id.txtComentario)
        private val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditarHistorico)
        private val btnExcluir: ImageButton = itemView.findViewById(R.id.btnExcluirHistorico)

        fun bind(historico: Historico) {
            txtTipoServico.text = historico.tipoServico
            txtData.text = "Data: ${dateFormat.format(historico.data)}"
            txtQuilometragem.text = "Km: ${historico.quilometragem}"
            txtCusto.text = "Custo: ${numberFormat.format(historico.custoServico)}"

            if (historico.comentario.isNotEmpty()) {
                txtComentario.visibility = View.VISIBLE
                txtComentario.text = historico.comentario
            } else {
                txtComentario.visibility = View.GONE
            }

            btnEditar.setOnClickListener { onEditClick(historico) }
            btnExcluir.setOnClickListener { onDeleteClick(historico) }
        }
    }

    class HistoricoDiffCallback : DiffUtil.ItemCallback<Historico>() {
        override fun areItemsTheSame(oldItem: Historico, newItem: Historico): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Historico, newItem: Historico): Boolean {
            return oldItem == newItem
        }
    }
}