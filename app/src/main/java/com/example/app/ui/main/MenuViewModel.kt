package com.example.app.ui.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.ui.main.model.DayModel
import com.example.app.ui.main.model.Ingrediente
import com.example.app.ui.main.model.MenuDelDia
import com.example.app.ui.main.model.MenuModel
import com.example.app.ui.main.model.Plato
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class MenuViewModel : ViewModel() {

    private val db = Firebase.firestore

    //val ref = db.collection("desayunos").document("ernesto")

    private val auth = FirebaseAuth.getInstance()

    //val ref = db.collection("menu_dia").document(auth.currentUser?.uid.toString())
    val ref = db.collection("menu_dia").document(auth.currentUser!!.uid)


    //private lateinit var referenceMenu: Map<String, DocumentReference>

    //weekModelL es una LiveData que expone la data observada, sirve para que desde el Fragment se pueda ovbservar
    val weekModelL: LiveData<MutableList<DayModel>?> get() = _WeekModel

    //_WeekModel es una variable privada que almacenará la data
    private val _WeekModel = MutableLiveData<MutableList<DayModel>?>()
    val menuModelL: LiveData<MenuModel?> get() = _menuModel

    private val _menuModel = MutableLiveData<MenuModel?>()
    fun fetchData(date: LocalDate) {
        Log.d("borrar", auth.currentUser!!.uid)

        viewModelScope.launch {
            try {
                val document = ref.collection("semActual").document(date.toString()).get().await()
                Log.d("MenuViewModel", "document : ${document}")

                if (document.exists()) {
                    //referenceMenu = document.data?.get("menu1") as Map<String, DocumentReference>
                    Log.d("MenuViewModel", "document : ${document}")

                    // referenceMenu = document.data?.get("menu") as Map<String, DocumentReference>
                    val desayunoReference: DocumentReference? =
                        document.data?.get("desayuno") as? DocumentReference
                    val cenaference: DocumentReference? =
                        document.data?.get("cena") as? DocumentReference

                    val comidaReference: DocumentReference? =
                        document.data?.get("comida") as? DocumentReference

                    //val desayunoDeferred = async { fetchPlato(referenceMenu["desayuno"]) }

                    Log.d("MenuViewModel", "Docuenmntrefernce ${comidaReference}")


                    val desayunoDeferred = async { fetchPlato(desayunoReference) }
                    val comidaDeferred = async { fetchPlato(comidaReference) }
                    val cenaDeferred = async { fetchPlato(cenaference) }

                    /*val desayuno = desayunoDeferred.await()
                    val comida = comidaDeferred.await()
                    val cena = cenaDeferred.await()*/
                    val desayuno = comidaDeferred.await()
                    val comida = comidaDeferred.await()

                    val cena = comidaDeferred.await()
                    Log.d("MenuViewModel", "comida data: ${comida}")


                    if (desayuno != null && comida != null && cena != null) {
                        val menuDelDia = MenuDelDia(desayuno, comida, cena)
                        val menuModel = MenuModel(menuDelDia)
                        _menuModel.postValue(menuModel)
                    } else {
                        _menuModel.postValue(null)
                    }
                } else {
                    _menuModel.postValue(null)
                    Log.d("MenuViewModel", "No document exist : ${document}")

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
                Log.d("MenuViewModel", "Menu data fetchPlato: ${menuData}")

                createPlato(menuData)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun createPlato(data: Map<*, *>?): Plato {
        val plato = data?.get("plato").toString()
        val imagen = data?.get("imagen").toString()

        Log.d("MenuViewModel", "Menu data createPlato: ${data}")


        val ingredientesData = data?.get("ingredientes") as? List<Map<*, *>> ?: emptyList()
        val ingredientes = ingredientesData.map { ingredienteData ->
            Ingrediente(
                ingredienteData["nombre"].toString(),
                ingredienteData["cantidad"].toString()
                /*ingredienteData["calorias"].toString().toDouble(),
                    ingredienteData["tipo"].toString(),
                    ingredienteData["gramos"].toString().toDouble()*/
            )

        }
        val total_grasa = data?.get("total_grasa").toString().toDouble()
        val total_proteina = data?.get("total_proteina").toString().toDouble()
        val total_carbohidratos = data?.get("total_carbohidratos").toString().toDouble()

        val intrucciones = data?.get("instrucciones").toString()




        return Plato(plato, ingredientes, total_grasa, total_proteina, total_carbohidratos, imagen,intrucciones)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDates() {
        val weekManager = WeekManager()
        val startDate = LocalDate.now()
        val daysOfWeek = weekManager.getDaysOfWeek(startDate)
        Log.d("WeekManager", "Today--> ${LocalDate.now()}")
        Log.d("WeekManager", "daysOfWeek--> ${daysOfWeek.toString()}")

        var weekModelList: MutableList<DayModel> = mutableListOf()

        val dateFormatter = DateTimeFormatter.ofPattern("E", Locale("es"))

        for (day in daysOfWeek) {
            day.format(dateFormatter)
            Log.d("WeekManager", "dayformat--> ${day}")

            val dayModel = DayModel(
                calendarDayOfWeek = day.dayOfMonth,
                calendarMonth = day.month.getDisplayName(
                    TextStyle.SHORT,
                    Locale("es")
                ), // Mes en español
                dayOfWeek = day.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale("es")
                ), // Dia en español
                year = day.year,
                isCurrentDay = LocalDate.now() == day,
                day = day,
            )
            weekModelList.add(dayModel)
        }
        _WeekModel.postValue(weekModelList)
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
}
