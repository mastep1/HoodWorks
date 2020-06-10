package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
            var nowaAktywnosc3 = Intent(applicationContext, Loguj::class.java)
            startActivity(nowaAktywnosc3)
        }

        l1Ikony.setOnClickListener {
            var samo = Intent(applicationContext, Samouczek::class.java)
            startActivity(samo)
        }
    }

    fun updateUI(currentUser : FirebaseUser?){
        if(currentUser != null){
        var nowaAktywnosc = Intent(applicationContext, Home::class.java)
        startActivity(nowaAktywnosc)
        finish()
        }
    }
}
