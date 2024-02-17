package com.example.app.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.app.R
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

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    //private val db = Firebase.firestore
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
                    (this as? RegisterActivity)?.showAlert()
                }
            }
        }
    }
    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }
    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Log.d("comprobar", "Error ${task.exception.toString()}")
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("comprobar", "Correct Login ${account.email}")
                val intent = Intent(this, Inicio::class.java)
                startActivity(intent)
            } else {
                Log.d("comprobar", "Error UI ${it.exception.toString()}")
            }
        }
    }
    private fun comprobarUser()  {
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

}