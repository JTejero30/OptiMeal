package com.example.app.ui.superhero.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.recyclerView.SuperHero


//Esta clase nos permite recoger un simple listado y convertirlo en un recyclerView
//Asi que le pasamos por parametro una lista de superheroes, que nos la dara la clase SuperHeroProvider
//Esta clase va a extender de RecyclerView.Adapter, diciendole la clase de nuestro viewHolder
//Se implementan automaticante los metodos abastractos de RacyclerView

class SuperHeroAdapter(private val superHeroList:List<SuperHero>) : RecyclerView.Adapter<SuperHeroViewHolder>() {

    //Esta funcion devuelve al viewHolder, dicinedo el layout que va a modificar
    //Va a devolver al viewHolder ese item que acabamos de crear al viewHolder por cada objeto que haya de cada superhero
    //Al recyclerView no hay que pasarle nunca el contexto
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        Log.d("ViewHolder", "oncreateViewHolder ejecutado ")

        return SuperHeroViewHolder(layoutInflater.inflate(R.layout.card_superhero,parent,false))
    }

    //Va a par por cada uno de los items y va a llamar al reder de superherViewHolder
    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {
        val item=superHeroList[position]
        holder.render(item)
    }

    //Esta funcion devuelve la cantidad de items de la lista
    override fun getItemCount(): Int = superHeroList.size

}