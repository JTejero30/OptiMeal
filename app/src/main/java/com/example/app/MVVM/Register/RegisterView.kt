package com.example.app.MVVM.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.app.MVVM.Register.userController.UserController
import com.example.app.R
import com.example.app.databinding.ActivityRegisterViewBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterView : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterViewBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val user = Firebase.auth.currentUser

        binding.registrarButton.setOnClickListener() {
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {

                        //binding.textoRegistro.text = "Registrado correctamente"

                        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                            binding.emailEditText.text.toString(),
                            binding.passwordEditText.text.toString()
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                if (user != null) {
                                    UserController.logIn(user)
                                    val questionsActivity: Intent = Intent(this, Register::class.java)
                                    startActivity(questionsActivity)
                                }
                            } else {
                                showAlert()
                            }
                        }
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error con la autenticacion del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}