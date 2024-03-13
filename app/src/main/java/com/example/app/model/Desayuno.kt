package com.example.app.model

data class PlatoBD(
    val plato: String,
    val ingredientes: List<Ingrediente>?,
    val total_grasa:Double,
    val total_proteina: Double,
    val total_carbohidratos: Double,
    val total_kilocalorias: Double,

    val imagen:String,


    )

data class Ingrediente(
    val nombre: String,
    val cantidad: String,
    val calorias: Double,
    val tipo: String,
    val gramos: Double
)
