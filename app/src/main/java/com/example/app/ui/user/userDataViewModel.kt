package com.example.app.ui.user

import androidx.lifecycle.ViewModel
import com.example.app.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class userDataViewModel : ViewModel() {

    private val db = Firebase.firestore

    val userCollection = db.collection("users")
    val auth = FirebaseAuth.getInstance()
    val uidUser= auth.currentUser!!.uid
    val userDocument = userCollection.document(uidUser).get()
    suspend fun getData(): User? {
        //coger el documento del usuario que esta logeado

        return withContext(Dispatchers.IO) {
            try {
                //cogemos el document del usuario actualmente logeado
                val uidUser= auth.currentUser!!.uid
                val document = userCollection.document(uidUser).get().await()

                //creamos la instancia del objeto User
                val user = User(
                    document["id"].toString(),
                    document["uid"].toString(),
                    document["email"].toString(),
                    document["dietetic_preference"].toString(),
                    document["sex"].toString(),
                    document["peso"].toString().toDouble(),
                    document["altura"].toString().toDouble(),
                    document["edad"].toString().toInt(),
                    document["actividad"].toString().toDouble(),
                    document["deficit"].toString().toDouble(),
                    document["alergias"].toString(),
                    document["tdee"].toString().toDouble(),
                    document["activityText"].toString(),
                    document["objetivo"].toString()
                )
                return@withContext user

            } catch (e: Exception) {
                return@withContext null
            }
        }
    }
    fun updateData(user: User?, dato: String, campo: String) {

        val data = hashMapOf(campo to dato)
        userCollection.document(user!!.uid!!).update(data as Map<String, Any>)
    }
}