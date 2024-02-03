package com.example.app.ui.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R

import com.example.app.ui.recipes.RecipeModel

class RecipesAdapter(private val recipesModelList: List<RecipeModel>) :
    RecyclerView.Adapter<RecipesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecipesViewHolder(layoutInflater.inflate(R.layout.card_recipe_recipe, parent, false))
    }

    override fun getItemCount(): Int = recipesModelList.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val item = recipesModelList[position]
        holder.render(item)
    }
}