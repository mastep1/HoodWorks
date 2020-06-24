package com.example.neighborhoodwork.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neighborhoodwor.ZadanieModel
import com.example.neighborhoodwork.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_dodaj_zlecenie.*
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

            val timestamp = System.currentTimeMillis()


            val opis = Etx5Opis.text.toString()
            val img = Etx5Img.text.toString()
            val length = Etx5Length.text.toString()
            val wynagrodzenie = Etx5Wynagrodzenie.text.toString()
            val x = Etx5X.text.toString()
            val y = Etx5Y.text.toString()
            val title = Etx5Title.text.toString()
            val time = Etx5Time.text.toString()
            val fireBaseInput = ZadanieModel( x = x , y = y, opis = opis,  wynagrodzenie = wynagrodzenie, img = img,
                length = length, time = time, title = title, ID =  timestamp.toString(), pracodawca = currentUser!!.displayName.toString())
            myRef.child("${Date().time}").setValue(fireBaseInput)
            val Mapa = Intent(applicationContext, Home::class.java)
            startActivity(Mapa)
            finish()
        }

        BT5Common.setOnClickListener {
            myRef.child("${Date().time}").setValue(ZadanieModel(pracodawca = currentUser!!.displayName.toString()))
            val Mapa = Intent(applicationContext, Home::class.java)
            startActivity(Mapa)
            finish()
        }
    }


}
