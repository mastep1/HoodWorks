package com.example.neighborhoodwork

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_home.*



class Home : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        tx4MainNapis.setOnClickListener {
            val chat = Intent(applicationContext, test10::class.java)
            startActivity(chat)
        }
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
        var testowyZnacznik = Zadanie("52.189", "20.828")
        dane.zadania.add(testowyZnacznik)
        ///// To tylko na chwile (na góże)
        znacznik(googleMap)
        googleMap.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            true

        }
    }


fun znacznik(mapa : GoogleMap){
    var i = 0
    while(i<dane.zadania.size){
        val wspolrzedne = LatLng(dane.zadania[i].x.toDouble(), dane.zadania[i].y.toDouble())
        mapa.addMarker(MarkerOptions().position(wspolrzedne).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).title("Moja Chałopa tu stoji"))
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(wspolrzedne, 16f))
        i++
    }
}

    fun ustawMarkr(name : String, x : Int, y : Int): Bitmap {
        var marker = BitmapFactory.decodeResource(resources, resources.getIdentifier(name, "drawble", packageName))
        return Bitmap.createScaledBitmap(marker, x, y, false)
    }

}


