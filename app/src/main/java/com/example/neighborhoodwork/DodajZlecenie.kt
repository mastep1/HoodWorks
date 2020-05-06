package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_dodaj_zlecenie.*
import java.util.*

class DodajZlecenie : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_zlecenie)
        val fireBase = FirebaseDatabase.getInstance()
        myRef = fireBase.getReference("Zadania")
        WpierdolDoBazy.setOnClickListener {
            val x = xxx.text.toString()
            val y = yyy.text.toString()
            val fireBaseInput = Zadanie( x , y )
            myRef.child("${Date().time}").setValue(fireBaseInput)
            val Mapa = Intent(applicationContext, Home::class.java)
            startActivity(Mapa)
        }
    }
}
