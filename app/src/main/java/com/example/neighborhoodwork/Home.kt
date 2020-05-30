package com.example.neighborhoodwork

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.AlteredCharSequence.make
import android.text.BoringLayout.make
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.neighborhoodwor.ZadanieModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dodaj_zlecenie.*
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*



class Home : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    lateinit var providers: List<AuthUI.IdpConfig>
    val MY_REQUEST_CODE: Int = 2807
    private lateinit var auth: FirebaseAuth

    private lateinit var mMap: GoogleMap
    lateinit var myRef: DatabaseReference

    lateinit var userData: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        val currentUser = auth.currentUser
        updateUI(currentUser)

        val map = supportFragmentManager.findFragmentById(R.id.mapView4) as SupportMapFragment
        map.getMapAsync(this)


        dateUser(currentUser)

        img4Menu.setOnClickListener {
            if(drawler4.isDrawerOpen(GravityCompat.START)){
                drawler4.closeDrawer(GravityCompat.START)
            }
            else{
                drawler4.openDrawer(GravityCompat.START)
            }
        }

        menu4.setNavigationItemSelectedListener(this)

        img4Chat.setOnClickListener {
            val chat = Intent(applicationContext, Chat::class.java)
            startActivity(chat)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        img4Profil.setOnClickListener {
            val profil = Intent(applicationContext, Profil::class.java)
            startActivity(profil)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    fun dateUser(currentUser: FirebaseUser?){
        if (currentUser != null) {
            val fireuserBase = FirebaseDatabase.getInstance()

            userData = fireuserBase.getReference("Users").child(currentUser.displayName.toString())

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
                            "ukonczono" -> user.ukonczone = i.value.toString().toInt()
                            "rating" -> user.rating = i.value.toString().toDouble()
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
                    Toast.makeText(applicationContext, "Zweryfikuj swoje konto email ${currentUser.email}", Toast.LENGTH_LONG).show()
                }
            }
            if(currentUser.phoneNumber != null)
            {
                user.tel = currentUser.phoneNumber.toString()
            }
            if(currentUser.phoneNumber != null)
            {
                user.tel = currentUser.phoneNumber.toString()
            }



        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val fireBase = FirebaseDatabase.getInstance()
        myRef = fireBase.getReference("Zadania")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (i in dataSnapshot.children) {
                    val element = i.getValue(ZadanieModel::class.java)
                    dane.zadania.add(element!!)
                    znaczniki(googleMap)
                }
            }
        })
    }

    fun znaczniki(googleMap: GoogleMap) {
        var i = 0
        while (i < dane.zadania.size) {
            val wspolrzedne = LatLng(dane.zadania[i].x.toDouble(), dane.zadania[i].y.toDouble())
            googleMap.addMarker(
                MarkerOptions().position(wspolrzedne)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
            )
            if(i == dane.zadania.size - 1){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wspolrzedne, 16f))
            }
            i++
        }

        googleMap.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                val fm = supportFragmentManager
                val F_Map = F_MapWindow()
                fm.beginTransaction().add(R.id.l4fragment, F_Map).commit()
                Toast.makeText(applicationContext, "${marker.id}", Toast.LENGTH_SHORT).show()
            }
            true

        }
    }

    fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (!currentUser.isEmailVerified) {
                Toast.makeText(applicationContext, "Zweryfikuj swojego e-maila", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.Menu_Mapa -> {
                val home = Intent(applicationContext, Home::class.java)
                startActivity(home)
            }
            R.id.Menu_Chat -> {
                val chat = Intent(applicationContext, Chat::class.java)
                startActivity(chat)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
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
                AuthUI.getInstance().signOut(this@Home).addOnCompleteListener {
                    val main = Intent(applicationContext, MainActivity::class.java)
                    startActivity(main)
                    finish()
                }.addOnFailureListener { e ->
                    Toast.makeText(this@Home, "Wylogowanie nie powidło się", Toast.LENGTH_SHORT).show()
                }
            }
        }
        drawler4.closeDrawer(GravityCompat.START)
        return true
    }

}

