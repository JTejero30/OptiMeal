package com.example.app.recyclerView.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.CardSuperheroBinding
import com.example.app.recyclerView.SuperHero
import android.util.Log
import com.bumptech.glide.Glide


//Esta clase va a recibir una vista
//Tenemos que decir que esta clase extienda de viewHolder, para decirle que va a ser de tipo viewHolder
class SuperHeroViewHolder(view:View):RecyclerView.ViewHolder(view) {

    //Aqui creo el binding, que recibe la vista que recibe la clase
    val binding = CardSuperheroBinding.bind(view)
    //Esta clase recoge los atributos y los pinta
    //Esta funcion se va a llamar por cada uno de los Superheros, para asi pintar la vista


    fun render(superhero: SuperHero){
        binding.tvSuperhero.text=superhero.superhero
        binding.tvRealName.text=superhero.realName
        binding.tvPublisher.text=superhero.publisher
        Glide.with(binding.ivAvatar.context).load(superhero.photo).into(binding.ivAvatar)

    }
}