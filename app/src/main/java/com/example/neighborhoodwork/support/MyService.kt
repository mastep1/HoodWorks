package com.example.neighborhoodwork.support

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.neighborhoodwor.ZadanieModel
import com.example.neighborhoodwork.Activities.ChatView
import com.example.neighborhoodwork.Adapters.ChatMenagerAdapter
import com.example.neighborhoodwork.Adapters.ChatViewAdapter
import com.example.neighborhoodwork.Adapters.OnSelectConConversation
import com.example.neighborhoodwork.Adapters.OnSelectConConversationV
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.Models.UserHome
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_home.*


class MyService : Service(), OnSelectConConversation, OnSelectConConversationV {

    lateinit var sqlUser: SQL_USER

    override fun onCreate() {
        super.onCreate()

        dateUser(dane.currentUser)

        checkNewTask(dane.googleMap)
        checkReciveMessage()
        downloadSQLUSER()
    }

    fun downloadSQLUSER(){
        sqlUser = SQL_USER(this)
        sqlUser.readOut()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service destroyed by user.", Toast.LENGTH_LONG).show()
    }

    fun dateUser(currentUser: FirebaseUser?){

        lateinit var contactsBase : SQL_CONTACTS
        lateinit var messageBase : SQL_MESSAGE
        lateinit var userData: DatabaseReference

        if (currentUser != null) {
            val fireuserBase = FirebaseDatabase.getInstance()

            userData = fireuserBase.getReference("Users").child(currentUser.displayName.toString()).child("Data")

            userData.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in dataSnapshot.children) {

                        when(i.key){
                            "opis" -> user.opis = i.value.toString()
                            "dislike" -> user.dislike = i.value.toString().toInt()
                            "like" -> user.like = i.value.toString().toInt()
                            "dni" -> user.dni = i.value.toString().toInt()
                            "ukonczone" -> user.ukonczone = i.value.toString().toInt()
                            "rating" -> user.rating = i.value.toString().toDouble()
                            "homeAddress" -> user.home.address = i.value.toString()
                            "homeAddress" -> user.home.address = i.value.toString()
                            "homeX" -> {
                                val bufferSecoundParameter = user.home.latLng.longitude
                                user.home.latLng = LatLng(i.value.toString().toDouble(), bufferSecoundParameter)
                            }
                            "homeY" -> {
                                val bufferFirstParameter = user.home.latLng.latitude
                                user.home.latLng = LatLng(bufferFirstParameter, i.value.toString().toDouble())
                            }
                            "homeType" -> {
                                 user.home.type = i.value.toString().toInt()
                            }
                        }
                    }
                }
            })

            if(currentUser.displayName != null)
            {
                user.imie = currentUser.displayName.toString()
            }
            if(currentUser.email != null)
            {
                user.email = currentUser.email.toString()
                if(!currentUser.isEmailVerified){
                    Toast.makeText(applicationContext, "Zweryfikuj swoje konto e-mail ${currentUser.email}", Toast.LENGTH_LONG).show()
                    dane.currentUser.sendEmailVerification(ActionCodeSettings.zza())
                }
            }
            if(currentUser.phoneNumber != null)
            {
                user.tel = currentUser.phoneNumber.toString()
            }


        }

        contactsBase = SQL_CONTACTS(this)
        dane.Contasts.clear()
        contactsBase.readAllUsers()

        messageBase = SQL_MESSAGE(this)
        dane.messages.clear()
        messageBase.readAllMessages(this)




    }

   

    private fun checkReciveMessage(){

        lateinit var newMessaesPath: DatabaseReference
        val fireuserBase = FirebaseDatabase.getInstance()    /// Połączenie z bazą

        newMessaesPath = fireuserBase.getReference("Users").child(dane.currentUser.displayName.toString())
            .child("Conversation")


        newMessaesPath.addValueEventListener( object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (i in dataSnapshot.children) {

                    lateinit var SQL_MESSAGE : SQL_MESSAGE


                    val element = i.getValue(MessageModel::class.java)
                    dane.avoid = 0
                   dane.messages.add(element!!)                                         /// RAM
                   SQL_MESSAGE = SQL_MESSAGE(this@MyService)                   /// SQL
                   SQL_MESSAGE.insert(element)                                          /// SQL

                   var toRemove = fireuserBase.getReference("Users").child(dane.currentUser.displayName.toString()).child("Conversation").child("${i.key}")
                   toRemove.removeValue()

                   if(!checkDoExistConversation(element.user)){
                       addConversation(element.user)
                       //rc3.adapter = ChatMenagerAdapter(this@MyService)
                   }

                     if(dane.currentActivity=="ChatMenager"){
                         dane.recycler.adapter = ChatMenagerAdapter(this@MyService)
                     }else if(dane.currentActivity=="ChatView"){
                         dane.recycler.adapter = ChatViewAdapter(this@MyService)
                     }else if(dane.currentActivity =="Home"){
                         dane.newMessage++
                         dane.tx.text = dane.newMessage.toString()
                         dane.tx.visibility = View.VISIBLE
                     }else if(dane.currentActivity =="Profil"){
                         dane.newMessage++
                         dane.tx.text = dane.newMessage.toString()
                         dane.tx.visibility = View.VISIBLE
                     }  
                }


            }
        })


    }

    private fun checkDoExistConversation(conversation : String) : Boolean{

        var size = dane.Contasts.size;  var i = 0;  while(i<size){
            if(dane.Contasts[i]==conversation){
                i = size
                return true
            }else{
                i++
            }
        }
        return false
    }
    

    private fun addConversation(newConversation : String){
        lateinit var SQL_CONTACTS_DB : SQL_CONTACTS

        dane.Contasts.add(newConversation)          /// RAM

        SQL_CONTACTS_DB = SQL_CONTACTS(this)            //// SQL
        SQL_CONTACTS_DB.insertUser("${newConversation}")        /// SQL


        dane.newContact = ""             /// wyczyszczenie zmiennej
    }

    override fun onItemClick(selectUser: String, position : Int) {
        dane.openConversation = position
        var chatView = Intent(this, ChatView::class.java)
        startActivity(chatView)
    }

    override fun onItemClickV(selectUser: String, position: Int) {
    }

    private fun checkNewTask(googleMap: GoogleMap){

        lateinit var myRef: DatabaseReference

        val fireBase = FirebaseDatabase.getInstance()
        myRef = fireBase.getReference("Zadania")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (i in dataSnapshot.children) {

                    val element = i.getValue(ZadanieModel::class.java)
                    dane.zadania.add(element!!)
                }
                znaczniki(googleMap)
            }
        })
    }

}

