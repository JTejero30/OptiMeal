package com.example.app.ui.main.adapterWeek

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.register.RegisterActivity
import com.example.app.ui.main.DayItemClickI
import com.example.app.ui.main.MenuFragment
import com.example.app.ui.main.MenuViewModel
import com.example.app.ui.main.model.DayModel

class WeekAdapter(private val weekModelList: List<DayModel>, private val listener:DayItemClickI) :
    RecyclerView.Adapter<WeekViewHolder>() {
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WeekViewHolder(layoutInflater.inflate(R.layout.card_day, parent, false))
    }


    //El metodo onBindViewHolder sirve para bindear cada data a su item correspondiente, con holder.render, llamando asi al metodo render del ViewHolder
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val item = weekModelList[position]

        Log.d("WeekViewHolder", "day : ${ weekModelList[position]}")


        //La variable isSelected, se guarda para tod0 el adapter, lo que quiere decir que se quedara hasta que se cambie en el onclick
        //por defecto es -1
        val isSelected = selectedPosition == position
        Log.d("WeekViewHolder", "isselecctedf : ${selectedPosition}")

        holder.render(item, isSelected)

        //En cada item, le asignamos un clickListener
        holder.itemView.setOnClickListener {

            //Cuando un item se pulse, se ejecutará esta funcion que se encargara de cambiar la posicion del item seleccionado
            //Guarda la posicion antigua
            val previouslySelectedPosition = selectedPosition
            Log.d("WeekViewHolder", "previusd : ${selectedPosition}")

            //Con el holder.adapterPosition, estamos cogiendo la posicion del item que ha sido clicado dentro del recycler, y estamos cambiando la variable selectedPosition
            //Que se comprobara el el isSelected de arriba
            selectedPosition = holder.adapterPosition


            listener.onDayItemClicked(item)

            //Aqui estamos llamando al notifyItemChanged con la posicion antigua y la nueva posicion seleccionada
            //El notifyItemChanged, lo que hace es ejecutar nuevamente el metodo onBindViewHolder de los items indicados en el notify,
            // para que asi tenga las nuevas posiciones actualizadas.
            notifyItemChanged(previouslySelectedPosition)
            notifyItemChanged(selectedPosition)


            Log.d("WeekViewHolder", "new : ${selectedPosition}")

        }
    }

    override fun getItemCount(): Int = weekModelList.size
}