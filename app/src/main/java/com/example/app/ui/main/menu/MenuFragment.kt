package com.example.app.ui.main.menu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.FragmentMenuBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {

    private var _binding:FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuViewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater,container,false)

        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        binding.loadingIndicator.visibility = View.VISIBLE
      //  binding.menuRV.visibility = View.GONE
        Log.d("MenuFragment", "MenuFragment rv=MenuAdapter ")
        lifecycleScope.launch(Dispatchers.Main) {
            menuViewModel.fetchData() // Start fetching data
        }

        observeViewModel()

       /* lifecycleScope.launch(Dispatchers.Main) {
            /*val rv = binding.menuRV*/
            val data = menuViewModel.fetchData()
            binding.loadingIndicator.visibility = View.GONE
           // binding.menuRV.visibility = View.VISIBLE
            Log.d("MenuFragment", "MenuFragment rv=MenuAdapter $data")

            menuViewModel.menuModelL.observe()

            data?.let {
                /*rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = RecipesAdapter(it)*/
                Log.d("MenuFragment", "MenuFragment rv=MenuAdapter $data")

                binding.nombreCard.text = it.toString()

               /* for (ingrediente in it.menu_del_dia.desayuno.ingredientes) {
                    val textView = TextView(binding.listaIngredientesCard.context)
                    textView.text = ingrediente.toString()
                    binding.listaIngredientesCard.addView(textView)
                }*/

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



            }
        }
*/
        return binding.root
    }
    private fun observeViewModel() {
        menuViewModel.menuModelL.observe(viewLifecycleOwner) { menuModel ->
            // Update UI elements here with the new menuModel data
            binding.loadingIndicator.visibility = View.GONE

            menuModel?.let {

                Glide.with(binding.ivDesayuno.context).load(it.menu_del_dia.desayuno.imagen).into(binding.ivDesayuno)


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
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): MenuFragment {
            val fragment = MenuFragment()

            return fragment
        }
    }
}