package com.example.neighborhoodwork.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.adddMessage
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.setCurrentActivity
import com.example.neighborhoodwork.support.user
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.profil.*


class Profil : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil)
        setOnClickListner()

    }

    override fun onResume() {
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
        
        Glide.with(this)
            .load("${user.avatarPhotoURL}")
            .apply(
                RequestOptions()
                .placeholder(circularProgressDrawable)
            )
            .into(img4ZdjProf)

        tx4Imie.text = user.imie
        ratingBar4.rating = user.rating.toFloat()
        tx4Ukonczone1.text = user.ukonczone.toString()
        tx4Dni1.text = "${user.dni} dni"
        tx4Like.text = user.like.toString()
        tx4DisLike.text = user.dislike.toString()
        tx4Opis2.text = user.opis


        if(user.email =="")
        {
            tx4Email.text = "Nie podano"
        };else{
            tx4Email.text = user.email
        }


        if(user.tel =="")
        {
            tx4Tel.text = "Nie podano"
        };else{
            tx4Tel.text = user.tel
        }
    }

    fun setOnClickListner(){

        img4Mapa.setOnClickListener {
            val map = Intent(applicationContext,  Home::class.java)
            startActivity(map)
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }

        img4Chat.setOnClickListener {
            val chat = Intent(applicationContext,  ChatMenager::class.java)
            startActivity(chat)
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


        FAB4.setOnClickListener {
            val editProfil = Intent(applicationContext,  EditProfil::class.java)
            startActivity(editProfil)
        }

    }
}


