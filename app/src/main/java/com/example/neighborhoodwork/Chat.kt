package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_chat.*

class Chat : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        rc3.apply {
            layoutManager = LinearLayoutManager(this@Chat)
            addItemDecoration(TopSpacingItemDecoration(30))
            adapter = AdapterChat()
        }

        img3Menu.setOnClickListener {
            if(drawer3.isDrawerOpen(GravityCompat.START)){
                drawer3.closeDrawer(GravityCompat.START)
            }
            else{
                drawer3.openDrawer(GravityCompat.START)
            }
        }

        menu3.setNavigationItemSelectedListener(this)

        img3Mapa.setOnClickListener {
            val mapa = Intent(applicationContext, Home::class.java)
            startActivity(mapa)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        img3Profil.setOnClickListener {
            val profil = Intent(applicationContext, Profil::class.java)
            startActivity(profil)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.Menu_Mapa -> {
                val home = Intent(applicationContext, Home::class.java)
                startActivity(home)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.Menu_Chat -> {
                val chat = Intent(applicationContext, Chat::class.java)
                startActivity(chat)
            }
            R.id.Menu_Profil -> {
                val profil = Intent(applicationContext, Profil::class.java)
                startActivity(profil)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.Menu_Dodaj_Zlecenie -> {
                val dodaj = Intent(applicationContext, DodajZlecenie::class.java)
                startActivity(dodaj)
            }
            R.id.Menu_Wyloguj -> {
                AuthUI.getInstance().signOut(this@Chat).addOnCompleteListener {
                    val main = Intent(applicationContext, MainActivity::class.java)
                    startActivity(main)
                    finish()
                }.addOnFailureListener {
                        e-> Toast.makeText(this@Chat, "Wylogowano",Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
        drawer3.closeDrawer(GravityCompat.START)
        return true
    }
}
