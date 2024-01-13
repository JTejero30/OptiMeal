package com.example.app.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app.databinding.ActivityRegisterViewBinding
import com.example.app.mainActivity.Inicio
import com.google.firebase.auth.FirebaseAuth

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
    }

    public fun replaceFragment(fr: Fragment) {
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

    public fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error con la autenticacion del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}