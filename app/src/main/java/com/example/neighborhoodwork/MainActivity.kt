package com.example.neighborhoodwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tx1Email.setOnClickListener {
            var nowaAktywnosc = Intent(applicationContext, utworzKontoEmail::class.java)
            startActivity(nowaAktywnosc)
        }
        tx1ZalogujSie.setOnClickListener{
            var nowaAktywnosc = Intent(applicationContext, Loguj::class.java)
            startActivity(nowaAktywnosc)
        }
    }
}
