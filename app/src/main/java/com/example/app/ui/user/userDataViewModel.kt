package com.example.app.ui.user

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.app.User
import com.example.app.register.LoginActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class userDataViewModel : ViewModel() {

    private val db = Firebase.firestore

    val userCollection = db.collection("users")
    private val auth = FirebaseAuth.getInstance()
    val uidUser = auth.currentUser!!.uid
    val userDocument = userCollection.document(uidUser).get()
    val name= auth.currentUser?.displayName
    suspend fun getData(): User? {
        //coger el documento del usuario que esta logeado

        return withContext(Dispatchers.IO) {
            try {
                //cogemos el document del usuario actualmente logeado
                val uidUser = auth.currentUser!!.uid
                val document = userCollection.document(uidUser).get().await()
                val imagen = document["imagen"]
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

    fun updateData(user: User?, dato: Any, campo: String) {

        val data = hashMapOf(campo to dato)
        userCollection.document(user!!.uid!!).update(data as Map<String, Any>)
    }
    fun deleteAccount(requireContext: Context) {
        val db = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser!!
        val documentReference = db.collection("users").document(user.uid)
        //hay que relogear al user para hacer esta operacion.
        //TODO ojo aqui la contraseña
        val credential = EmailAuthProvider
            .getCredential(user.email.toString(), "1234")
        user.reauthenticate(credential)
        //borramos los datos de la tabla user
        documentReference.delete()
        //borramos el Authentication
        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(requireContext, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    requireContext.startActivity(intent)

                }else{
                    Log.d("deleteAccount", "No borrado.")
                }
            }
    }

    fun logOut(requireContext: Context) {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(requireContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        requireContext.startActivity(intent)
    }
}