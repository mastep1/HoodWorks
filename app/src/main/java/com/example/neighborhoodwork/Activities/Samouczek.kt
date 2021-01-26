package com.example.neighborhoodwork.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.example.neighborhoodwork.Models.UserHome
import kotlinx.android.synthetic.main.activity_samouczek.*
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.Models.UserModel
import com.example.neighborhoodwork.support.RoomDatabaseForUsersAvatars.DataEntityUsersAvatars
import com.example.neighborhoodwork.support.RoomDatabaseForUsersAvatars.UsersAvatarsDatabase
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.user
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class Samouczek : AppCompatActivity(){

    lateinit var userRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var providers: List<AuthUI.IdpConfig>
    val MY_REQUEST_CODE: Int = 2807

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_samouczek)

        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )
        showSignInOptions()

        baton1.setOnClickListener {
                textView1.text = "Kliknij button2!"

                    try{
                        //  Pobieranie użytkownika
                        auth = FirebaseAuth.getInstance()
                        val currentU = auth.currentUser

                        // Pobieranie ścieżki do Firabase i ustawianie jej na users
                        val fireuserBase = FirebaseDatabase.getInstance()
                        userRef = fireuserBase.getReference("Users")

                        if(currentU!=null){


                                val UName = currentU.displayName.toString()

                                /// Zbieranie danych w model użytkownika w calu wysłania do FireBase, RAM i SQL
                                val toInsert = DataEntityUsersAvatars(UName, dane.photoInBytes!!.toString(), 0.0, 0, 0, 0,
                                    0, editText3.text.toString(), 0.0, 0.0, 0, "", 1)

                                ////Ustawianie licznika wiadomości na 1
                                 userRef.child(UName).child("messagesMeter").setValue("1")

                                ///Ram
                                 dane.currentUsersData = toInsert
                            
                                ///Wysyłanie danych użytkownika do Firebase
                                userRef.child(UName).child("Data").setValue(toInsert)


                                 //// SQL
                                val local = UsersAvatarsDatabase.getInstance(applicationContext)
                                 GlobalScope.launch {
                                     local.usersAvatarsDao().insertOrUpdate(toInsert)
                                }

                            /*





                        Toast.makeText(applicationContext, "I'm right here", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(applicationContext, "current Is null", Toast.LENGTH_LONG).show()
                         */
                        
                        }



                    }
                    catch(e: Exception){
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show(); Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        Log.e("KRYSTIAN", e.message)
                    }
                
            }

        baton2.setOnClickListener {
            val home = Intent(applicationContext, Home::class.java)
            startActivity(home)
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showSignInOptions() {
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), MY_REQUEST_CODE
        )
    }
}



