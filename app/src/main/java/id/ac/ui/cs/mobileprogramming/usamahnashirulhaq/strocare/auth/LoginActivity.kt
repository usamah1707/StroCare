package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.auth

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.google.android.material.snackbar.Snackbar
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.databinding.ActivityLoginBinding
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.home.HomeActivity
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var animationDrawable: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.statusBarColor = Color.TRANSPARENT
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        var loginButton: CircularProgressButton = binding.buttonSignIn

        loginButton.setOnClickListener(View.OnClickListener { view: View ->
            loginButtonOnClick(loginButton, binding.buttonRegister, view)
        })

        binding.buttonRegister.setOnClickListener(View.OnClickListener { view: View ->
            openRegisterPage(view)
        })
    }

    fun loginButtonOnClick(button: CircularProgressButton, registerButton: Button, view: View){
        button.startAnimation()

        var intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Toast.makeText(this,"Login berhasil", Toast.LENGTH_LONG).show()

//        registerButton.isEnabled = false
    }

    fun openRegisterPage(view: View){

        startActivity(Intent(this, RegisterActivity::class.java))
    }
}