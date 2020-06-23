package com.example.neighborhoodwork

import android.provider.BaseColumns
import com.example.neighborhoodwor.ZadanieModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser

object dane {
    var zadania = arrayListOf<ZadanieModel>()
    var clicked : Int = 0
    var lokalizacjaAktualna = LatLng(51.0, 21.0)
    var newContact = ""
    var Contasts = arrayListOf<String>()
    lateinit var currentUser : FirebaseUser
}


object user {
    var imie = ""
    var email = ""
    var tel = ""
    var rating : Double = 0.0
    var ukonczone : Int = 0
    var dni : Int = 0
    var like : Int = 0
    var dislike : Int = 0
    var opis = ""
}

object SQL_BD_CONTACT {

    class Contacts : BaseColumns {
        companion object {
            val TABLE_NAME = "contacts"
            val CONTACT_ROW = "contactRow"
        }
    }
}


