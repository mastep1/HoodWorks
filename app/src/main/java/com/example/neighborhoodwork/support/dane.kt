package com.example.neighborhoodwork.support

import android.net.Uri
import android.provider.BaseColumns
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neighborhoodwor.ZadanieModel
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.Models.UserHome
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import java.io.File
import java.net.URI
import java.net.URL

object dane {
    var zadania = arrayListOf<ZadanieModel>()
    var clicked : Int = 0
    var lokalizacjaAktualna = LatLng(51.0, 21.0)
    var newContact = ""
    var Contasts = arrayListOf<String>()
    lateinit var currentUser : FirebaseUser
    var openConversation = 0
    var messages = arrayListOf<MessageModel>()
    var avoid = 0
    var currentActivity = "Home"
    lateinit var recycler : RecyclerView
    var newMessage = 0
    lateinit var tx : TextView
    var HomeOnCreate = false
    lateinit var googleMap : GoogleMap
    lateinit var UriBuffer : Uri
    lateinit var superImage : ImageView
    var addTask = 1
    var newTask = ZadanieModel("taklubnie", "taklubnie", "","","taklubnie","","taklubnie", "taklubnie", "","")
    var arrayForAdapter = arrayListOf<String>()
    lateinit var messageListner :
    }

object time{
    var timeSet = false
    var dateSet = false
    var min = 0
    var hour = 0
    var dayMonthYear = 0.toLong()
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
    var opis = "otoopis"
    var avatarPhotoURL = "URL"
    var home = UserHome(LatLng(0.0,0.0), "", 0)
}

object SQL_BD_CONTACT{

    class Contacts : BaseColumns {
        companion object {
            val TABLE_NAME = "contacts"
            val CONTACT_ROW = "contactRow"
        }
    }
}

object SQL_DB_MESSAGES_METER{

    class MessagesMeter : BaseColumns {
        companion object {
            val TABLE_NAME = "messagesmeter"
            val NUMBER = "number"
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
            val BRING = "bring"
            val TTT = "ttt"
            val YYY = "ttt"
            val UUU = "ttt"
            val III = "iii"
        }
    }
}


object SQL_USER_OBJECT {

    class userSQL : BaseColumns {
        companion object {
            val NAZWATABELI = "nazwa"
            val IMIE = "imie"
            val EMAIL = "email"
            val TEL = "tel"
            val RATING : String = "rating"
            val UKONCZONE : String = "ukonczone"
            val DNI : String = "dni"
            val LIKE : String = "like"
            val DISLIKE : String = "dislike"
            val OPIS = "opis"
            val AVATARPHOTOURL = "avatarphotourl"
        }
    }
}


