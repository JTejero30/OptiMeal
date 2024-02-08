package com.example.app.ui.recipes.adapter

import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.CardRecipeRecipeBinding
import com.example.app.ui.recipes.RecipeModel

class RecipesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = CardRecipeRecipeBinding.bind(view)
    fun render(recipeModel: RecipeModel) {

        Glide.with(binding.ivAvatar.context).load(recipeModel.imagen).into(binding.ivAvatar)

        binding.nombreCard.text = recipeModel.nombre

        for (ingrediente in recipeModel.ingredients) {
            val textView = TextView(binding.listaIngredientesCard.context)
            textView.text = ingrediente.toString()
            binding.listaIngredientesCard.addView(textView)
        }

        binding.displayIngredientes.setOnClickListener() {

            if (binding.listaIngredientesCard.visibility == View.VISIBLE) {
                binding.listaIngredientesCard.visibility = View.GONE
                binding.displayIngredientes.setIconResource(R.drawable.baseline_arrow_drop_down_24)
            } else {
                binding.listaIngredientesCard.visibility = View.VISIBLE
                binding.displayIngredientes.setIconResource(R.drawable.baseline_arrow_drop_up_24)
            }
        }

        binding.displayMacros.setOnClickListener() {

            if (binding.listaMacrosCard.visibility == View.VISIBLE) {
                binding.listaMacrosCard.visibility = View.GONE
                binding.displayMacros.setIconResource(R.drawable.baseline_arrow_drop_down_24)
            } else {
                binding.listaMacrosCard.visibility = View.VISIBLE
                binding.displayMacros.setIconResource(R.drawable.baseline_arrow_drop_up_24)
            }
        }

        for (macro in recipeModel.macros) {
            val textView = TextView(binding.listaMacrosCard.context)
            textView.text = macro.toString()
            binding.listaMacrosCard.addView(textView)
        }

    }
}