package com.example.app.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentDieteticBinding

import com.example.app.databinding.FragmentPersonalDataBinding

class DieteticPreference : Fragment() {

    private var _binding : FragmentDieteticBinding? =null
    private val binding get() = _binding!!

    private lateinit var selectedDietetic : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDieteticBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_personal_data, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dieteticPreference.addOnButtonCheckedListener{ _, checkedId, isChecked->
            if(isChecked){
                //cogemos el id del pulsado y lo pasamos a string para añadirlo al array
                val selectedDieteticId= view.findViewById<Button>(checkedId)
                selectedDietetic = selectedDieteticId.text.toString()

                (activity as? RegisterActivity)?.addDato("dietetic_preference", selectedDietetic)

                (activity as? RegisterActivity)?.nextQuestion()
            }
        }
    }


}