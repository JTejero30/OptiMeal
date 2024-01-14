package com.example.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel(){


    private val _userEmail = MutableLiveData<String>().apply {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        value = auth.currentUser?.email
    }
    val userEmail: LiveData<String> = _userEmail
}