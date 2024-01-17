package com.example.app.MVVM.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.app.User
import com.example.app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val fragmentManager = supportFragmentManager
    private var userData: MutableMap<String, Any> = mutableMapOf(
        "dietetic_preference" to "",
        "height" to 0.00,
        "weight" to 0.00,
        "data" to "",
        "age" to 0,
        "sex" to "",
        "allergies" to arrayOf<String>(),
        "deficit" to 0.00,
        "activity" to 0.0
    )
    private lateinit var user: User
    private var progreso = 0

    private var contador = 0

    //private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection: CollectionReference = db.collection("users")

    private var TDEE = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("comprobar", "User logged: ${currentUser.email}")
        }
        //cargamos el primer fragmento
        //TODO descomentar linea abajo y borrar otra:
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
                calcularTDEE()
                replaceFragment(Alergias())

                val user = createUserFromJson()
                usersCollection.add(user)
            }

            5 -> Log.d("User data:", userData.toString())
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
    public fun addDato(key: String, dato: Any) {
        userData.put(key, dato)
    }

    //funcion que calcula las calorias totales diarias de la persona
    //es asincrona para hacerlo mas eficiente
    fun calcularTDEE() {
        val sex = userData["sex"]
        val peso = userData["weight"].toString().toDouble()
        val altura = userData["height"].toString().toDouble()
        val edad = userData["age"].toString().toInt()
        val actividad = userData["activity"].toString().toDouble()
        val deficit = userData["deficit"].toString().toDouble()
        /*Men: BMR = 88.362 + (13.397 x weight in kg) + (4.799 x height in cm) - (5.677 x age in years)*/
        /*del BMR sacamos el TDEE (total daily energy expenditure)*/
        if (sex == "Hombre") {
            TDEE =
                (88.362 + (13.397 * peso) + (4.799 * altura) - (5.677 * edad)) * actividad * deficit
        } else {
            /*Women: BMR = 447.593 + (9.247 x weight in kg) + (3.098 x height in cm) - (4.330 x age in years)*/
            TDEE =
                (447.593 + (9.247 * peso) + (3.098 * altura) - (4.330 * edad)) * actividad * deficit
        }
        println(TDEE)
    }

    //Funcion que pasa el json a User
    fun createUserFromJson(): User {
        val dietetic_preference: String = userData["dietetic_preference"].toString()
        val sex = userData["sex"].toString()
        val peso = userData["weight"].toString().toDouble()
        val altura = userData["height"].toString().toDouble()
        val edad = userData["age"].toString().toInt()
        val actividad = userData["activity"].toString().toDouble()
        val deficit = userData["deficit"].toString().toDouble()
        val alergiasArray = userData["allergies"].toString()
        auth = FirebaseAuth.getInstance()

        return User(
            auth.currentUser?.uid,
            auth.currentUser?.email,
            dietetic_preference,
            sex,
            peso,
            altura,
            edad,
            actividad,
            deficit,
            alergiasArray,
        )
    }
}