package com.example.app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.ui.home.adapter.HomeAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val textView: TextView = binding.textUserEmail
        homeViewModel.userEmail.observe(viewLifecycleOwner) {
            textView.text = it
        }


        binding.loadingIndicator.visibility=View.VISIBLE
        binding.homeRV.visibility=View.GONE

        //Aqui lanzamos la corrutina para que se ejecute en segundo plano
        lifecycleScope.launch(Dispatchers.Main) {

            val rv = binding.homeRV

            //Y aqui dentro estamos llamando la funcion getData, que como tinee un await
            //Va a esperar hasta que se recojan todos los datos
            val data = homeViewModel.getData()
            binding.loadingIndicator.visibility=View.GONE
            binding.homeRV.visibility=View.VISIBLE

            data?.let {
                rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = HomeAdapter(it)

                Log.d("Comprobar", "HomeFragment rv=HomeAdapter ${rv.adapter}")
            }
        }

/*
        // Set up RecyclerView
        val rv = binding.homeRV
        rv.layoutManager = LinearLayoutManager(context)
        val adapter = HomeAdapter(emptyList())
        rv.adapter = adapter

        // Set up loading indicator
        val loadingIndicator = binding.imageView3
        loadingIndicator.visibility = View.GONE

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                loadingIndicator.visibility = View.VISIBLE
                rv.visibility = View.INVISIBLE
            } else {
                loadingIndicator.visibility = View.GONE
                rv.visibility = View.VISIBLE
                val data = homeViewModel.getData()
                data?.let {
                    rv.layoutManager = LinearLayoutManager(context)
                    rv.adapter = HomeAdapter(it)

                    Log.d("Comprobar", "HomeFragment rv=HomeAdapter ${rv.adapter}")
                }

            }
        }*/


/*
        var rv =binding.homeRV
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = homeViewModel.getData()?.let { HomeAdapter(it) }
        Log.d("Comprobar","HomeFragment rv=HomeAdapter ${rv.adapter}")
        */
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}