package com.example.app.ui.user

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.app.databinding.FragmentUserDataBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class userDataFragment : Fragment() {

    private var _binding: FragmentUserDataBinding? = null
    private val binding get() = _binding!!
    private val dietetics = arrayOf("Vegana", "Vegetariana", "Estándar")
    private val activities = arrayOf("Poco o ninguno", "Ligero, 1-3 dias/semana", "Moderado, 3-5 dias/semana", "Muy activo, 5-7 dias/semana")
    private val objetives = arrayOf("Perder peso", "Ganar masa muscular", "Comer más saludable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDataBinding.inflate(inflater, container, false)

        val userDataViewModel = ViewModelProvider(this).get(userDataViewModel::class.java)

        //hacemos la llamada asyncrona del metodo getData()
        lifecycleScope.launch(Dispatchers.Main) {
            val user = userDataViewModel.getData()
            user.let {
                Log.d("lambda", it.toString())
            }
            fillUserData( binding.weight,user?.peso.toString())
            fillUserData(binding.height,user?.altura.toString())
            fillUserData(binding.dieteticType, user?.dietetic_preference.toString())
            //TODO guardar strings de nivel actividad
            // fillUserData(binding.userActivities,user?.actividad)

            fillDropDown(dietetics, binding.dieteticType)
            fillDropDown(activities, binding.userActivities)
            fillDropDown(objetives, binding.userObjetives)


            binding.weight.setOnFocusChangeListener{_,hasFocus->
                if (!hasFocus) {
                    userDataViewModel.updateData(user,binding.weight.text.toString(), "peso")
                }
            }
        }
        return binding.root
    }

    private fun fillUserData(dropDown: AutoCompleteTextView, userData: String) {
        val index = dietetics.indexOf(userData)
        dropDown.text= Editable.Factory.getInstance().newEditable(userData)
    }

    private fun fillUserData(input: TextInputEditText, userData: String) {

        input.text = Editable.Factory.getInstance().newEditable(userData)
    }

    private fun fillDropDown(items: Array<String>, dropDown: AutoCompleteTextView) {
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            items
        )
        dropDown.setAdapter(adapter)
    }
}