package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.AuthListener
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityRegisterBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var authViewModel: AuthViewModel
    private lateinit var registerButton: CircularProgressButton
    private lateinit var backToLoginButton: AppCompatButton
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var userID: String
    private lateinit var password: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        //inisiasi AuthViewModel
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.authListener = this

        //inisiasi views
        registerButton = binding.buttonRegister
        backToLoginButton = binding.buttonBackToLogin!!
        email = binding.registerEmailField
        password = binding.registerPasswordField
        name = binding.registerNameField

        registerButton.setOnClickListener {
            authViewModel.onRegisterButtonClick(
                it,
                name.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
        }

        backToLoginButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun registerFieldCheck(email: String?, password: String?, name: String?): Boolean {
        if (name.isNullOrEmpty()) {
            this.name.setError(getString(R.string.nama_kosong))
            return true
        }
        if (email.isNullOrEmpty()) {
            this.email.setError(getString(R.string.email_kosong))
            return true
        }

        if (password.isNullOrEmpty()) {
            this.password.setError(getString(R.string.password_kosong))
            return true
        }
        if (password.length < 6) {
            this.password.setError(getString(R.string.password_kurang))
            return true
        }
        return false
    }

    override fun loginFieldCheck(email: String?, password: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onStarted(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(getString(R.string.registrasi_gagal))
                }
            }
    }

    override fun onSuccess() {
        // Sign in success, update UI with the signed-in user's information
        Log.d("Register Activity", "createUserWithEmail:success")

        userID = mAuth.currentUser!!.uid
        var documentReference: DocumentReference = fStore.collection("users").document(userID)
        var user: HashMap<String, String> = HashMap<String, String>()

        var nama: String = this.name.text.toString()
        var email: String = this.email.text.toString()

        user.put("full_name", nama)
        user.put("email", email)

        documentReference.set(user).addOnSuccessListener {
            Log.d("Storing Data Activity", "Succes to storing user data")
            Toast.makeText(
                applicationContext,
                getString(R.string.registrasi_berhasil) + nama,
                Toast.LENGTH_SHORT
            ).show()
        }
        var intent = Intent(this, HomeActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }

    override fun onFailure(message: String) {
        Log.w("Register Activity", "createUserWithEmail:failure")
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}