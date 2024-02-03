package com.example.app.ui.home.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.databinding.CardViewDefaultBinding
import com.example.app.ui.home.IngredientModel


//Esta clase va a recibir una vista
//Tenemos que decir que esta clase extienda de viewHolder, para decirle que va a ser de tipo viewHolder
class HomeViewHolder(view:View):RecyclerView.ViewHolder(view) {

    //Aqui creo el binding, que recibe la vista que recibe la clase
    val binding = CardViewDefaultBinding.bind(view)

    //Esta clase recoge los atributos y los pinta
    //Esta funcion se va a llamar por cada uno de los Superheros, para asi pintar la vista
    fun render(ingredientModel: IngredientModel){

        Log.d("Comprobar","HomeViewHolder Render")
        binding.nombreCard.text=ingredientModel.name
        binding.listaIngredientesCard.text=ingredientModel.proteinas
        binding.grasasCard.text=ingredientModel.grasas

        binding.hidratosDeCarbonoCard.text=ingredientModel.hidratos_de_carbono
        binding.caloriasCard.text=ingredientModel.calorias

    }
}