package com.example.app.model

data class PlatoNutri(
    val plato: String?,
    val ingredientes: List<IngredienteNutri>?,
    val contenido: String?,
    val instrucciones: List<InstruccionesNutri>?,
    val total_grasa: Double?,
    val total_proteina: Double?,
    val total_carbohidratos: Double?,
    val total_kilocalorias: Int?,
    val tipo: Int?,
    val dieta: Int?,
    val imagen: String?,
)

data class IngredienteNutri(
    val ingredientes: String?
)

data class InstruccionesNutri(
    val instrucciones: String?
)