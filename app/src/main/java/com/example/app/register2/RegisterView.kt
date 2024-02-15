package com.example.app.register2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.app.databinding.ActivityRegisterViewBinding
import com.example.app.mainActivity.Inicio
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class RegisterView : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterViewBinding
    private val fragmentManager = supportFragmentManager


    private lateinit var googleSignInClient : GoogleSignInClient



    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterViewBinding.inflate(layoutInflater)
        val screenSplash = installSplashScreen()

        screenSplash.setKeepOnScreenCondition{false}
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
        val screenSplash = installSplashScreen()

        screenSplash.setKeepOnScreenCondition{true}

        val inicioActivity = Intent(this, Inicio::class.java)
        startActivity(inicioActivity)
        finish()

    }



}