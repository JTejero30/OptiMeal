package com.example.app.MVVM.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.app.databinding.ActivityRegisterBinding
import org.json.JSONArray

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val fragmentManager = supportFragmentManager
    var jsonData : JSONArray = JSONArray()
    private var progreso =0

    private var contador = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //cargamos el primer fragmento
        replaceFragment(PersonalData())
    }

    public fun nextQuestion() {
        progreso++
        binding.progress.incrementProgressBy(1)
        when(progreso){
            1-> replaceFragment(PersonalData2())
            2-> replaceFragment(Alergias())
            3-> replaceFragment(Objetives())
            4-> println(jsonData)
        }
    }
    //funcion que remplaza fragmentos por otros
    private fun replaceFragment(fr: Fragment){
        val fragment= fr
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
        fragmentTransaction.commit()
    }
    //añadir datos al json
    public fun addDato(dato: String){
        jsonData.put(dato)
    }
    public fun addDato(dato: Int){
        jsonData.put(dato)
    }
    public fun addDato(dato: MutableList<String>){
        jsonData.put(dato)
    }
    public fun addDato(dato: Float){
        jsonData.put(dato)
    }
}