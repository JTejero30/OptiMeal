package com.example.app.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.databinding.FragmentMainBinding
import com.example.app.ui.main.calendar.CalendarPagerAdapter

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val adapter = CalendarPagerAdapter(parentFragmentManager)
        binding.viewPager.adapter = adapter

        val position = Integer.MAX_VALUE / 2

        binding.viewPager.setCurrentItem(position, false)

        return binding.root
    }

}