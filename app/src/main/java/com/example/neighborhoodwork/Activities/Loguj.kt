package com.example.neighborhoodwork.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.neighborhoodwork.R
import kotlinx.android.synthetic.main.activity_loguj.*

class Loguj : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loguj)


        tx2Zaloguj.setOnClickListener {
            var nowaAktywnosc = Intent(applicationContext, Home::class.java)
            startActivity(nowaAktywnosc)
        }
    }

}
