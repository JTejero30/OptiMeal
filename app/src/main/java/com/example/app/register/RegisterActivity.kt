package com.example.app.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.app.User
import com.example.app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
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
        "activity" to 0.0,
        "activityText" to "",
        "objetivo" to ""
    )
    private lateinit var user: User
    private var progreso = -1

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

        binding.back.setOnClickListener{
            nextQuestion(false)
        }
        nextQuestion(true)
    }

    /*override fun onBackPressed() {
        super.onBackPressed()
        nextQuestion(false)
    }*/
    public fun nextQuestion(avanza: Boolean) {

        if(avanza) {
            progreso++
            binding.progress.incrementProgressBy(1)
        }
        else{
            progreso--
            binding.progress.incrementProgressBy(-1)
        }
        Log.d("avanza",progreso.toString())
        //
        when (progreso) {
            0-> replaceFragment(DieteticPreferenceFragment())
            1 -> replaceFragment(PersonalDataFragment())
            2 -> replaceFragment(PhysicalFragment())
            3 -> replaceFragment(ObjetivesFragment())
            4 -> {
                calcularTDEE()
                replaceFragment(AlergiasFragment())
            }
            5 -> {
                replaceFragment(EmailFragment())
                val user = createUserFromJson()
            }
            6 -> Log.d("User data:", userData.toString())
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
        val variableSex = if(sex=="Hombre") 5 else -161

        /*Men: BMR =(10 x peso en kg) + (6,25 × altura en cm) - (5 × edad en años) + 5*/
        /*Women: BMR = (10 x peso en kg) + (6,25 × altura en cm) - (5 × edad en años) - 161*/
        /*del BMR sacamos el TDEE (total daily energy expenditure)*/

        TDEE = ((10 * peso) + (6.25 * altura) - (5 * edad) + variableSex)* actividad * deficit


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
        val activityText= userData["activityText"].toString()
        val deficit = userData["deficit"].toString().toDouble()
        val alergiasArray = userData["allergies"].toString()
        val objetivo = userData["objetivo"].toString()
        auth = FirebaseAuth.getInstance()


       // val JSONObject= JSONObject(userData)
        return User(
            auth.currentUser?.uid,
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
            TDEE,
            activityText,
            objetivo
        )
    }
    fun createUserFromJsonGoogle(id:String): User {
        val dietetic_preference: String = userData["dietetic_preference"].toString()
        val sex = userData["sex"].toString()
        val peso = userData["weight"].toString().toDouble()
        val altura = userData["height"].toString().toDouble()
        val edad = userData["age"].toString().toInt()
        val actividad = userData["activity"].toString().toDouble()
        val activityText= userData["activityText"].toString()
        val deficit = userData["deficit"].toString().toDouble()
        val alergiasArray = userData["allergies"].toString()
        val objetivo = userData["objetivo"].toString()
        auth = FirebaseAuth.getInstance()


        // val JSONObject= JSONObject(userData)
        return User(
            id,
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
            TDEE,
            activityText,
            objetivo
        )
    }
    public fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error con la autenticacion del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}