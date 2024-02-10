package com.example.app.ui.home

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.R
import com.example.app.register.RegisterView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val db = Firebase.firestore

    val ref = db.collection("alimentos")

    private val _ingredientList = MutableLiveData<List<IngredientModel>>()

    private val auth = FirebaseAuth.getInstance()
    val ref2 = db.collection("menu_dia").document(auth.currentUser?.uid.toString())
    private lateinit var reference: DocumentReference

    private val _userEmail = MutableLiveData<String>().apply {
        value = auth.currentUser?.email
    }
    private val _userName = MutableLiveData<String>().apply {
        value = auth.currentUser?.displayName
    }

    val userEmail: LiveData<String> = _userEmail
    val userName: LiveData<String> = _userName


    suspend fun getData2() {

        Log.d("comprobar", "referencia->${ref2}")


        return withContext(Dispatchers.IO) {
            val document: DocumentSnapshot = ref2.get().await()
            if (document.exists()) {
                Log.d("comprobar", "referenciaDocument->${document.data?.get("menu1")}")

            }
        }
    }
    fun getData3() {

        Log.d("comprobar", "referencia->${ref2}")
        Log.d("comprobar", "uid->${auth.currentUser?.uid.toString()}")
        ref2.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("comprobar", "DocumentSnapshot data: ${document.data?.get("menu1")}")
                    reference= document.data?.get("menu1") as DocumentReference
                    reference.get()
                        .addOnSuccessListener { document2 ->
                            if (document2 != null) {
                                Log.d("comprobar", "DocumentSnapshot data: ${document2.data}")

                            } else {
                                Log.d("comprobar", "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d("comprobar", "get failed with ", exception)
                        }
                } else {
                    Log.d("comprobar", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("comprobar", "get failed with ", exception)
            }


    }


    //Creo una funcion en suspend, para que se ejecute en segundo plano
    suspend fun getData(): List<IngredientModel>? {
        //El withContext se utiliza para cambiar de contexto, es decir, para permitir que se realice en segundo plano desde otra clas
        //El dispatcher sirve para que cuando se termine de realizar en este caso la recogida de datos, toma control sobre la ejecucion para realizar
        //un cambio
        return withContext(Dispatchers.IO) {
            try {
                //EL QuerySnapshot contiene el result de la query
                //Le ponemos como await para congelar la funcion hasta que la llamada haya terminado, es decir para esperar a que se recojan todos los datos
                val result: QuerySnapshot = ref.get().await()
                val ingredients = mutableListOf<IngredientModel>()
                //Recorremos el result para obtener todos los elementos
                for (document in result.documents) {
                    val name = document["nombre"].toString()
                    val proteinas = document["proteinas"].toString()
                    val grasas = document["grasas"].toString()
                    val hidratos_de_carbono = document["hidratos_de_carbono"].toString()
                    val calorias = document["calorias"].toString()
                    val imagen = document["imagen"].toString()

                    val ingredient = IngredientModel(
                        name,
                        proteinas,
                        grasas,
                        hidratos_de_carbono,
                        calorias,
                        imagen
                    )
                    ingredients.add(ingredient)
                }
                _ingredientList.postValue(ingredients)

                //Este @withContext, simplemente es para que sea mas claro de que metododo es este return,
                //en este caso de return withContext(Dispatchers.IO) de arriba
                return@withContext ingredients
            } catch (e: Exception) {
                Log.e("Comprobar", "Error getting data: ${e.message}")
                return@withContext null
            }
        }
    }

    fun logOut() {
        auth.signOut()


    }

    /*fun getData(): List<IngredientModel>? {
        //val document = Firebase.firestore.collection("alimentos").get().await()

        ref.get().addOnCompleteListener { result ->
            val resulSet = result.result
            val ingredients = mutableListOf<IngredientModel>()

            for (nombre in resulSet!!) {
                val name = nombre["nombre"].toString()
                val proteinas = nombre["proteinas"].toString()
                val grasas = nombre["grasas"].toString()
                val hidratos_de_carbono = nombre["hidratos_de_carbono"].toString()
                val calorias = nombre["calorias"].toString()
                val imagen = nombre["imagen"].toString()
                val ingredient = IngredientModel(name, proteinas, grasas, hidratos_de_carbono, calorias, imagen)
                ingredients.add(ingredient)
            }
            _ingredientList.value = ingredients
        }
        Log.d("Comprobar", "HomeViewModel -> ingredients list ${_ingredientList.value}")
        return _ingredientList.value
    }*/
    /* companion object {
         val isReady: Boolean = true
     }*/
}