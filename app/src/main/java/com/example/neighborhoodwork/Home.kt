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
import com.example.neighborhoodwor.ZadanieModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dodaj_zlecenie.*
import kotlinx.android.synthetic.main.activity_home.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.properties.Delegates


class Home : AppCompatActivity(), OnMapReadyCallback {

    lateinit var providers : List<AuthUI.IdpConfig>
    val MY_REQUEST_CODE: Int = 2807

    private lateinit var mMap: GoogleMap
    lateinit var myRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )
        showSignInOptions()

        tx4MainNapis.setOnClickListener {
        }

        img4Menu.setOnClickListener {
            val DodajZledeniazmiennnnna = Intent(applicationContext, DodajZlecenie::class.java)
            startActivity(DodajZledeniazmiennnnna)
        }

        img4Chat.setOnClickListener {
            val chat = Intent(applicationContext, Chat::class.java)
            startActivity(chat)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView4) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.Menu_Wyloguj -> {
                AuthUI.getInstance().signOut(this@Home).addOnCompleteListener{
                    Toast.makeText(this,"Wylogowanoooo", Toast.LENGTH_SHORT).show()
                    showSignInOptions()
                }.addOnFailureListener {
                        e-> Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==MY_REQUEST_CODE)
        {
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, ""+user!!.email, Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "i chuj", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showSignInOptions() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers).setTheme(R.style.MyTheme)
            .build(),MY_REQUEST_CODE)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val fireBase = FirebaseDatabase.getInstance()
        myRef = fireBase.getReference("Zadania")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(i in dataSnapshot.children){
                    val element = i.getValue(ZadanieModel::class.java)
                    dane.zadania.add(element!!)
                   znaczniki(googleMap)
                }
            }
        })
    }

    fun znaczniki(googleMap : GoogleMap){
     var i = 0
        while(i<dane.zadania.size){
                val wspolrzedne = LatLng(dane.zadania[i].x.toDouble(), dane.zadania[i].y.toDouble())
            googleMap.addMarker(MarkerOptions().position(wspolrzedne).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Moja Chałopa tu stoji"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(wspolrzedne, 16f))
                 i++
         }

        googleMap.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                Toast.makeText(applicationContext, "ukryto", Toast.LENGTH_SHORT).show()
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
                Toast.makeText(applicationContext, "Pokazano", Toast.LENGTH_SHORT).show()
            }
            true

        }
    }

}


