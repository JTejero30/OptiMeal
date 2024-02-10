package com.example.app

data class User(

    val uid: String?,
    val email: String?,
    val dietetic_preference: String,
    val sex :String,
    val peso:Double,
    val altura:Double,
    val edad:Int,
    val actividad:Double,
    val deficit :Double,
    val alergiasArray :String,
    val TDEE : Double

/*     auth.currentUser?.uid,
            auth.currentUser?.email,
            dietetic_preference,
            sex,
            peso,
            altura,
            edad,
            actividad,
            deficit,
            alergiasArray,*/
    )
