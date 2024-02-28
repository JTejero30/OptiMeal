package com.example.app.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.ui.main.model.MenuModel

class MenuAdapter(private val menuModelList: List<MenuModel>) :
    RecyclerView.Adapter<MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MenuViewHolder(layoutInflater.inflate(R.layout.card_menu, parent, false))
    }

    override fun getItemCount(): Int = menuModelList.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuModelList[position]
        holder.render(item)
    }
}