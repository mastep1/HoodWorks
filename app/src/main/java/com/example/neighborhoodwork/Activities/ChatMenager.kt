package com.example.neighborhoodwork.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neighborhoodwork.Adapters.ChatMenagerAdapter
import com.example.neighborhoodwork.Adapters.OnSelectConConversation
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.*
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_home.*

class ChatMenager : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnSelectConConversation{


    lateinit var SQL_CONTACTS_DB : SQL_CONTACTS

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        img3filter.setOnClickListener {
            Toast.makeText(applicationContext, dane.info, Toast.LENGTH_LONG).show()
        }
        
        setCurrentActivity(tx3NewMessages, "ChatMenager")
        dane.recycler = rc3

        addContactIfShould()


        setOnClickListnerAndRecyclerApply()

        menu3.setNavigationItemSelectedListener(this)



    }

    override fun onResume() {
        super.onResume()

        setCurrentActivity(tx3NewMessages, "ChatMenager")

        if(dane.newMessage!=0){
            tx3NewMessages.text = dane.newMessage.toString()
            tx3NewMessages.visibility = View.VISIBLE
        }else{
            tx3NewMessages.visibility = View.INVISIBLE
        }
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

    private fun addContactIfShould(){

        if(dane.newContact !=""){

            var add = checkDoExistConversation(dane.newContact)

            if(add == false){
                
                   // val fireuserBase = FirebaseDatabase.getInstance()           /// Utworzenie konwersacji (W FireBase) Dla drugiego użytkownika
                   // userConversationOther = fireuserBase.getReference("Users")
                   //     .child(dane.newContact).child("Conversation").child(dane.currentUser!!.displayName.toString()) /// Utworzenie konwersacji (W FireBase) Dla drugiego użytkownika
                  //  userConversationOther.setValue("TAKIETAKIETAKIETAKIETAKIE")         // Utworzenie konwersacji (W FireBase) Dla drugiego użytkownika

                addConversation(dane.newContact)
            }

        }
    }



    private fun addConversation(newConversation : String){
        dane.Contasts.add(newConversation)          /// RAM

        SQL_CONTACTS_DB = SQL_CONTACTS(this)            //// SQL
        SQL_CONTACTS_DB.insertUser("${newConversation}")        /// SQL

        downloadSBsProfil(dane.newContact,  applicationContext)
        dane.newContact = ""             /// wyczyszczenie zmiennej


    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.Menu_Mapa -> {
                val home = Intent(applicationContext, Home::class.java)
                startActivity(home)
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
            }
            R.id.Menu_Chat -> {
                val chat = Intent(applicationContext, ChatMenager::class.java)
                startActivity(chat)
            }
            R.id.Menu_Profil -> {
                val profil = Intent(applicationContext, Profil::class.java)
                startActivity(profil)
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
            }
            R.id.Menu_Dodaj_Zlecenie -> {
                val dodaj = Intent(applicationContext, DodajZlecenie::class.java)
                startActivity(dodaj)
            }
            R.id.Menu_Wyloguj -> {
                AuthUI.getInstance().signOut(this@ChatMenager).addOnCompleteListener {
                    val main = Intent(applicationContext, MainActivity::class.java)
                    startActivity(main)
                    finish()
                }.addOnFailureListener { e ->
                    Toast.makeText(this@ChatMenager, "Wylogowano", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
        drawer3.closeDrawer(GravityCompat.START)
        return true
    }

    @ExperimentalStdlibApi
    fun setOnClickListnerAndRecyclerApply() {


        img3Menu.setOnClickListener {
            if (drawer3.isDrawerOpen(GravityCompat.START)) {
                drawer3.closeDrawer(GravityCompat.START)
            } else {
                drawer3.openDrawer(GravityCompat.START)
            }
        }

        img3Mapa.setOnClickListener {
            val mapa = Intent(applicationContext, Home::class.java)
            startActivity(mapa)
            finish()
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }

        img3Profil.setOnClickListener {
            val profil = Intent(applicationContext, Profil::class.java)
            startActivity(profil)
            finish()
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }

        rc3.apply {
            layoutManager = LinearLayoutManager(this@ChatMenager)
            addItemDecoration(
                TopSpacingItemDecoration(
                    30
                )
            )
            adapter = ChatMenagerAdapter(this@ChatMenager)


        }
    }

    override fun onItemClick(selectUser: String, position : Int) {
        dane.openConversation = position
        var chatView = Intent(this, ChatView::class.java)
        startActivity(chatView)
    }

}
