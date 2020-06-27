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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.example.neighborhoodwor.ZadanieModel
import com.example.neighborhoodwork.Fragments.BigInfoWindow
import com.example.neighborhoodwork.Adapters.InfoWindowAdapter
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.SQL_BASE_MESSAGE
import com.example.neighborhoodwork.support.SQL_CONTACTS
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.user
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class Home : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    lateinit var providers: List<AuthUI.IdpConfig>
    private lateinit var auth: FirebaseAuth
    var frag = supportFragmentManager
    var infoWindow = BigInfoWindow(this)
    lateinit var myRef: DatabaseReference
    lateinit var userData: DatabaseReference
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    internal lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10
    lateinit var googleMapForLocation : GoogleMap
    lateinit var locationMarker : Marker
    var dodanoLokalizacje : Boolean = false

    lateinit var contactsBase : SQL_CONTACTS
    lateinit var messageBase : SQL_BASE_MESSAGE

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
        
        if (currentUser != null) {
            dane.currentUser = currentUser
        }

        mLocationRequest = LocationRequest()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }


        val map = supportFragmentManager.findFragmentById(R.id.mapView2) as SupportMapFragment
        map.getMapAsync(this)


        dateUser(currentUser)

        setOnClickListner()

    }

    fun setOnClickListner(){

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

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // do work here
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: Location) {

        if (location != null) {
            var lokalizacjaDoZapisania = LatLng( location.latitude, location.longitude)
            dane.lokalizacjaAktualna = lokalizacjaDoZapisania
            dodajLok(googleMapForLocation)
        }

    }

    protected fun startLocationUpdates() {

        mLocationRequest = LocationRequest()
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest!!.setInterval(INTERVAL)
        mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
            Looper.myLooper())
    }

    private fun stoplocationUpdates() {
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }

    fun dateUser(currentUser: FirebaseUser?){

        if (currentUser != null) {
            val fireuserBase = FirebaseDatabase.getInstance()

            userData = fireuserBase.getReference("Users").child(currentUser.displayName.toString()).child("Data")

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
                            "ukonczone" -> user.ukonczone = i.value.toString().toInt()
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
                    Snackbar.make(l2Content, "Zweryfikuj swoje konto e-mail ${currentUser.email}", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }
            if(currentUser.phoneNumber != null)
            {
                user.tel = currentUser.phoneNumber.toString()
            }


        }

        contactsBase = SQL_CONTACTS(this)
        dane.Contasts.clear()
        contactsBase.readAllUsers()

        messageBase = SQL_BASE_MESSAGE(this)
        dane.messages.clear()
        messageBase.readAllMessages(this)




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMapForLocation = googleMap
        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }

        googleMap.setInfoWindowAdapter(
            InfoWindowAdapter(
                this
            )
        )

        val fireBase = FirebaseDatabase.getInstance()
        myRef = fireBase.getReference("Zadania")

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (i in dataSnapshot.children) {

                    val element = i.getValue(ZadanieModel::class.java)
                    dane.zadania.add(element!!)
                }
                znaczniki(googleMap)
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
                    .snippet("${dane.zadania[i].ID}")
            )
            i++
        }


        googleMap.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()

            }
            true

        }

        googleMap.setOnInfoWindowClickListener(OnInfoWindowClickListener { arg0 ->

            arg0.hideInfoWindow()

            bigInfoWindow(true)

            tx2MainNapis.setOnClickListener {
                bigInfoWindow(false)
            }

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

    fun dodajLok(googleMap: GoogleMap){
        stoplocationUpdates()

        if(dodanoLokalizacje)
        { locationMarker.remove()}


        locationMarker = googleMap.addMarker(MarkerOptions().position(dane.lokalizacjaAktualna).icon(BitmapDescriptorFactory.fromResource(
            R.drawable.marker
        )))
        locationMarker.snippet = "737F"
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dane.lokalizacjaAktualna, 16f))

        dodanoLokalizacje = true

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

