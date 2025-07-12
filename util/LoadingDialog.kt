package com.alexandre.controledefrota.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import com.alexandre.controledefrota.R

class LoadingDialog(private val context: Context) {

    private var dialog: Dialog? = null
    private var txtMensagem: TextView? = null

    fun mostrar(mensagem: String = "Carregando...") {
        if (dialog == null) {
            dialog = Dialog(context)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCancelable(false)

            val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
            txtMensagem = view.findViewById(R.id.txtMensagem)

            dialog?.setContentView(view)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        txtMensagem?.text = mensagem
        dialog?.show()
    }

    fun esconder() {
        dialog?.dismiss()
    }

    fun atualizar(mensagem: String) {
        txtMensagem?.text = mensagem
    }
}