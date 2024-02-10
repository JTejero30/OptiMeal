package com.example.app.model

data class Desayuno(
    val plato: String,
    val ingredientes: List<Ingrediente>,
    val total_grasa: Int,
    val total_proteina: Int,
    val total_carbohidratos: Int


)

data class Ingrediente(
    val nombre: String,
    val cantidad: String,
    val calorias: Double,
    val tipo: String,
    val gramos: Int
)
