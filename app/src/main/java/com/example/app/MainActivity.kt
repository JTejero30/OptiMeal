package com.example.app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint

class MainActivity : AppCompatActivity() {
    // Declaración de la variable 'question'
    private lateinit var question:   TextView
    private lateinit var progress: ProgressBar
    private lateinit var q1: LinearLayout
    private lateinit var q2: ConstraintLayout
    private lateinit var q4: LinearLayout
    private lateinit var buttonNext: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById<ProgressBar>(R.id.progress)
        q1 = findViewById(R.id.q1)
        q2 = findViewById(R.id.q2)
        q4 = findViewById(R.id.q4)
    }

    @SuppressLint("SetTextI18n")
    fun nextQuestion(view: View) {
        progress.incrementProgressBy(1)

        when (progress.progress) {
            0 ->
                question.text = "¿Tienes alguna preferencia dietética?"

            1 -> {
                question.text = "¿Alguna alergia que debamos conocer?"
                ocultarGrupo(q1)
                q2.visibility=View.VISIBLE
            }

            2 -> {
                question.text = "Introduce tus datos"
                ocultarGrupo(q2)
                q4.visibility=View.VISIBLE
            }

            3 -> {
                question.text = ""
                mostrarGrupo(q2)
            }
        }
    }

    fun mostrarGrupo(grupo: LinearLayout) {
        for (i in 0 until grupo.childCount) {
            val button = grupo.getChildAt(i)
            button.visibility = View.VISIBLE
        }
    }

    fun ocultarGrupo(grupo: LinearLayout) {
        for (i in 0 until grupo.childCount) {
            val button = grupo.getChildAt(i)
            button.visibility = View.GONE
        }
    }

    fun mostrarGrupo(grupo: ConstraintLayout) {
        for (i in 0 until grupo.childCount) {
            val button = grupo.getChildAt(i)
            button.visibility = View.VISIBLE
        }
    }

    fun ocultarGrupo(grupo: ConstraintLayout) {
        for (i in 0 until grupo.childCount) {
            val button = grupo.getChildAt(i)
            button.visibility = View.GONE
        }
    }
}