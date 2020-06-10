package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.neighborhoodwor.ZadanieModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_dodaj_zlecenie.*
import java.sql.Time
import java.util.*

class DodajZlecenie : AppCompatActivity() {

    lateinit var myRef : DatabaseReference
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_zlecenie)

        val fireBase = FirebaseDatabase.getInstance()
        myRef = fireBase.getReference("Zadania")

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser


        WpierdolDoBazy.setOnClickListener {
            val opis = Etx5Opis.text.toString()
            val img = Etx5Img.text.toString().toInt()
            val length = Etx5Length.text.toString().toInt()
            val wynagrodzenie = Etx5Wynagrodzenie.text.toString().toInt()
            val x = Etx5X.text.toString()
            val y = Etx5Y.text.toString()
            val title = Etx5Title.text.toString()
            val time = Etx5Time.drawingTime
            val fireBaseInput = ZadanieModel( x , y, opis,  wynagrodzenie, img, length, time, title, "${losujID(currentUser,time)}")
            myRef.child("${Date().time}").setValue(fireBaseInput)
            val Mapa = Intent(applicationContext, Home::class.java)
            startActivity(Mapa)
            finish()
        }
    }

    private fun losujID(currentUser: FirebaseUser?, time : Long) : String{
        var id = "${currentUser?.providerId}$time"
        return id
    }
}
