package com.example.neighborhoodwork.Fragments

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neighborhoodwork.Activities.ChatMenager
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.user
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.info_window_big.*
import java.util.*


class BigInfoWindow(contextBuffor : Context) : Fragment() {

    var xxxxxxx = contextBuffor

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_window_big, container, false )

    }

    override fun onStart() {
        super.onStart()

        setAll(xxxxxxx)


    }

    fun setAll(contextBuffor : Context){

        BT6Chat.setOnClickListener {
            dane.newContact = "${dane.zadania[dane.clicked].pracodawca}"
            val chat = Intent(context, ChatMenager::class.java)
            startActivity(chat)
        }

        tx6LengthV.text = "${dane.zadania[dane.clicked].length} MIN"
        tx6WynagrodzenieV.text = "${dane.zadania[dane.clicked].wynagrodzenie} PLN"
        tx6StartV.text = dane.zadania[dane.clicked].time
        tx6OpisV.text = dane.zadania[dane.clicked].opis
        tx6Imie.text = dane.zadania[dane.clicked].pracodawca

        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(context, Locale.getDefault())
        addresses = geocoder.getFromLocation(dane.zadania[dane.clicked].x.toDouble(), dane.zadania[dane.clicked].y.toDouble(), 1)
        val address = addresses[0].getAddressLine(0)
        tx6AdresV.text = address



    val fireBase = FirebaseDatabase.getInstance()
    var myRef = fireBase.getReference("Users").child("${dane.zadania[dane.clicked].pracodawca}").child("Data").child("rating")

    myRef.addValueEventListener(object : ValueEventListener {

        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {

              val element = dataSnapshot.getValue()
              ratingBar6.rating = element.toString().toFloat()
         }
})

                        
    }


}