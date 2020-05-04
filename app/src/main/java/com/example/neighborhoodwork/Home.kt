package com.example.neighborhoodwork

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_home.*



class Home : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        img4Chat.setOnClickListener {
            val chat = Intent(applicationContext, Chat::class.java)
            startActivity(chat)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView4) as SupportMapFragment
        mapFragment.getMapAsync(this)

        img4Menu.setOnClickListener {
            val fastMenu = PopupMenu(this, it)
            fastMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.Menu_Mapa -> {
                        var nowa = Intent(applicationContext, Home::class.java)
                        startActivity(nowa)
                        true
                    }
                    R.id.Menu_Mapa -> {
                        var nowa = Intent(applicationContext, Home::class.java)
                        startActivity(nowa)
                        true
                    }
                    R.id.Menu_Mapa -> {
                        var nowa = Intent(applicationContext, Home::class.java)
                        startActivity(nowa)
                        true
                    }
                    R.id.Menu_Ustawienia -> {
                        var nowa = Intent(applicationContext, Home::class.java)
                        startActivity(nowa)
                        true
                    }
                    else -> false
                }
            }
            fastMenu.inflate(R.menu.main_menu)
            fastMenu.show()
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isIndoorEnabled = true

        val sydney = LatLng(52.189, 20.828)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker"))
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f))
    }
}
