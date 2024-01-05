package com.example.app.MVVM.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.app.databinding.ActivityRegisterBinding
import org.json.JSONArray

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val fragmentManager = supportFragmentManager
    var jsonData: JSONArray = JSONArray()
    private var progreso = 0

    private var contador = 0
    private var bmr = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //cargamos el primer fragmento
        replaceFragment(PersonalData())
        //le asignamos al boton atras la funcion previousQuestion
        binding.back.setOnClickListener{
            previousQuestion()
        }
    }

    public fun nextQuestion() {
        progreso++
        binding.progress.incrementProgressBy(1)
        when (progreso) {
            1 -> replaceFragment(PersonalData2())
            2 -> replaceFragment(PhysicalActivity())
            3 -> replaceFragment(Objetives())
            4 -> {
                calcularBMR()
                replaceFragment(Alergias())
            }

            5 -> println(jsonData)
        }
    }

    fun previousQuestion() {
//["No",14,9,"747964800000",30,"Mujer",1.375,0.8,"[altramuces]"]
        binding.progress.incrementProgressBy(-1)
        when (progreso) {
            1 -> {
                replaceFragment(PersonalData())
                deleteData(0)
            }
            2 -> {
                replaceFragment(PersonalData2())
                deleteData(arrayOf(5,4,3,2,1))
            }
            3 -> {
                replaceFragment(Objetives())
                deleteData(6)
            }
            4 -> {
                replaceFragment(PhysicalActivity())
                deleteData(7)
            }

            5 -> println(jsonData)
        }
        progreso--
    }

    //funciones que borran datos del array al retroceder
    fun deleteData(index: Int) {
        jsonData.remove(index)
    }

    fun deleteData(array: Array<Int>) {
        array.forEach { indice ->
            jsonData.remove(indice)
        }
    }

    //funcion que remplaza fragmentos por otros
    private fun replaceFragment(fr: Fragment) {
        val fragment = fr
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
        fragmentTransaction.commit()
    }

    //añadir datos al json
    public fun addDato(clave: String, valor: Any) {
        jsonData.put(clave,valor)
    }

    //funcion que calcula las calorias totales diarias de la persona
    //es asincrona para hacerlo mas eficiente
    fun calcularBMR() {
        val sex = jsonData[5]
        val peso = jsonData[2].toString().toDouble()
        val altura = jsonData[1].toString().toDouble()
        val edad = jsonData[4].toString().toInt()
        val actividad = jsonData[6].toString().toDouble()
        val deficit = jsonData[7].toString().toDouble()
        /*Men: BMR = 88.362 + (13.397 x weight in kg) + (4.799 x height in cm) - (5.677 x age in years)*/
        if (sex == "Hombre") {
            bmr =
                (88.362 + (13.397 * peso) + (4.799 * altura) - (5.677 * edad)) * actividad * deficit
        } else {
            /*Women: BMR = 447.593 + (9.247 x weight in kg) + (3.098 x height in cm) - (4.330 x age in years)*/
            bmr =
                (447.593 + (9.247 * peso) + (3.098 * altura) - (4.330 * edad)) * actividad * deficit
        }
    }

}