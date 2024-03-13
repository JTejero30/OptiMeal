package com.example.app.model


data class PlatoWetaca(
    val plato: String,
    val ingredientes: List<IngredienteWetaca>?,
    val contenido:String,
    val instrucciones:String,
    val total_gramos:Double,
    val total_grasa: Double,
    val total_proteina: Double,
    val total_carbohidratos: Double,
    val total_kilocalorias: Double,
    val imagen: String,

    )

data class IngredienteWetaca(
    val nombre: String,
    val cantidad: String
)
