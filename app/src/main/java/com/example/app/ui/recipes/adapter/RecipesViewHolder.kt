package com.example.app.ui.recipes.adapter

import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.databinding.CardRecipeRecipeBinding
import com.example.app.ui.recipes.RecipeModel

class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = CardRecipeRecipeBinding.bind(view)
    fun render(recipeModel: RecipeModel) {
        binding.nombreCard.text=recipeModel.nombre

        for (ingrediente in recipeModel.ingredients) {
            val textView = TextView(binding.listaIngredientesCard.context)
            textView.text = ingrediente.toString()
            binding.listaIngredientesCard.addView(textView)
        }



        for (macro in recipeModel.macros) {
            val textView = TextView(binding.listaMacrosCard.context)
            textView.text = macro.toString()
            binding.listaMacrosCard.addView(textView)
        }

        Glide.with(binding.ivAvatar.context).load(recipeModel.imagen).into(binding.ivAvatar)
    }
}