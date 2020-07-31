package com.example.neighborhoodwork.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_samouczek.*
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.Models.UserModel
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.user
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Samouczek : AppCompatActivity() {

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
                auth = FirebaseAuth.getInstance()
                val obecny = auth.currentUser
                val fireuserBase = FirebaseDatabase.getInstance()
                userRef = fireuserBase.getReference("Users")

                val doWczytania = UserModel(
                    0.0, 0, 0, 0, 0,
                    " ${editText3.text}", user.avatarPhotoURL
                )

                if(obecny!=null){
                    var id = obecny.displayName.toString()
                   baton1.text = dane.UriBuffer.toString()
                    userRef.child(id).child("Data").setValue(doWczytania)
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



