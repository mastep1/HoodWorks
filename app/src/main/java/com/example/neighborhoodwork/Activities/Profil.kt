package com.example.neighborhoodwork.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.user
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.profil.*


class Profil : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profil)

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



        img4Menu.setOnClickListener {
            if(drawer4.isDrawerOpen(GravityCompat.START)){
                drawer4.closeDrawer(GravityCompat.START)
            }
            else{
                drawer4.openDrawer(GravityCompat.START)
            }
        }


        menu4.setNavigationItemSelectedListener(this)


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

        FAB4.setOnClickListener {
            val editProfil = Intent(applicationContext,  EditProfil::class.java)
            startActivity(editProfil)
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
        drawer4.closeDrawer(GravityCompat.START)
        return true
    }
}
