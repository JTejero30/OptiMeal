package com.example.app.MVVM.Register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

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
        val user = Firebase.auth.currentUser
        Log.d("comprobar", "Login Fragment Show: $user")

        binding.accederButton.setOnClickListener() {
            (activity as? RegisterView)?.checkLogin(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString())
        }
    }
}