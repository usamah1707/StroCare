package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.viewmodel

import android.app.Application
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.HomeActivity
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.R
import id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.strocare.data.AuthListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AuthViewModel (application: Application) : AndroidViewModel(application) {

    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View, email: String, password: String){
        if (authListener?.loginFieldCheck(email,password)!!){
            return
        }
        authListener?.onStarted(email,password)
    }

    fun onRegisterButtonClick(view: View, name: String, email: String, password: String){
        if (authListener?.registerFieldCheck(email,password,name)!!){
            return
        }
        authListener?.onStarted(email,password)
    }

    fun retriveInfo(fStore: FirebaseFirestore, mAuth: FirebaseAuth, tvNamaAkun: TextView, tvEmailAkun: TextView) {
        viewModelScope.launch(Dispatchers.IO) {
            var documentReference : DocumentReference = fStore.collection("users").document(mAuth.currentUser!!.uid)
            documentReference.addSnapshotListener(EventListener<DocumentSnapshot>
            { documentSnapshot: DocumentSnapshot?, _ ->
                var name: String? = documentSnapshot?.getString("full_name")
                var email: String? = documentSnapshot?.getString("email")
                tvNamaAkun.text = name?.capitalize()
                tvEmailAkun.text = email
            })
        }
    }
}