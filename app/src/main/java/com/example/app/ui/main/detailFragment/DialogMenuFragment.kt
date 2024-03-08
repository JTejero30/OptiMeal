package com.example.app.ui.main.detailFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.FragmentDialogMenuBinding
import com.example.app.ui.main.model.MenuModel
import com.example.app.ui.main.model.Plato
import com.squareup.picasso.Picasso


class DialogMenuFragment : DialogFragment() {
    /**/

    private var toolbar: Toolbar? = null
    private var btnClose: Button? = null
    private var _binding: FragmentDialogMenuBinding? = null

    private val binding get() = _binding!!

    private lateinit var menuModelPlatoL: Plato

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDialogMenuBinding.inflate(inflater, container, false)
        //toolbar = view.findViewById(R.id.toolbar)
        /**/
        //Desayuno
        /* Glide.with(binding.ivDesayuno.context).load(menuModelPlatoL.imagen)
             .into(binding.ivDesayuno)*/
        binding.loadingIndicator.visibility = View.VISIBLE
        binding.dataLL.visibility = View.GONE
        binding.ivDesayunoLL.visibility = View.GONE

        Picasso.get()
            .load(menuModelPlatoL.imagen)
            .into(binding.ivDesayuno)



        Picasso.get()
            .load(menuModelPlatoL.imagen)
            .into(binding.ivDesayuno, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    binding.ivDesayunoLL.background = binding.ivDesayuno.drawable

                    binding.loadingIndicator.visibility = View.GONE
                    binding.dataLL.visibility = View.VISIBLE
                    binding.ivDesayunoLL.visibility = View.VISIBLE

                }

                override fun onError(e: Exception?) {
                    binding.loadingIndicator.visibility = View.GONE
                    binding.dataLL.visibility = View.VISIBLE
                    binding.ivDesayunoLL.visibility = View.VISIBLE

                }
            })



        binding.nombreCardDesayuno.text = menuModelPlatoL.plato + " (450g)"

        binding.proteinasTx.text = menuModelPlatoL.total_proteina.toString() + "g"

        binding.carboHidratosTx.text = menuModelPlatoL.total_carbohidratos.toString() + "g"

        binding.grasasTx.text = menuModelPlatoL.total_grasa.toString() + "g"


        for (ingrediente in menuModelPlatoL.ingredientes) {
            val textViewCantidad = TextView(binding.listaIngredientesCardDesayuno.context)
            textViewCantidad.text = ingrediente.cantidad + " "
            textViewCantidad.textSize = 15F
            binding.listaIngredientesCardDesayuno.addView(textViewCantidad)

            val textViewNombre = TextView(binding.listaIngredientesCardDesayuno.context)
            textViewNombre.text = ingrediente.nombre
            textViewNombre.textSize = 15F

            binding.listaIngredientesCardDesayuno.addView(textViewNombre)
        }




        binding.btnClose.setOnClickListener {
            Log.d("DialogMenuFragment", "Clickado")
            dismiss()
        }
        return binding.root
    }

    fun setMenuModel(menuModelPlato: Plato) {
        menuModelPlatoL = menuModelPlato
    }

    companion object {
        const val TAG = "example_dialog"

        fun display(fragmentManager: FragmentManager?, menuModel: MenuModel): DialogFragment {
            val DialogFragment = DialogFragment()
            if (fragmentManager != null) {
                DialogFragment.show(fragmentManager, TAG)
            }
            return DialogFragment
        }
    }
}