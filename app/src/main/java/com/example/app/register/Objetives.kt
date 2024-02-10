package com.example.app.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.app.databinding.FragmentObjetivesBinding
import com.example.app.register.RegisterActivity


class Objetives : Fragment() {
    private var _binding: FragmentObjetivesBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentObjetivesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.objetivesBu.addOnButtonCheckedListener { _, id, ischecked ->
            if (ischecked) {
                val selectedObjetiveTag = view.findViewById<Button>(id).tag.toString()
                val deficit = calcularDeficit(selectedObjetiveTag)
                (activity as? RegisterActivity)?.addDato("deficit",deficit)
                (activity as? RegisterActivity)?.nextQuestion()
            }
        }
    }

    private fun calcularDeficit(selectedObjetiveTag: String): Float {
        var deficit = 1.00f
        when (selectedObjetiveTag) {
            "1" -> deficit = 0.8f
            "2" -> deficit = 1.07f
            "3" -> deficit = 1.00f
        }
        return deficit
    }
}