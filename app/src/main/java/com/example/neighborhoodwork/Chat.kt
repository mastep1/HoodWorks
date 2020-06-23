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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class Chat : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnSelectConConversation {


    lateinit var SQL_CONTACTS_DB : SQL_CONTACTS
    lateinit var userConversationOther: DatabaseReference
    lateinit var userConversationCurrent: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        addContactIfShould()

        setOnClickListnerAndRecyclerApply()

        menu3.setNavigationItemSelectedListener(this)

        checkReciveMessage()



    }

    private fun checkReciveMessage(){

        val fireuserBase = FirebaseDatabase.getInstance()    /// Połączenie z bazą

        userConversationCurrent = fireuserBase.getReference("Users").child(dane.currentUser.displayName.toString())
            .child("Data").child("Conversation")

        userConversationCurrent.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (i in dataSnapshot.children) {
                    var exist = checkDoExistConversation(i.key.toString())
                    if(!exist){
                        addConversation(i.key.toString())
                    }
                    tx3MainNapis.text = "$exist"
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

    private fun addContactIfShould(){

        if(dane.newContact!=""){

            var add = checkDoExistConversation(dane.newContact)

            if(add == false){
                addConversation(dane.newContact)

                val fireuserBase = FirebaseDatabase.getInstance()           /// Utworzenie konwersacji (W FireBase) Dla drugiego użytkownika
                userConversationOther = fireuserBase.getReference("Users").child(dane.newContact).child("Data").child("Conversation").child(dane.currentUser!!.displayName.toString())  /// Utworzenie konwersacji (W FireBase) Dla drugiego użytkownika
                userConversationOther.setValue("TAKIETAKIETAKIETAKIETAKIE")         // Utworzenie konwersacji (W FireBase) Dla drugiego użytkownika
            }

        }
    }

    private fun addConversation(newConversation : String){
        dane.Contasts.add(newConversation)          /// RAM

        SQL_CONTACTS_DB = SQL_CONTACTS(this)            //// SQL
        SQL_CONTACTS_DB.insertUser("${newConversation}")        /// SQL



        dane.newContact = ""                            /// wyczyszczenie zmiennej
    }

    override fun onItemClick(selectUser: String) {
        Toast.makeText(this, selectUser, Toast.LENGTH_SHORT).show()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
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
                }.addOnFailureListener { e ->
                    Toast.makeText(this@Chat, "Wylogowano", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
        drawer3.closeDrawer(GravityCompat.START)
        return true
    }

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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        img3Profil.setOnClickListener {
            val profil = Intent(applicationContext, Profil::class.java)
            startActivity(profil)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        rc3.apply {
            layoutManager = LinearLayoutManager(this@Chat)
            addItemDecoration(TopSpacingItemDecoration(30))
            adapter = AdapterChat(this@Chat)


        }
    }

}
