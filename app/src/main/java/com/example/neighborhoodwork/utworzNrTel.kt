package com.example.neighborhoodwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_utworz_nr_tel.*

class utworzNrTel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utworz_nr_tel)
        tx7Wolisz.setOnClickListener {
            var nowaAktywnosc = Intent(applicationContext, utworzKontoEmail::class.java)
            startActivity(nowaAktywnosc)
        }
    }
}
