package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.AuthListener
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityLoginBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.Constants
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.util.NetworkConnection
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel.AuthViewModel


class LoginActivity : AppCompatActivity(), AuthListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var mAuth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel
    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var appName: TextView
    private lateinit var loginButton: CircularProgressButton
    private lateinit var networkConnection: NetworkConnection
    private var loginCalled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        networkConnection = NetworkConnection(applicationContext)

        //inisiasi AuthViewModel
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.authListener = this

        //inisiasi views
        loginButton = binding.buttonSignIn
        email = binding.loginEmailField
        password = binding.loginPasswordField
        appName = binding.tvAppNameBelow!!

        appName.setText(Constants.APP_NAME)

        if (mAuth.currentUser != null) {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }

        loginButton.setOnClickListener {
            loginCalled = false
            networkConnection.observe(this, Observer { isConnected ->
                if (isConnected) {
                    if (!loginCalled) {
                        authViewModel.onLoginButtonClick(
                            it,
                            email.text.toString(),
                            password.text.toString()
                        )
                        loginCalled = true
                    }
                } else {
                    noConnectionDialogBuilder()
                }
            })
        }

        binding.buttonRegister.setOnClickListener(View.OnClickListener { view: View ->
            openRegisterPage(view)
        })
    }

    private fun noConnectionDialogBuilder() {
        val image = ImageView(this)
        image.setImageResource(R.drawable.image_no_network_available)

        val builder: AlertDialog.Builder =
            AlertDialog.Builder(this)
                .setView(image)
                .setTitle(R.string.jaringan_tidak_ada)
                .setPositiveButton(R.string.dialog_dismiss_button) { dialog, _ ->
                    dialog.dismiss()
                }
        builder.create().show()
    }

    fun openRegisterPage(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun registerFieldCheck(email: String?, password: String?, name: String?): Boolean {
        TODO("Not implemented here, open RegisterActivity instead")
    }

    override fun loginFieldCheck(email: String?, password: String?): Boolean {
        if (email.isNullOrEmpty()) {
            this.email.setError(getString(R.string.email_kosong))
            return true
        }
        if (password.isNullOrEmpty()) {
            this.password.setError(getString(R.string.password_kosong))
            return true
        }
        return false
    }

    override fun onStarted(email: String, password: String) {
        loginButton.startAnimation()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(getString(R.string.login_gagal))
                }
            }
    }

    override fun onSuccess() {
        var intent = Intent(this, HomeActivity::class.java)
        //push home activity to top and remove login activity from stack
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Toast.makeText(this, R.string.login_berhasil, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String) {
        loginButton.revertAnimation()
        loginButton.background = getDrawable(R.drawable.shape_button_login)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}