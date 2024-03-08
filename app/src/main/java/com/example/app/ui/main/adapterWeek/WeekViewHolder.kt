package com.example.app.ui.main.adapterWeek

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.databinding.CardDayBinding
import com.example.app.databinding.CardMenuBinding
import com.example.app.ui.main.model.DayModel
import com.example.app.ui.main.model.MenuModel
import java.time.LocalDate

class WeekViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = CardDayBinding.bind(view)

    //Con el init le estoy dando el onclICk listener cuando se crea el item
    init {
        view.setOnClickListener {
            Toast.makeText(itemView.context, binding.calendarDay.text, Toast.LENGTH_SHORT).show()
            Log.d("WeekViewHolder", "Clicked : ${binding.calendarDay.text}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun render(dayModel: DayModel,isSelected: Boolean) {
        binding.calendarDay.text = dayModel.dayOfWeek
        binding.calendarMonth.text = dayModel.calendarMonth
        binding.calendarDayOfWeek.text = dayModel.calendarDayOfWeek.toString()

        if (isSelected) {
            itemView.setBackgroundResource(R.drawable.item_underline_background)

        }else{
            itemView.setBackgroundColor(Color.TRANSPARENT)
        }
        if(dayModel.isCurrentDay){
            itemView.setBackgroundColor(Color.GRAY)
        }
    }
}