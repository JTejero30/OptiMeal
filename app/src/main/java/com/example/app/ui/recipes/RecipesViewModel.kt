package com.example.app.ui.recipes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.ui.home.IngredientModel
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RecipesViewModel: ViewModel() {

    private val db = Firebase.firestore

    val ref = db.collection("recipes")

    private val _recipesList = MutableLiveData<List<RecipeModel>>()

    suspend fun getData(): List<RecipeModel>? {

        return withContext(Dispatchers.IO) {
            try {
                val result: QuerySnapshot = ref.get().await()
                val ingredients = mutableListOf<RecipeModel>()

                for (document in result.documents) {
                    val name = document["nombre"].toString()
                    val ingredientes: List<String> = document.get("ingredientes") as? List<String> ?: emptyList()
                    val macros: List<String> = document.get("macros") as? List<String> ?: emptyList()
                    val imagen = document["imagen"].toString()

                    val recipe = RecipeModel(
                        name,
                        imagen,
                        ingredientes,
                        macros
                    )
                    ingredients.add(recipe)
                }
                _recipesList.postValue(ingredients)

                //Este @withContext, simplemente es para que sea mas claro de que metododo es este return,
                //en este caso de return withContext(Dispatchers.IO) de arriba
                return@withContext ingredients
            } catch (e: Exception) {
                Log.e("Comprobar", "Error getting data: ${e.message}")
                return@withContext null
            }
        }
    }



}