package com.example.app.mainActivity


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.app.R
import com.example.app.databinding.ActivityInicioBinding
import com.example.app.model.PlatoNutri
import com.example.app.model.PlatoWetaca
import com.example.app.ui.main.WeekManager
import com.example.app.ui.main.model.Plato
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class Inicio : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding

    val auth = FirebaseAuth.getInstance()
    val uidUser = auth.currentUser!!.uid

    private val TAG = "MyActivity"
    private val db = Firebase.firestore


    @RequiresApi(Build.VERSION_CODES.O)
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
                R.id.navigation_userData, R.id.navigation_menu, R.id.navigation_recipes
            )
        )

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.hide()

        /*
                val toolbar2: Toolbar = findViewById(R.id.bottomAppBar)
                setSupportActionBar(toolbar2)
                supportActionBar?.hide()
        */

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /***************CARGAR MENUS*************/

        /*              lifecycleScope.launch(Dispatchers.Main) {
                          //val data = getData("comidas_wetaca", 700, 23, 23, 60)
                          val dataComida = getData("comidas_wetaca_nutri", 200, 1, 1, 1)
                          val dataCena = getData("platos_nutri", 200, 1, 1, 1)
                          //val dataCena = getData("comidas_nutri", 200, 1, 1, 1)

                          dataComida?.let {

                              Log.d("CreacionMenu", "PLatos ${it.random()}")
                              Log.d("CreacionMenu", "PLatos ${it}")

                              cargarMenu("menu_dia", it, "comida")
                          }

                          dataCena?.let {

                              Log.d("CreacionMenu", "PLatos ${it.random()}")
                              Log.d("CreacionMenu", "PLatos ${it}")
                              cargarMenu("menu_dia", it, "desayuno")

                              cargarMenu("menu_dia", it, "cena")
                          }
                      }*/

        /***************CARGAR PLATOS*************/
        //cargarPlatos()

        //cargarPlatosNutri("platosNutri.json","platos_nutri")
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun cargarMenu(
        coleccion: String,
        documentReferenceList: MutableList<DocumentReference>,
        tipo: String
    ) {
        Log.d("CreacionMenu", "Uid suer-> ${uidUser}")
        Log.d("CreacionMenu", "Dia-> ${LocalDate.now()}")
        var listaAuxiliar = documentReferenceList

        val weekManager = WeekManager()
        val startDate = LocalDate.now()
        val daysOfWeek = weekManager.getDaysOfWeek(startDate)
        Log.d("CreacionMenu", "Today--> ${LocalDate.now()}")
        Log.d("CreacionMenu", "daysOfWeek--> ${daysOfWeek.toString()}")


        val dateFormatter = DateTimeFormatter.ofPattern("E", Locale("es"))

        for (day in daysOfWeek) {
            val documentReference = listaAuxiliar.random()
            listaAuxiliar.remove(documentReference)
            val campo = hashMapOf(
                tipo to documentReference
            )
            day.format(dateFormatter)
            Log.d("CreacionMenu", "dayformat--> ${day}")
            Log.d("CreacionMenu", "documentReference--> ${documentReference}")

            db.collection(coleccion)
                .document(uidUser).collection("semActual").document(day.toString())
                .set(campo, SetOptions.merge())
                .addOnSuccessListener { documentReference ->
                    Log.d("CreacionMenu", "MenuDia added with ID: ")
                }
                .addOnFailureListener { e ->
                    Log.w("CreacionMenu", "Error adding MenuDia", e)
                }
        }
    }

    fun cargarPlatosNutri(jsonFileName: String) {
        data class PlatosData(val comidasNutri: List<PlatoNutri>)

        try {
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
            val platoData = gson.fromJson(jsonString, PlatosData::class.java)

            Log.d(TAG, "mealsData data: $platoData")

            val platosList = platoData.comidasNutri

            Log.d(TAG, "mealsList data: $platosList")

            val platosCollectionDesayuno = db.collection("platos_desayuno")
            val platosCollectionComida = db.collection("platos_comida")
            val platosCollectionCena = db.collection("platos_cena")

            for (plato in platosList) {
                when (plato.tipo) {
                    1 -> platosCollectionDesayuno.add(plato)
                    2 -> platosCollectionComida.add(plato)
                    3 -> platosCollectionCena.add(plato)
                }
            }

        } catch (e: IOException) {
            // Handle the exception if there's an issue reading the JSON file
            Log.e(TAG, "Error reading JSON file: $e")
            Toast.makeText(this, "Error reading JSON file", Toast.LENGTH_SHORT).show()
        }
    }

    fun cargarPlatos() {
        data class PlatosData(val comidasWetaca: List<PlatoWetaca>)

        try {
            val jsonFileName = "comidasWetaca.json"
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
            val platoData = gson.fromJson(jsonString, PlatosData::class.java)

            Log.d(TAG, "mealsData data: $platoData")

            val platosList = platoData.comidasWetaca

            Log.d(TAG, "mealsList data: $platosList")

            val platosCollection = db.collection("comidas_wetaca")

            for (plato in platosList) {
                platosCollection.add(plato)
            }

        } catch (e: IOException) {
            // Handle the exception if there's an issue reading the JSON file
            Log.e(TAG, "Error reading JSON file: $e")
            Toast.makeText(this, "Error reading JSON file", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun getData(
        tipo: Int, dietaUser: String,

        calorias: Int,
        grasas: Int,
        proteinas: Int,
        carbohidratos: Int,
    ): MutableList<DocumentReference>? {
        return withContext(Dispatchers.IO) {
            try {
                var coleccion = ""
                when (tipo) {
                    1 -> coleccion = "platos_desayuno"
                    2 -> coleccion = "platos_comida"
                    3 -> coleccion = "platos_cena"
                }
                val result: QuerySnapshot = db.collection(coleccion).get().await()
                val documentsReferences = mutableListOf<DocumentReference>()

                for (document in result.documents) {
                    val totalGrasa = document.getDouble("total_grasa") ?: 0.0
                    val totalProteina = document.getDouble("total_proteina") ?: 0.0
                    val totalCarbohidratos = document.getDouble("total_carbohidratos") ?: 0.0
                    val totalKilocalorias = document.getDouble("total_kilocalorias") ?: 0.0
                    val dieta = document.get("dieta") ?: 3

                    when (dietaUser) {
                        "Estándar" -> documentsReferences.add(document.reference)
                        "Vegetariana" -> if (dieta != 1) {
                            documentsReferences.add(document.reference)
                        }

                        "Vegana" -> if (dieta == 3) {
                            documentsReferences.add(document.reference)
                        }
                    }


                    /*  if (totalKilocalorias > calorias && totalGrasa > grasas && totalCarbohidratos > carbohidratos && totalProteina > proteinas) {

                          documentsReferences.add(document.reference)
                      }*/
                }
                // _recipesList.postValue(ingredients)

                //Este @withContext, simplemente es para que sea mas claro de que metododo es este return,
                //en este caso de return withContext(Dispatchers.IO) de arriba
                return@withContext documentsReferences
            } catch (e: Exception) {
                Log.e("Comprobar", "Error getting data: ${e.message}")
                return@withContext null
            }
        }
    }

    /*get data sin references
      suspend fun getData(
          coleccion: String,
          calorias: Int,
          grasas: Int,
          proteinas: Int,
          carbohidratos: Int,
      ): List<PlatoBD>? {
          return withContext(Dispatchers.IO) {
              try {
                  val result: QuerySnapshot = db.collection(coleccion).get().await()
                  val platos = mutableListOf<PlatoBD>()

                  for (document in result.documents) {
                      val platoNombre = document.getString("plato") ?: ""
                      val ingredientesList =
                          document.get("ingredientes") as? List<Ingrediente> ?: emptyList()
                      val totalGrasa = document.getDouble("total_grasa") ?: 0.0
                      val totalProteina = document.getDouble("total_proteina") ?: 0.0
                      val totalCarbohidratos = document.getDouble("total_carbohidratos") ?: 0.0
                      val totalKilocalorias = document.getDouble("total_kilocalorias") ?: 0.0
                      val imagen = document.getString("imagen") ?: ""

                      if (totalKilocalorias > calorias && totalGrasa > grasas && totalCarbohidratos > carbohidratos && totalProteina > proteinas) {
                          val platoBD = PlatoBD(
                              plato = platoNombre,
                              ingredientes = ingredientesList,
                              total_grasa = totalGrasa,
                              total_proteina = totalProteina,
                              total_carbohidratos = totalCarbohidratos,
                              total_kilocalorias = totalKilocalorias,
                              imagen = imagen
                          )
                          platos.add(platoBD)
                      }
                  }
                  // _recipesList.postValue(ingredients)

                  //Este @withContext, simplemente es para que sea mas claro de que metododo es este return,
                  //en este caso de return withContext(Dispatchers.IO) de arriba
                  return@withContext platos
              } catch (e: Exception) {
                  Log.e("Comprobar", "Error getting data: ${e.message}")
                  return@withContext null
              }
          }
      }
  */


}