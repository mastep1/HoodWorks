package com.example.neighborhoodwork.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neighborhoodwork.R
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

        BT1nowynapokladzie.setOnClickListener {
            var intent = Intent(this, SamouczekAvater::class.java)
            startActivity(intent)
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
