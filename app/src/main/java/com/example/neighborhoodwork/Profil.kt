package com.example.neighborhoodwork

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.profil.*


class Profil : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil)

        tx6Imie.text = user.imie
        ratingBar6.rating = user.rating.toFloat()
        tx6Ukonczone1.text = user.ukonczone.toString()
        tx6Dni1.text = user.dni.toString()
        tx6Like.text = user.like.toString()
        tx6DisLike.text = user.dislike.toString()
        tx6Opis2.text = user.opis


        if(user.email=="")
        {
            tx6Email.text = "Nie podano"
        };else{
            tx6Email.text = user.email
        }


        if(user.tel=="")
        {
            tx6Tel.text = "Nie podano"
        };else{
            tx6Tel.text = user.tel
        }



        img6Menu.setOnClickListener {
            if(drawer6.isDrawerOpen(GravityCompat.START)){
                drawer6.closeDrawer(GravityCompat.START)
            }
            else{
                drawer6.openDrawer(GravityCompat.START)
            }
        }


        menu6.setNavigationItemSelectedListener(this)


        img6Mapa.setOnClickListener {
            val map = Intent(applicationContext,  Home::class.java)
            startActivity(map)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }


        img6Chat.setOnClickListener {
            val chat = Intent(applicationContext,  Chat::class.java)
            startActivity(chat)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

    }


    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.Menu_Mapa -> {
                val home = Intent(applicationContext, Home::class.java)
                startActivity(home)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            R.id.Menu_Chat -> {
                val chat = Intent(applicationContext, Chat::class.java)
                startActivity(chat)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            R.id.Menu_Profil -> {
                val profil = Intent(applicationContext, Profil::class.java)
                startActivity(profil)
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
        drawer6.closeDrawer(GravityCompat.START)
        return true
    }
}
