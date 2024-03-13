package com.example.app.ui.main.model

data class MenuModel(
    val menu_del_dia: MenuDelDia
)
data class MenuDelDia(
    val desayuno: Plato,
    val comida: Plato,
    val cena: Plato
)

data class Plato(
    val plato: String,
    val ingredientes: List<Ingrediente>,
    val total_grasa: Double,
    val total_proteina: Double,
    val total_carbohidratos: Double,



    val imagen: String,
    val intrucciones: String


    )

data class Ingrediente(
    val nombre: String,
    val cantidad: String,
  /*  val calorias: Double,
    val tipo: String,
    val gramos: Double*/
)


