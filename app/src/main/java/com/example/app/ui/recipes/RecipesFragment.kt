package com.example.app.ui.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.databinding.FragmentRecipesBinding
import com.example.app.ui.recipes.adapter.RecipesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater,container,false)

        val recipeViewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)

        binding.loadingIndicator.visibility = View.VISIBLE
        binding.recipeRV.visibility = View.GONE

        lifecycleScope.launch(Dispatchers.Main) {
            val rv = binding.recipeRV
            val data = recipeViewModel.getData()
            binding.loadingIndicator.visibility = View.GONE
            binding.recipeRV.visibility = View.VISIBLE

            data?.let {
                rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = RecipesAdapter(it)
                Log.d("Comprobar", "RecipeFragment rv=RecipeAdapter ${rv.adapter}")
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}