package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        updateUI(currentUser)

        tx1ZalogujSie.setOnClickListener{
            var nowaAktywnosc = Intent(applicationContext, Loguj::class.java)
            startActivity(nowaAktywnosc)
        }

        Constraint1ikony.setOnClickListener {
            var Home = Intent(applicationContext, Home::class.java)
            startActivity(Home)
        }


    }

    fun updateUI(currentUser : FirebaseUser?){
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                var nowaAktywnosc = Intent(applicationContext, Home::class.java)
                startActivity(nowaAktywnosc)
                finish()
            };else {
                var nowaAktywnosc = Intent(applicationContext, Home::class.java)
                startActivity(nowaAktywnosc)
                finish()
               Toast.makeText(applicationContext, "Zweryfikuj swojego E-maila", Toast.LENGTH_LONG).show()
            }
        }
    }


}
