package com.example.app.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentRegisterBinding
import com.example.app.register2.RegisterView
import com.google.firebase.auth.FirebaseAuth

class EmailFragment : Fragment() {
    private lateinit var _binding: FragmentRegisterBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.registrarButton.setOnClickListener() {
                (activity as? RegisterActivity)?.nextQuestion()
                if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.emailEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(activity, RegisterActivity::class.java)
                            startActivity(intent)
                        } else {
                            (activity as? RegisterView)?.showAlert()
                        }
                    }
            }
        }
    }
}