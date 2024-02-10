package com.example.app.ui.main.menu.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MenuViewModel : ViewModel() {

    private val db = Firebase.firestore

    //val ref = db.collection("desayunos").document("ernesto")

    private val auth = FirebaseAuth.getInstance()
    val ref = db.collection("menu_dia").document(auth.currentUser?.uid.toString())

    private lateinit var referenceMenu: Map<String, DocumentReference>
    private lateinit var referenceMenu2: DocumentReference


    private val _menuModel = MutableLiveData<MenuModel?>()
    val menuModelL: LiveData<MenuModel?> get() = _menuModel


    fun fetchData() {
        viewModelScope.launch {
            try {
                val document = ref.get().await()

                if (document.exists()) {
                    referenceMenu = document.data?.get("menu1") as Map<String, DocumentReference>

                    val desayunoDeferred = async { fetchPlato(referenceMenu["desayuno"]) }
                    val comidaDeferred = async { fetchPlato(referenceMenu["comida"]) }
                    val cenaDeferred = async { fetchPlato(referenceMenu["cena"]) }

                    val desayuno = desayunoDeferred.await()
                    val comida = comidaDeferred.await()
                    val cena = cenaDeferred.await()

                    if (desayuno != null && comida != null && cena != null) {
                        val menuDelDia = MenuDelDia(desayuno, comida, cena)
                        val menuModel = MenuModel(menuDelDia)
                        _menuModel.postValue(menuModel)
                    } else {
                        _menuModel.postValue(null) // Post null if some data is missing
                    }
                } else {
                    _menuModel.postValue(null)
                }
            } catch (e: Exception) {
                _menuModel.postValue(null)
                e.printStackTrace()
            }


        }
    }

    private suspend fun fetchPlato(reference: DocumentReference?): Plato? {
        return try {
            val document = reference?.get()?.await()
            if (document != null && document.exists()) {
                val menuData = document.data as Map<String, *>
                createPlato(menuData)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /* suspend fun getData(): MenuModel? {


         return withContext(Dispatchers.IO) {
             try {
                 val document: DocumentSnapshot = ref.get().await()
                 if (document.exists()) {
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
                     val menuData = document.data?.get("menu_del_dia") as Map<String, *>

                     val desayunoData = menuData["desayuno"] as? Map<String, *>
                     val comidaData = menuData["desayuno"] as? Map<String, *>
                     val cenaData = menuData["desayuno"] as? Map<String, *>

                     Log.d("MenuViewModel", " DesayunoData ${desayunoData}")


                     val desayuno = createPlato(desayunoData)
                     val comida = createPlato(comidaData)
                     val cena = createPlato(cenaData)
                     Log.d("MenuViewModel", " desayuno ${desayuno}")

                     val menuDelDia = MenuDelDia(desayuno, comida, cena)
                     val menuModel = MenuModel(menuDelDia)

                     _menuModel.postValue(menuModel)
                     Log.d("MenuViewModel", "Retorna $menuModel")

                     return@withContext menuModel
                 } else {
                     Log.d("MenuViewModel", "Document does not exist")
                     return@withContext null
                 }
             } catch (e: Exception) {
                 Log.e("MenuViewModel", "Error getting data: ${e.message}")
                 return@withContext null
             }
         }
     }*/

    private fun createPlato(data: Map<*, *>?): Plato {
        val plato = data?.get("plato").toString()
        val imagen = data?.get("imagen").toString()

        val ingredientesData = data?.get("ingredientes") as? List<Map<*, *>> ?: emptyList()
        val ingredientes = ingredientesData.map { ingredienteData ->
            Ingrediente(
                ingredienteData["nombre"].toString(),
                ingredienteData["cantidad"].toString(),
                ingredienteData["calorias"].toString().toDouble(),
                ingredienteData["tipo"].toString(),
                ingredienteData["gramos"].toString().toInt()
            )
        }
        val total_grasa = data?.get("total_grasa").toString().toInt()
        val total_proteina = data?.get("total_proteina").toString().toInt()
        val total_carbohidratos = data?.get("total_carbohidratos").toString().toInt()

        Log.d("MenuViewModel", "DocumentSnapshot data: ${imagen}")
        Log.d("MenuViewModel", "DocumentSnapshot data: ${data}")


        return Plato(plato, ingredientes, total_grasa, total_proteina, total_carbohidratos,imagen)
    }
}
