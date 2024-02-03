package com.example.app.ui.recipes

data class RecipeModel(

    val nombre:String,
    val imagen: String,
    val ingredients: List<String>,
    val macros: List<String>

)
