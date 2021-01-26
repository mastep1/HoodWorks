package com.example.neighborhoodwork.Activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.example.neighborhoodwork.Adapters.InfoWindowAdapter
import com.example.neighborhoodwork.Fragments.BigInfoWindow
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.Room6
import com.example.neighborhoodwork.SQLIMAGEACTIVITY
import com.example.neighborhoodwork.support.*
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class Home : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    lateinit var providers: List<AuthUI.IdpConfig>
    private lateinit var auth: FirebaseAuth
    var frag = supportFragmentManager
    var infoWindow = BigInfoWindow(this)

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    internal lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10
    lateinit var googleMapForLocation : GoogleMap
    lateinit var locationMarker : Marker
    var dodanoLokalizacje : Boolean = false
    private val mLocationPermissionsGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        img2Filter.setOnClickListener {
            Toast.makeText(applicationContext, "${dane.currentUsersData}", Toast.LENGTH_LONG).show()
        }

        
        if(dane.HomeOnCreate==false){
            auth = FirebaseAuth.getInstance()
            providers = Arrays.asList<AuthUI.IdpConfig>(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build()
            )

            val currentUser = auth.currentUser

            if (currentUser != null) {
                dane.currentUser = currentUser
            }
            dane.superImage = img2Mapa



        }


        setOnClickListners()
    }

    override fun onResume() {
        super.onResume()

        setCurrentActivity(tx2NewMessage, "Home")

        val map = supportFragmentManager.findFragmentById(R.id.mapView2) as SupportMapFragment
        map.getMapAsync(this)
        mLocationRequest = LocationRequest()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }

        if(dane.newMessage!=0){
            tx2NewMessage.text = dane.newMessage.toString()
            tx2NewMessage.visibility = View.VISIBLE
        }else{
            tx2NewMessage.visibility = View.INVISIBLE
        }

    }


    fun setOnClickListners(){

        FAB2MyLocation.setOnClickListener {
            if (checkPermissionForLocation(this)) {
                startLocationUpdates()
            }
        }

        img2Menu.setOnClickListener {
            if(drawler2.isDrawerOpen(GravityCompat.START)){
                drawler2.closeDrawer(GravityCompat.START)
            }
            else{
                drawler2.openDrawer(GravityCompat.START)
            }
        }

        menu2.setNavigationItemSelectedListener(this)

        img2Chat.setOnClickListener {
            val chat = Intent(applicationContext, ChatMenager::class.java)
            startActivity(chat)
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }

        img2Profil.setOnClickListener {
            val profil = Intent(applicationContext, Profil::class.java)
            startActivity(profil)
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }

        BT6Exit.setOnClickListener {
            bigInfoWindow(false)
        }
    }

    fun checkPermissionForLocation(context: Context): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show()
                startLocationUpdates()
            }
        }
    }

    private fun buildAlertMessageNoGps() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
               // startActivityForResult(
                    //Intent(BassBoost.Settings.ACTION_LOCATION_SOURCE_SETTINGS) , 11
                //)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
                finish()
            }
        val alert: AlertDialog  = builder.create()
        alert.show()




    }

    protected fun startLocationUpdates() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return
        }else{
            autoLokalizacja(dane.googleMap)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        if(dane.HomeOnCreate==false){
            dane.googleMap = googleMap
            startService(Intent(this@Home, MyService::class.java))
            dane.HomeOnCreate = true
        }



        znaczniki(googleMap)
        
        googleMapForLocation = googleMap

        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }

        googleMap.setInfoWindowAdapter(
            InfoWindowAdapter(
                this
            )
        )

        googleMap.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener { arg0 ->

            arg0.hideInfoWindow()

            bigInfoWindow(true)

            // tx2MainNapis.setOnClickListener {
            //    bigInfoWindow(false)
            // }

        })


    }



    fun bigInfoWindow(tryb : Boolean){
        if(tryb==true){
            FAB2MyLocation.hide()
            frag.beginTransaction().add(R.id.l2InfoFrag, infoWindow).commit()

        }else{
            FAB2MyLocation.show()
            frag.beginTransaction().remove(infoWindow).commit()
        }
    }

    fun autoLokalizacja(googleMap: GoogleMap){

        googleMap!!.isMyLocationEnabled = true
        googleMap!!.setOnMyLocationChangeListener { location ->

            val ltlng = LatLng(location.latitude, location.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                ltlng, 15f
            )
            googleMap!!.animateCamera(cameraUpdate)
            var lokalizacjaDoZapisania = LatLng( location.latitude, location.longitude)
            dane.lokalizacjaAktualna = lokalizacjaDoZapisania
        }

    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.Menu_Mapa -> {
                val home = Intent(applicationContext, Home::class.java)
                startActivity(home)
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
                AuthUI.getInstance().signOut(this@Home).addOnCompleteListener {
                    val main = Intent(applicationContext, MainActivity::class.java)
                    startActivity(main)
                    finish()
                }.addOnFailureListener { e ->
                    Toast.makeText(this@Home, "Wylogowanie nie powidło się", Toast.LENGTH_SHORT).show()
                }
            }
        }
        drawler2.closeDrawer(GravityCompat.START)
        return true
        
    
    }

    

}

