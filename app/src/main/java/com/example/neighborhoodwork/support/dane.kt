package com.example.neighborhoodwork.support

import android.provider.BaseColumns
import com.example.neighborhoodwor.ZadanieModel
import com.example.neighborhoodwork.Models.MessageModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser

object dane {
    var zadania = arrayListOf<ZadanieModel>()
    var clicked : Int = 0
    var lokalizacjaAktualna = LatLng(51.0, 21.0)
    var newContact = ""
    var Contasts = arrayListOf<String>()
    lateinit var currentUser : FirebaseUser
    var openConversation = 0
    var messages = arrayListOf<MessageModel>()
    var indexMessage = 0
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

object SQL_BD_CONTACT{

    class Contacts : BaseColumns {
        companion object {
            val TABLE_NAME = "contacts"
            val CONTACT_ROW = "contactRow"
        }
    }
}


object SQL_DB_MESSAGE {

    class Message : BaseColumns {
        companion object {
            val TABLE_NAME = "message"
            val MESSAGE = "message"
            val THIS_USER = "thisUser"
            val TIME = "time"
            val USER = "user"
            
        }
    }
}


