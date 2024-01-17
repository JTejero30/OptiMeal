package com.example.app.MVVM.Menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.app.R
import com.example.app.databinding.FragmentMenuBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Menu.newInstance] factory method to
 * create an instance of this fragment.
 */
class Menu : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        pintarMenu()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun pintarMenu() {
        var opciones: Array<String> = arrayOf("Desayuno", "Comida", "Cena")
        //requireContext() es una funcion para coger el contexto desde los fragmentos
        var myAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, opciones)

        var lunes = binding.lunes
        //lunes.adapter = myAdapter

    }
}