package com.example.app.ui.user

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.app.databinding.FragmentUserDataBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class userDataFragment : Fragment() {
    private lateinit var viewModel: userDataViewModel
    private var _binding: FragmentUserDataBinding? = null
    private val binding get() = _binding!!
    private val dietetics = arrayOf("Vegana", "Vegetariana", "Estándar")
    private val activities = arrayOf(
        "Poco o ninguno",
        "Ligero, 1-3 dias/semana",
        "Moderado, 3-5 dias/semana",
        "Muy activo, 5-7 dias/semana"
    )
    private val objetives = arrayOf("Perder peso", "Ganar masa muscular", "Comer más saludable")
    private val inputMap: MutableMap<String, Any> = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDataBinding.inflate(inflater, container, false)
        val userDataViewModel =ViewModelProvider(this).get(userDataViewModel::class.java)
        val name = userDataViewModel.name
        viewModel= userDataViewModel
        inputMap["peso"] = binding.weight
        inputMap["altura"] = binding.height
        inputMap["dietetic_preference"] = binding.dieteticType
        inputMap["activityText"]=binding.userActivities
        inputMap["objetivo"]=binding.userObjetives

        //hacemos la llamada asyncrona del metodo getData()
        lifecycleScope.launch(Dispatchers.Main) {
            val user = userDataViewModel.getData()
            binding.emailUser.text=user?.email
            binding.userName.text=name
            user.let {
                Log.d("lambda", it.toString())
            }
            fillUserData(binding.weight, user?.peso.toString())
            fillUserData(binding.height, user?.altura.toString())
            fillUserData(binding.dieteticType, user?.dietetic_preference.toString())
            fillUserData(binding.userActivities, user?.activityText.toString())
            fillUserData(binding.userObjetives, user?.objetivo.toString())

            fillDropDown(dietetics, binding.dieteticType)
            fillDropDown(activities, binding.userActivities)
            fillDropDown(objetives, binding.userObjetives)
//a cada campo le añadimos la propiedad de que se actualice cuando se cambia el foco
            inputMap.forEach { (campoBD, input) ->
                run {
                    if (input.javaClass.toString().contains("TextInputEditText")) {
                        (input as? TextInputEditText)?.setOnFocusChangeListener { _, hasFocus ->
                            if (!hasFocus) {
                                userDataViewModel.updateData(
                                    user,
                                    input.text.toString().toDouble(),
                                    campoBD
                                )
                              hideKeyboard()
                                Toast.makeText(requireContext(),"Perfil actualizado",Toast.LENGTH_LONG).show()
                            }
                        }
                    } else if (input.javaClass.toString().contains("AutoCompleteTextView")) {
                        (input as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->

                            val selectedItem = parent.adapter.getItem(position)
                            userDataViewModel.updateData(
                                user,
                                selectedItem.toString(),
                                campoBD
                            )
                            Toast.makeText(requireContext(),"Perfil actualizado",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
        binding.deleteAccountB.setOnClickListener{deleteAccount()}
        binding.logOut.setOnClickListener{logOut()}
        return binding.root
    }



    private fun fillUserData(dropDown: AutoCompleteTextView, userData: String) {
        val index = dietetics.indexOf(userData)
        dropDown.text = Editable.Factory.getInstance().newEditable(userData)
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
    /*https://dev.to/rohitjakhar/hide-keyboard-in-android-using-kotlin-in-20-second-18gp*/
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    /*https://dev.to/rohitjakhar/hide-keyboard-in-android-using-kotlin-in-20-second-18gp*/
    private fun deleteAccount() {

       val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar este usuario?")
        builder.setPositiveButton("Sí") { _, _ ->
            viewModel.deleteAccount(requireContext())
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.create()
        builder.show()

    }
    private fun logOut() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Cerrar sesión")
        builder.setMessage("¿Desea cerrar sesión?")
        builder.setPositiveButton("Sí") { _, _ ->
            viewModel.logOut(requireContext())
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.create()
        builder.show()
    }
}
