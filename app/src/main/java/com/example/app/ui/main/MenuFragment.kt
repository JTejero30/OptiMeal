package com.example.app.ui.main

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.FragmentMenuBinding
import com.example.app.ui.main.adapterWeek.WeekAdapter
import com.example.app.ui.main.detailFragment.DialogMenuFragment
import com.example.app.ui.main.model.DayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate


class MenuFragment : Fragment(), DayItemClickI {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuViewModel: MenuViewModel

    private val TAG = "ComprobarSemana"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)



        menuViewModel.getDates()
        menuViewModel.weekModelL.observe(viewLifecycleOwner) { weekModelList ->
            weekModelList?.let {
                Log.d("MenuFragment", "weekMoidel--> ${weekModelList.toString()}")
                val rv = binding.weekRV
                rv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)



                //aqui creo el weekAdapter con el listener, que será este fragment
                rv.adapter = WeekAdapter(weekModelList, this)

            }
        }




        binding.loadingIndicator.visibility = View.VISIBLE
        binding.menuSV.visibility = View.GONE

        lifecycleScope.launch(Dispatchers.Main) {
            menuViewModel.fetchData(LocalDate.now())
            observeViewModel()
        }

        return binding.root
    }

    private fun observeViewModel() {
        menuViewModel.menuModelL.observe(viewLifecycleOwner) { menuModel ->
            // Update UI elements here with the new menuModel data
            binding.loadingIndicator.visibility = View.GONE
            binding.menuSV.visibility = View.VISIBLE
            menuModel?.let {
                Log.d("MenuFragment", "weekMoidel--> ${menuModel.toString()}")

                //Desayuno
                Glide.with(binding.ivDesayuno.context).load(it.menu_del_dia.desayuno.imagen)
                    .into(binding.ivDesayuno)
                binding.ivDesayuno.setOnClickListener {
                    val dialogFragment = DialogMenuFragment()
                    dialogFragment.setMenuModel(menuModel.menu_del_dia.desayuno)

                    dialogFragment.show(childFragmentManager, DialogMenuFragment.TAG)
                }
                binding.nombreCardDesayuno.text = it.menu_del_dia.desayuno.plato
                for (ingrediente in it.menu_del_dia.desayuno.ingredientes) {
                    val textView = TextView(binding.listaIngredientesCardDesayuno.context)
                    textView.text = ingrediente.toString()
                    binding.listaIngredientesCardDesayuno.addView(textView)
                }
                binding.displayIngredientesDesayuno.setOnClickListener() {
                    if (binding.listaIngredientesCardDesayuno.visibility == View.VISIBLE) {
                        binding.listaIngredientesCardDesayuno.visibility = View.GONE
                        binding.displayIngredientesDesayuno.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaIngredientesCardDesayuno.visibility = View.VISIBLE
                        binding.displayIngredientesDesayuno.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }
                binding.displayMacrosDesayuno.setOnClickListener() {
                    if (binding.listaMacrosCardDesayuno.visibility == View.VISIBLE) {
                        binding.listaMacrosCardDesayuno.visibility = View.GONE
                        binding.displayMacrosDesayuno.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaMacrosCardDesayuno.visibility = View.VISIBLE
                        binding.displayMacrosDesayuno.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }

                //Comida
                Glide.with(binding.ivComida.context).load(it.menu_del_dia.comida.imagen)
                    .into(binding.ivComida)

                binding.ivComida.setOnClickListener {
                    val dialogFragment = DialogMenuFragment()
                    dialogFragment.setMenuModel(menuModel.menu_del_dia.comida)

                    dialogFragment.show(childFragmentManager, DialogMenuFragment.TAG)
                }

                binding.nombreCardComida.text = it.menu_del_dia.comida.plato
                for (ingrediente in it.menu_del_dia.comida.ingredientes) {
                    val textView = TextView(binding.listaIngredientesCardComida.context)
                    textView.text = ingrediente.toString()
                    binding.listaIngredientesCardComida.addView(textView)
                }
                binding.displayIngredientesComida.setOnClickListener() {
                    if (binding.listaIngredientesCardComida.visibility == View.VISIBLE) {
                        binding.listaIngredientesCardComida.visibility = View.GONE
                        binding.displayIngredientesComida.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaIngredientesCardComida.visibility = View.VISIBLE
                        binding.displayIngredientesComida.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }
                binding.displayMacrosComida.setOnClickListener() {
                    if (binding.listaMacrosCardComida.visibility == View.VISIBLE) {
                        binding.listaMacrosCardComida.visibility = View.GONE
                        binding.displayMacrosComida.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaMacrosCardComida.visibility = View.VISIBLE
                        binding.displayMacrosComida.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }

                //Comida
                Glide.with(binding.ivCena.context).load(it.menu_del_dia.cena.imagen)
                    .into(binding.ivCena)

                binding.ivDesayuno.setOnClickListener {
                    val dialogFragment = DialogMenuFragment()
                    dialogFragment.setMenuModel(menuModel.menu_del_dia.desayuno)

                    dialogFragment.show(childFragmentManager, DialogMenuFragment.TAG)
                }

                binding.nombreCardCena.text = it.menu_del_dia.cena.plato
                for (ingrediente in it.menu_del_dia.cena.ingredientes) {
                    val textView = TextView(binding.listaIngredientesCardCena.context)
                    textView.text = ingrediente.toString()
                    binding.listaIngredientesCardCena.addView(textView)
                }
                binding.displayIngredientesCena.setOnClickListener() {
                    if (binding.listaIngredientesCardCena.visibility == View.VISIBLE) {
                        binding.listaIngredientesCardCena.visibility = View.GONE
                        binding.displayIngredientesCena.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaIngredientesCardCena.visibility = View.VISIBLE
                        binding.displayIngredientesCena.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }
                binding.displayMacrosCena.setOnClickListener() {
                    if (binding.listaMacrosCardCena.visibility == View.VISIBLE) {
                        binding.listaMacrosCardCena.visibility = View.GONE
                        binding.displayMacrosCena.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaMacrosCardCena.visibility = View.VISIBLE
                        binding.displayMacrosCena.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Inside MenuFragment class
    companion object {
        fun newInstance(): MenuFragment {
            val fragment = MenuFragment()

            return fragment
        }
    }

    override fun onDayItemClicked(dayModel: DayModel) {
        Log.d("MenuFragment", "dayModel--> ${dayModel}")

        binding.loadingIndicator.visibility = View.VISIBLE
        binding.menuSV.visibility = View.GONE

        lifecycleScope.launch(Dispatchers.Main) {

            menuViewModel.fetchData(dayModel.day)
            delay(500)
            observeViewModel()
        }
    }
}

