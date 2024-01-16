package com.example.app.ui.superhero

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.FragmentSuperheroBinding
import com.example.app.recyclerView.SuperHeroProvider
import com.example.app.ui.superhero.adapter.SuperHeroAdapter


class SuperHeroFragment : Fragment() {
    private var _binding: FragmentSuperheroBinding? = null


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuperheroBinding.inflate(inflater, container, false)

        val rv = RecyclerView(requireContext())
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = SuperHeroAdapter(SuperHeroProvider.superHeroList)

        return rv
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}