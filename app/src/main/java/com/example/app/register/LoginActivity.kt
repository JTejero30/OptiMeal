package com.example.app.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.app.R
import com.example.app.User
import com.example.app.databinding.ActivityLoginBinding
import com.example.app.databinding.ActivityRegisterBinding
import com.example.app.mainActivity.Inicio
import com.example.app.register2.RegisterView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        comprobarUser()
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.SignInButton.setOnClickListener() {
            signInGoogle()
            googleSignInClient.signOut()
        }
        binding.irRegister.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.accederButton.setOnClickListener() {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("comprobar", "Correct Login desde el fragment ")
                    val intent = Intent(this, Inicio::class.java)
                    startActivity(intent)
                } else {
                    Log.d("comprobar", "Error")
                    showAlert()
                }
            }
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        Log.d("LogInGoogle", "Lanzo singIn launcher. signInIntent->$signInIntent")
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(
                "LogInGoogle",
                "Estoy dentro de launcher registerForActivityResult.result->$result"
            )
            Log.d("LogInGoogle", "Estoy dentro de launcher result.resultCode->${result.resultCode}")

            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                Log.d("LogInGoogle", "Estoy dentro del IF en launcher task->${task}")

                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        Log.d("LogInGoogle", "Estoy dentro de handleResults task->${task}")

        if (task.isSuccessful) {

            val account: GoogleSignInAccount? = task.result
            Log.d("LogInGoogle", "task isSuccesful account->${account}")

            if (account != null) {

                Log.d("LogInGoogle", "updateUI->${account}")

                updateUI(account)

            }
        } else {
            Log.d("LogInGoogle", "Error ${task.exception.toString()}")
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        Log.d("LogInGoogle", "-----------------------------")

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        Log.d("LogInGoogle", "updateUI credential->${credential}")
        Log.d("LogInGoogle", "account-> ${account},${account.id},${account.email}")

        Log.d("LogInGoogle", "-----------------------------")



        db.collection("users")
            .whereEqualTo("id", account.id) // Adjust the field name as per your database
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("LogInGoogle", "Existe")

                    //Esto es lo que crea el user en Autentication
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        Log.d(
                            "LogInGoogle",
                            "Estoy dentro de auth.signInWithCredential(credential)  auth.signInWithCredential(credential)->${
                                auth.signInWithCredential(credential)
                            }"
                        )

                        if (it.isSuccessful) {
                            Log.d("LogInGoogle", "Correct Login ${account.email}")
                            Log.d("LogInGoogle", "Correct Login ${auth.currentUser?.uid}")

                            val intent = Intent(this, Inicio::class.java)
                            startActivity(intent)
                        } else {
                            Log.d("LogInGoogle", "Error UI ${it.exception.toString()}")
                        }
                    }
                    return@addOnSuccessListener // Exit the loop if a matching document is found
                }

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("Vaya! Parece que no estás registrado...")
                builder.setPositiveButton("Cancelar", null)
                builder.setNegativeButton(
                    "Ir a registro",
                    DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent)
                        finish()
                    })
                val dialog: AlertDialog = builder.create()
                dialog.show()
                /*
                val dietetic_preference: String = ""
                val sex =""
                val peso = 0.0
                val altura = 0.0
                val edad =  0
                val actividad =  0.0
                val deficit =  0.0
                val alergiasArray = ""
                val userF= User(
                    account.id,
                    account.id,
                    account.email,
                    dietetic_preference,
                    sex,
                    peso,
                    altura,
                    edad,
                    actividad,
                    deficit,
                    alergiasArray,
                    2.2
                )
                db.collection("users").add(userF)*/
                Log.e("LogInGoogle", "No existe")
            }
            .addOnFailureListener { exception ->
                // Handle failure gracefully
                Log.e("LogInGoogle", "Error checking user data: $exception")
                // You might want to show an error message to the user or retry
            }


    }

    private fun comprobarUser() {
        //comprobamos si hay un usuario ya logeado y si es asi lo mandamos a inicio
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, Inicio::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Al no poner nada estoy evitando que el user pueda retroceder a la pantalla anterior
    }

    public fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error con la autenticacion del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}