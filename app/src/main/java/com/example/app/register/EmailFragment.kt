package com.example.app.register

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.FragmentRegisterBinding
import com.example.app.mainActivity.Inicio
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EmailFragment : Fragment() {
    private lateinit var _binding: FragmentRegisterBinding
    private val binding get() = _binding!!
    private val db = Firebase.firestore

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var password = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.passwordEditText.setOnClickListener{
            mostrarContrasena()
        }
        return binding.root
    }

    private fun mostrarContrasena() {
        password = !password
        val ver : Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ver,null)
        val esconder : Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.esconder,null)

        if(password){
            binding.passwordEditText.setCompoundDrawablesWithIntrinsicBounds(ver,null,null,null)
            binding.passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }else{
            binding.passwordEditText.setCompoundDrawablesWithIntrinsicBounds(esconder,null,null,null)
            binding.passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.registrarButton.setOnClickListener() {
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        (activity as? RegisterActivity)?.nextQuestion(true)
                        val user = (activity as? RegisterActivity)?.createUserFromJson()
                        Log.d("userLog", user.toString())
                        db.collection("users").document(auth.currentUser?.uid.toString())
                            .set(user!!)
                        val intent = Intent(activity, Inicio::class.java)
                        startActivity(intent)
                    } else {

                        if (binding.passwordEditText.text.length < 6) {
                            (activity as? RegisterActivity)?.showAlertError(
                                "Vaya!",
                                "Pruebe con una contraseña más segura"
                            )
                        } else {
                            (activity as? RegisterActivity)?.showAlertError(
                                "Vaya!",
                                "Se ha producido un error con la autenticacion del usuario"
                            )
                        }
                    }
                }
            } else {
                (activity as? RegisterActivity)?.showAlertError(
                    "Vaya!",
                    "Rellene todos los campos"
                )
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding.SignInButton.setOnClickListener() {
            signInGoogle()
            googleSignInClient.signOut()
        }


    }


    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        Log.d("LogInGoogle", "Lanzo singIn launcher. signInIntent->$signInIntent")
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(
                "LogInGoogle",
                "Estoy dentro de launcher registerForActivityResult.result->$result"
            )
            Log.d("LogInGoogle", "Estoy dentro de launcher result.resultCode->${result.resultCode}")

            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                Log.d("LogInGoogle", "Estoy dentro del IF en launcher task->${task}")

                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        Log.d("LogInGoogle", "Estoy dentro de handleResults task->${task}")

        if (task.isSuccessful) {

            val account: GoogleSignInAccount? = task.result
            Log.d("LogInGoogle", "task isSuccesful account->${account}")

            if (account != null) {

                Log.d("LogInGoogle", "updateUI->${account}")

                updateUI(account)

            }
        } else {
            Log.d("LogInGoogle", "Error ${task.exception.toString()}")
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        Log.d("LogInGoogle", "-----------------------------")

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        Log.d("LogInGoogle", "updateUI credential->${credential}")
        Log.d("LogInGoogle", "account-> ${account},${account.id},${account.email}")

        Log.d("LogInGoogle", "-----------------------------")



        db.collection("users")
            .whereEqualTo("id", account.id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("LogInGoogle", "Existe")


                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Vaya!")
                    builder.setMessage("Parece que ya hay una cuenta con este correo...")
                    builder.setPositiveButton("Cancelar", null)
                    builder.setNegativeButton(
                        "Ir a login",
                        DialogInterface.OnClickListener { dialog, which ->

                            val intent = Intent(activity, LoginActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        })
                    val dialog: AlertDialog = builder.create()
                    dialog.show()


                    return@addOnSuccessListener

                }
                Log.e("LogInGoogle", "No existe")
                //Esto es lo que crea el user en Autentication
                auth.signInWithCredential(credential).addOnCompleteListener {
                    Log.d(
                        "LogInGoogle",
                        "Estoy dentro de auth.signInWithCredential(credential)  auth.signInWithCredential(credential)->${
                            auth.signInWithCredential(credential)
                        }"
                    )

                    if (it.isSuccessful) {
                        Log.d("LogInGoogle", "Correct Login ${account.email}")
                        Log.d("LogInGoogle", "Correct Login ${auth.currentUser?.uid}")
                        val user =
                            (activity as? RegisterActivity)?.createUserFromJsonGoogle(account.id!!)
                        Log.d("userLog", user.toString())
                        db.collection("users").document(auth.currentUser?.uid.toString())
                            .set(user!!)
                        val intent = Intent(activity, Inicio::class.java)
                        requireActivity().finish()
                        startActivity(intent)
                    } else {
                        Log.d("LogInGoogle", "Error UI ${it.exception.toString()}")


                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("LogInGoogle", "Error checking user data: $exception")


            }

    }
}