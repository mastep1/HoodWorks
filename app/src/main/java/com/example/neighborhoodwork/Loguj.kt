package com.example.neighborhoodwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_loguj.*
import kotlinx.android.synthetic.main.activity_utworz_konto_email.*

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
