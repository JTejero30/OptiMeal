package com.example.app.MVVM.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.app.databinding.FragmentPersonalDataBinding


private var _binding : FragmentPersonalDataBinding? =null
private val binding get() = _binding!!
private lateinit var selectedDietetic : String

class PersonalData : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPersonalDataBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_personal_data, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dieteticPreference.addOnButtonCheckedListener{_,checkedId,isChecked->
            if(isChecked){
                //cogemos el id del pulsado y lo pasamos a string para añadirlo al array
                val selectedDieteticId= view.findViewById<Button>(checkedId)
                selectedDietetic= selectedDieteticId.text.toString()
                (activity as? Register)?.addDato(selectedDietetic)
                (activity as? Register)?.nextQuestion()
            }
        }
    }
}