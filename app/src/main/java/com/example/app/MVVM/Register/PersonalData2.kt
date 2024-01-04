package com.example.app.MVVM.Register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.app.databinding.FragmentPersonalData2Binding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PersonalData2 : Fragment() {
    private var _binding: FragmentPersonalData2Binding? = null
    private val binding get() = _binding!!
    private var selectedDate: Long? = null
    private var edad: Int = 0
    private var selectedSex: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalData2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dateInput.setOnClickListener {
            datePicker()
        }
        binding.sex.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                selectedSex = view.findViewById(checkedId)
            }
        }
        binding.nextQuestion.setOnClickListener {
            registerData()
            (activity as? Register)?.nextQuestion()
        }
    }

    fun datePicker() {
        val builder =
            MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener {
            //cuando pulsa aceptar formateamos de UTC a la fecha legible y la seteamos en el input
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            selectedDate = picker.selection
            val formatedDateString = dateFormat.format(selectedDate)
            val editText = binding.dateInput
            editText.setText(formatedDateString.toString())
            calcularEdad(selectedDate)
        }

        picker.show(childFragmentManager, picker.toString())
    }

    private fun calcularEdad(fechaUser: Long?) {
        val fechaActual = Date().time
        edad = ((fechaActual - fechaUser!!) / 1000 / 60 / 60 / 24 / 365).toInt()
    }

    fun registerData() {
        val altura = binding.height.text.toString().toFloat()
        val peso = binding.weight.text.toString().toFloat()
        val fecha = selectedDate.toString()
        val sex = selectedSex?.text?.toString() ?: ""
        (activity as? Register)?.addDato(altura)
        (activity as? Register)?.addDato(peso)
        (activity as? Register)?.addDato(fecha)
        (activity as? Register)?.addDato(edad)
        (activity as? Register)?.addDato(sex)
    }
}