package com.example.app.MVVM.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.app.databinding.ActivityRegisterViewBinding
import com.example.app.mainActivity.Inicio
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class RegisterView : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterViewBinding
    private val fragmentManager = supportFragmentManager


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            userLogged()
        }
        replaceFragment(LoginFragment())

        checkFirestoreCollectionAccess()
    }

    private fun replaceFragment(fr: Fragment) {
        val fragment = fr
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
        fragmentTransaction.commit()
    }

    public fun userLogged() {
        Log.d("comprobar", "Correct Login")

        val inicioActivity: Intent = Intent(this, Inicio::class.java)
        startActivity(inicioActivity)
    }

    public fun checkLogin(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("comprobar", "Correct Login desde el fragment ")
                //UserController.logIn(user)
                val intent = Intent(this, Inicio::class.java)
                startActivity(intent)
            } else {
                Log.d("comprobar", "Error")

                showAlert()
            }
        }
    }

    public fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error con la autenticacion del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private val TAG = "YourClass" // Replace with your desired tag
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection: CollectionReference = db.collection("users")

    fun checkFirestoreCollectionAccess() {
        // Perform a simple read operation to check access
        usersCollection
            .limit(1) // Limit the query to retrieve at most one document
            .get()
            .addOnSuccessListener { documents ->
                // Successfully retrieved data, it means you have access
                if (documents.size() > 0) {
                    // Access is confirmed
                    Log.d(TAG, "Access confirmed to Firestore collection.")
                } else {
                    // Collection is empty but still indicates access
                    Log.d(TAG, "Access confirmed to Firestore collection (collection is empty).")
                }
            }
            .addOnFailureListener { e ->
                // Handle any errors that occurred
                Log.e(TAG, "Error accessing Firestore collection: $e")
            }
    }
}