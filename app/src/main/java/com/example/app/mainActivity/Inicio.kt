package com.example.app.mainActivity


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.app.R
import com.example.app.databinding.ActivityInicioBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream


class Inicio : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    private lateinit var auth: FirebaseAuth

    private val TAG = "MyActivity"
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?
        //val navController = navHostFragment!!.navController

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHostFragment.navController
        val navView: BottomNavigationView = binding.navView


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_userData, R.id.navigation_ingredients, R.id.navigation_recipes
            )
        )

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.hide()

        val toolbar2: Toolbar = findViewById(R.id.bottomAppBar)
        setSupportActionBar(toolbar2)
        supportActionBar?.hide()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        //Sin el nav:
        /*
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
           binding.textView2.text=currentUser.email
        }
        binding.button.setOnClickListener(){
            auth.signOut()
            val intent = Intent(this, RegisterView::class.java)
            startActivity(intent)
        }*/
/*
        data class DesayunosData(val desayunos: List<Desayuno>)

        try {
            val jsonFileName = "desayunos.json"
            val inputStream: InputStream = this.assets.open(jsonFileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            // Convert the byte array to a String (assuming UTF-8 encoding)
            val jsonString = String(buffer, Charsets.UTF_8)

            // Log or display the JSON data
            Log.d(TAG, "JSON data: $jsonString")

            // Now you can parse the JSON string and further process the data
            // ...

            val gson = Gson()
            val desayunosData = gson.fromJson(jsonString,DesayunosData::class.java)

            Log.d(TAG, "mealsData data: $desayunosData")

            val desayunosList = desayunosData.desayunos

            Log.d(TAG, "mealsList data: $desayunosList")

            val desayunosCollection = db.collection("desayunos")

            for (desayuno in desayunosList) {
                desayunosCollection.add(desayuno)
            }

        } catch (e: IOException) {
            // Handle the exception if there's an issue reading the JSON file
            Log.e(TAG, "Error reading JSON file: $e")
            Toast.makeText(this, "Error reading JSON file", Toast.LENGTH_SHORT).show()
        }*/


        //Cargar menu
        /*
            try {
                val jsonFileName = "menu_del_dia.json" // Nombre del nuevo JSON
                val inputStream: InputStream = this.assets.open(jsonFileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()

                // Convertir el arreglo de bytes a una cadena String (asumiendo codificación UTF-8)
                val jsonString = String(buffer, Charsets.UTF_8)

                // Log o muestra los datos JSON
                Log.d(TAG, "JSON data: $jsonString")

                // Convert JSON string to MenuDia object using Gson
                val gson = Gson()
                val menuDia = gson.fromJson(jsonString, MenuDia::class.java)

    // Add menuDia object to Firestore collection named "menu_dia"
                db.collection("menu_dia")
                    .document("ernesto")
                    .set(menuDia)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "MenuDia added with ID: ")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding MenuDia", e)
                    }
                /*
                            // Ejemplo de cómo acceder a los datos
                            val desayuno = menuData.desayuno
                            val comida = menuData.comida
                            val cena = menuData.cena

                            // Guardar los datos en Firebase Firestore
                            val menuCollection = db.collection("menu_del_dia")
                            menuCollection.document("desayuno").set(desayuno)
                            menuCollection.document("comida").set(comida)
                            menuCollection.document("cena").set(cena)*/

            } catch (e: IOException) {
                // Maneja la excepción si hay algún problema al leer el archivo JSON
                Log.e(TAG, "Error leyendo el archivo JSON: $e")
                Toast.makeText(this, "Error leyendo el archivo JSON", Toast.LENGTH_SHORT).show()
            }*/

    }

    // Override onSupportNavigateUp to handle Up button presses in the default ActionBar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Al no poner nada estoy evitando que el user pueda retroceder a la pantalla anterior
    }
}