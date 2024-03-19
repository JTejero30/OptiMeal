package com.example.app.ui.main.model

import com.example.app.model.IngredienteNutri
import com.example.app.model.InstruccionesNutri
import com.example.app.model.PlatoNutri

data class MenuModel(
    val menu_del_dia: MenuDelDia
)

data class MenuDelDia(
    val desayuno: Plato,
    val comida: Plato,
    val cena: Plato
)

data class Plato(
    val plato: String?,
    val ingredientes: List<Ingrediente>?,
    val contenido: String?,
    val instrucciones: List<Instruccion>?,
    val total_grasa: Double?,
    val total_proteina: Double?,
    val total_carbohidratos: Double?,
    val total_kilocalorias: Int?,
    val imagen: String?,
)

data class Ingrediente(
    val ingredientes: String?
)

data class Instruccion(
    val instrucciones: String?
)

/*data class Ingrediente(
    val nombre: String,
    val cantidad: String,
    *//*  val calorias: Double,
      val tipo: String,
      val gramos: Double*//*
)*/
/*
data class PlatoNutri(
    val plato: String?,
    val ingredientes: List<IngredienteNutri>?,
    val contenido: String?,
    val instrucciones: List<InstruccionesNutri>?,
    val total_grasa: Double?,
    val total_proteina: Double?,
    val total_carbohidratos: Double?,
    val total_kilocalorias: Int?,
    val imagen: String?,
)

data class IngredienteNutri(
    val ingredientes: String?,
    *//*  val calorias: Double,
      val tipo: String,
      val gramos: Double*//*
)*/

