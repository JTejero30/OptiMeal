package com.example.app.register2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.FragmentLoginBinding
import com.example.app.mainActivity.Inicio
import com.example.app.register.Email
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {
    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

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
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding.SignInButton.setOnClickListener() {
            signInGoogle()
            googleSignInClient.signOut()
        }

        binding.accederButton.setOnClickListener() {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("comprobar", "Correct Login desde el fragment ")
                    val intent = Intent(activity, Inicio::class.java)
                    startActivity(intent)
                } else {
                    Log.d("comprobar", "Error")
                    (activity as? RegisterView)?.showAlert()
                }
            }
        }
        binding.irRegister.setOnClickListener() {
            (activity as? RegisterView)?.replaceFragment(Email())
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                Log.d("comprobar", "task= $task data= ${result.data}")
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Log.d("comprobar", "Error ${task.exception.toString()}")
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("comprobar", "Correct Login ${account.email}")
                val intent = Intent(activity, Inicio::class.java)
                startActivity(intent)
            } else {
                Log.d("comprobar", "Error UI ${it.exception.toString()}")
            }
        }
    }
}