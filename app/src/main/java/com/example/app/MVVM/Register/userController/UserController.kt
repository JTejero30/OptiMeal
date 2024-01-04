package com.example.app.MVVM.Register.userController

import com.google.firebase.auth.FirebaseUser

object UserController {
    var userLog: FirebaseUser? = null

    fun logIn(user: FirebaseUser) {
        userLog = user
    }
}