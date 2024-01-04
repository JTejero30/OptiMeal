package com.example.app.MVVM.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.app.MVVM.Register.userController.UserController
import com.example.app.User
import com.example.app.databinding.ActivityRegisterBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONArray

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val fragmentManager = supportFragmentManager
    private var jsonData: JSONArray = JSONArray()
    private lateinit var user: User
    private var progreso = 0

    private var contador = 0

    //private val db = Firebase.firestore

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection: CollectionReference = db.collection("users")

    private var bmr = 0.0
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
        when (progreso) {
            1 -> replaceFragment(PersonalData2())
            2 -> replaceFragment(PhysicalActivity())
            3 -> replaceFragment(Objetives())
            4 -> {
                calcularBMR()
                replaceFragment(Alergias())
                Log.d("comprobar", "JSON data: $jsonData")
                val user = createUserFromJson(jsonData)
                Log.d("comprobar", "USER data: $user")
                usersCollection.add(user)
            }

            5 -> println(jsonData)
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
    public fun addDato(dato: String) {
        jsonData.put(dato)
    }

    public fun addDato(dato: Int) {
        jsonData.put(dato)
    }

    public fun addDato(dato: MutableList<String>) {
        jsonData.put(dato)
    }

    public fun addDato(dato: Float) {
        jsonData.put(dato)
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
        println(bmr)
    }

    //Funcion que pasa el json a User
    fun createUserFromJson(jsonArray: JSONArray): User {
        val personalData1: String? = jsonArray.optString(0)
        val altura: Float? = jsonArray.optDouble(1).toFloat()
        val peso: Float? = jsonArray.optDouble(2).toFloat()
        val fecha: String? = jsonArray.optString(3)
        val sex: String? = jsonArray.optString(4)
        val alergiasArray: JSONArray? = jsonArray.optJSONArray(5)
        val alergias: List<String>? =
            alergiasArray?.let { 0.until(it.length()).map { i -> it.optString(i) } }
        val objetive: String = jsonArray.optString(6)

        return User(
            UserController.userLog?.uid,
            UserController.userLog?.email,
            personalData1,
            altura,
            peso,
            fecha,
            sex,
            alergias,
            objetive
        )
    }
}