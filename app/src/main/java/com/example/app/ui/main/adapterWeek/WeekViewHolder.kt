package com.example.app.ui.main.adapterWeek

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.databinding.CardDayBinding
import com.example.app.databinding.CardMenuBinding
import com.example.app.ui.main.model.DayModel
import com.example.app.ui.main.model.MenuModel

class WeekViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = CardDayBinding.bind(view)
    fun render(dayModel: DayModel) {

        binding.calendarDay.text = dayModel.dayOfWeek

        binding.calendarMonth.text = dayModel.calendarMonth

        binding.calendarDayOfWeek.text = dayModel.calendarDayOfWeek.toString()


    }
}