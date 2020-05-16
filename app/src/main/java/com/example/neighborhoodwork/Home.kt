package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.neighborhoodwor.ZadanieModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dodaj_zlecenie.*
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.properties.Delegates


class Home : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var myRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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


