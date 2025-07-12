package com.alexandre.controledefrota.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "veiculos")
data class Veiculo(
    @PrimaryKey(autoGenerate = false)
    val placa: String,
    val tipoVeiculo: String,
    val marca: String,
    val modelo: String,
    val ano: String,
    val onus: String,
    val valor: String,
    val chassi: String,
    val renavan: String,
    val motorista: String,
    val cep: String
)