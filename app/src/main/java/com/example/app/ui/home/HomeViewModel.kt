package com.example.app.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class HomeViewModel : ViewModel(){

    private val db = Firebase.firestore

    val ref = db.collection("alimentos")

    private val _ingredientList = MutableLiveData<List<IngredientModel>>()

    val ingredientList: LiveData<List<IngredientModel>> get() = _ingredientList



    private val _userEmail = MutableLiveData<String>().apply {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        value = auth.currentUser?.email
    }
    val userEmail: LiveData<String> = _userEmail

    fun getData(): List<IngredientModel>? {
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

                val ingredient = IngredientModel(name, proteinas, grasas, hidratos_de_carbono, calorias)
                ingredients.add(ingredient)
            }

            _ingredientList.value = ingredients
        }

        Log.d("ViewHolder", "ingredients list ${_ingredientList.value}")

        return _ingredientList.value

    }

   /* companion object {
        val isReady: Boolean = true
    }*/
}