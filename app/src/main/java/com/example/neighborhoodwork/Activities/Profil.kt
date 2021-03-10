package com.example.neighborhoodwork.Activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.setCurrentActivity
import com.example.neighborhoodwork.support.user
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.profil.*


class Profil : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil)

        setOnClickListner()

    }

    override fun onResume(){
        super.onResume()
        setData()
        setCurrentActivity(tx4NewMessage, "Profil")

        if(dane.newMessage!=0){
            tx4NewMessage.text = dane.newMessage.toString()
            tx4NewMessage.visibility = View.VISIBLE
        }else{
            tx4NewMessage.visibility = View.INVISIBLE
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.Menu_Mapa -> {
                val home = Intent(applicationContext, Home::class.java)
                startActivity(home)
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }
            R.id.Menu_Chat -> {
                val chat = Intent(applicationContext, ChatMenager::class.java)
                startActivity(chat)
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }
            R.id.Menu_Dodaj_Zlecenie -> {
                val dodaj = Intent(applicationContext, DodajZlecenie::class.java)
                startActivity(dodaj)
            }
            R.id.Menu_Wyloguj -> {
                AuthUI.getInstance().signOut(this@Profil).addOnCompleteListener {
                    val main = Intent(applicationContext, MainActivity::class.java)
                    startActivity(main)
                    finish()
                }.addOnFailureListener { e ->
                    Toast.makeText(this@Profil, "Wylogowano", Toast.LENGTH_SHORT).show()
                }
            }
        }
        drawer4.closeDrawer(GravityCompat.START)
        return true
    }

    fun setData(){

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()



           // var bitmap = BitmapFactory.decodeByteArray(dane.currentUserPhoto, 0, dane.currentUserPhoto.size)
           // img4ZdjProf.setImageBitmap(bitmap)


        


         /*
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/hoodworks-6948c.appspot.com/o/Images%2F1596110855207?alt=media&token=234135ad-de87-44bc-9def-6da8bd65fa06")
            .into(img4ZdjProf)

          */

       // var imageBytes = dane.currentUsersData!!.photo
       // var objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

       // img4ZdjProf.setImageBitmap(objectBitmap)

        tx4Imie.text = dane.currentUser.displayName
        ratingBar4.rating = dane.currentUsersDataUsers!!.rating.toFloat()
        tx4Ukonczone1.text = dane.currentUsersDataUsers!!.completed.toString()
        tx4Dni1.text = "${dane.currentUsersDataUsers!!.daysWithApp} dni"
        tx4Like.text = dane.currentUsersDataUsers!!.likes.toString()
        tx4DisLike.text = dane.currentUsersDataUsers!!.dislikes.toString()
        tx4Opis2.text = dane.currentUsersDataUsers!!.description


        if(dane.currentUser.email == null)
        {
            img4Email.setOnClickListener {
                var intent = Intent(applicationContext, EditProfil::class.java)
                startActivity(intent)
            }
            img4Email.setOnClickListener {
                var intent = Intent(applicationContext, EditProfil::class.java)
                startActivity(intent)
            }
            tx4Email.text = "Nie podano, (kliknij aby dodać)"
        };else{
            tx4Email.text = dane.currentUser.email
        }
        

        if(dane.currentUser.phoneNumber == "")
        {
            tx4Tel.setOnClickListener {
                var intent = Intent(applicationContext, EditProfil::class.java)
                startActivity(intent)
            }
            img4Tel.setOnClickListener {
                var intent = Intent(applicationContext, EditProfil::class.java)
                startActivity(intent)
            }
            tx4Tel.text = "Nie podano, (kliknij aby dodać)"
        };else{
            tx4Tel.text = dane.currentUser.phoneNumber
        }

        tx4UserChat.visibility = View.GONE
        img4UserChat.visibility = View.GONE
    }

    fun setOnClickListner(){

        img4Mapa.setOnClickListener {
            val map = Intent(applicationContext,  Home::class.java)
            startActivity(map)
            finish()
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }

        img4Chat.setOnClickListener {
            val chat = Intent(applicationContext,  ChatMenager::class.java)
            startActivity(chat)
            finish()
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }


        img4Menu.setOnClickListener {
            if(drawer4.isDrawerOpen(GravityCompat.START)){
                drawer4.closeDrawer(GravityCompat.START)
            }
            else{
                drawer4.openDrawer(GravityCompat.START)
            }
        }

        menu4.setNavigationItemSelectedListener(this)


        FAB4.setOnClickListener{
            val editProfil = Intent(applicationContext,  EditProfil::class.java)
            startActivity(editProfil)
        }

    }
}




