package com.example.neighborhoodwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        updateUI(currentUser)
        tx1Email.setOnClickListener {
            var nowaAktywnosc = Intent(applicationContext, utworzKontoEmail::class.java)
            startActivity(nowaAktywnosc)
        }
        tx1ZalogujSie.setOnClickListener{
            var nowaAktywnosc = Intent(applicationContext, Loguj::class.java)
            startActivity(nowaAktywnosc)
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
