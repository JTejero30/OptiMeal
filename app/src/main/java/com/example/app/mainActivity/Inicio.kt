package com.example.app.mainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.app.MVVM.Register.RegisterView
import com.example.app.databinding.ActivityInicioBinding
import com.google.firebase.auth.FirebaseAuth

class Inicio : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
           binding.textView2.text=currentUser.email
        }
        binding.button.setOnClickListener(){
            auth.signOut()
            val intent = Intent(this, RegisterView::class.java)
            startActivity(intent)
        }
    }
}