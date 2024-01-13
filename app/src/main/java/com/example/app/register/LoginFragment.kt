package com.example.app.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentLoginBinding
import com.example.app.mainActivity.Inicio
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accederButton.setOnClickListener() {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("comprobar", "Correct Login desde el fragment ")
                    Log.d("comprobar", "Simplemente estoy probando, borrar estos ")

                    val intent = Intent(activity, Inicio::class.java)
                    startActivity(intent)
                } else {
                    Log.d("comprobar", "Error")
                    (activity as? RegisterView)?.showAlert()
                }
            }
        }
        binding.irRegister.setOnClickListener() {
            (activity as? RegisterView)?.replaceFragment(RegisterFragment())
        }
    }
}