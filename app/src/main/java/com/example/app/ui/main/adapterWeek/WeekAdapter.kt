package com.example.app.ui.main.adapterWeek

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.ui.main.model.DayModel

class WeekAdapter(private val weekModelList: List<DayModel>) :
    RecyclerView.Adapter<WeekViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WeekViewHolder(layoutInflater.inflate(R.layout.card_day, parent, false))
    }

    override fun getItemCount(): Int = weekModelList.size

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val item = weekModelList[position]
        holder.render(item)
    }




}