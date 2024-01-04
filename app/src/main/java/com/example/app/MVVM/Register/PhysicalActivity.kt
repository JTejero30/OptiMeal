package com.example.app.MVVM.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.app.R
import com.example.app.databinding.FragmentPhysicalActivityBinding

private var _binding: FragmentPhysicalActivityBinding? = null
private val binding get() = _binding!!

class PhysicalActivity : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhysicalActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.physicalAct.addOnButtonCheckedListener { _, id, isCheck ->
            if (isCheck) {
                val selected = view.findViewById<Button>(id)
                val selectedTag = selected.tag
                val multiplicador= valorSelected(selectedTag)
                (activity as? Register)?.addDato(multiplicador)
                (activity as? Register)?.nextQuestion()
            }
        }
    }

    private fun valorSelected(selectedTag: Any?): Float {
        var multiplicador: Float = 0.0f
        when (selectedTag) {
            "1" -> multiplicador = 1.2f
            "2" -> multiplicador = 1.375f
            "3" -> multiplicador = 1.55f
            "4" -> multiplicador = 1.725f
        }
        return multiplicador
    }

}