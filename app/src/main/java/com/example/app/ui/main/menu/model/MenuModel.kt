package com.example.app.ui.main.menu.model

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
    val total_grasa: Int,
    val total_proteina: Int,
    val total_carbohidratos: Int,
    val imagen: String

    )

data class Ingrediente(
    val nombre: String,
    val cantidad: String,
    val calorias: Double,
    val tipo: String,
    val gramos: Int
)


