package com.example.app.ui.main.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.databinding.CardMenuBinding
import com.example.app.ui.main.model.MenuModel

class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = CardMenuBinding.bind(view)
    fun render(menuModel: MenuModel) {

       // Glide.with(binding.ivAvatar.context).load(menuModel.imagen).into(binding.ivAvatar)

        binding.nombreCard.text = menuModel.menu_del_dia.desayuno.plato

        for (ingrediente in menuModel.menu_del_dia.desayuno.ingredientes) {
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

        /*for (macro in menuModel.macros) {
            val textView = TextView(binding.listaMacrosCard.context)
            textView.text = macro.toString()
            binding.listaMacrosCard.addView(textView)
        }*/

    }
}