package com.example.app

data class User(
    /*val uid: String?,
    val email: String?,
    val personalData1: String?,
    val altura : Float?,
    val peso : Float?,
    val fecha: String?,
    val sex: String?,
    val alergias: List<String>?,
    val objetive: String?,*/

    val uid: String?,
    val email: String?,
    val dietetic_preference: String,
    val sex :String,
    val peso:Double,
    val altura:Double,
    val edad:Int,
    val actividad:Double,
    val deficit :Double,
    val alergiasArray :String

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
