package com.example.neighborhoodwork

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.example.neighborhoodwor.ZadanieModel
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
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
    lateinit var lm : LocationManager
    lateinit var location : Location
    private lateinit var auth: FirebaseAuth
    var frag = supportFragmentManager
    var infoWindow = F_MapWindow()
    lateinit var myRef: DatabaseReference
    lateinit var fusedLocationClient : FusedLocationProviderClient
    lateinit var userData: DatabaseReference
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUpdates()

        val map = supportFragmentManager.findFragmentById(R.id.mapView2) as SupportMapFragment
        map.getMapAsync(this)






        dateUser(currentUser)
        /*if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),111)
            }

                lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)


                var ll = object:LocationListener{
                    override fun onLocationChanged(location: Location?) {
                        reverseLocation(location)
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    }

                    override fun onProviderEnabled(provider: String?) {
                    }

                    override fun onProviderDisabled(provider: String?) {
                    }

                }

                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10.0f, ll)

         */





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
            val chat = Intent(applicationContext, Chat::class.java)
            startActivity(chat)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        img2Profil.setOnClickListener {
            val profil = Intent(applicationContext, Profil::class.java)
            startActivity(profil)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        BT6Exit.setOnClickListener {
            bigInfoWindow(false)
        }

    }

    private fun getLocationUpdates()
    {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 5000
        locationRequest.smallestDisplacement = 10f // 170 m = 0.1 mile
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {

                    val location = locationResult.lastLocation
                    aktualizujLokalizacje(location)

                    // use your location object
                    // get latitude , longitude and other info from this
                }


            }
        }
    }

    //start location updates
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    // stop location updates
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // stop receiving location update when activity not visible/foreground
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    // start receiving location update when activity  visible/foreground
    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun aktualizujLokalizacje(lok: Location?) {
        var x = lok?.latitude
        var y = lok?.accuracy
        val miejsceNaMapie = LatLng(x!!, y!!.toDouble())
        dane.lokalizacjaAktualna = miejsceNaMapie
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
                    Snackbar.make(l2Content, "Zweryfikuj swoje konto e-mail ${currentUser.email}", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
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

        googleMap.setInfoWindowAdapter(InfoWindowAdapter(this))

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
       dodajLok(googleMap)


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
        googleMap.addMarker(MarkerOptions().position(dane.lokalizacjaAktualna).icon(BitmapDescriptorFactory.fromResource(R.drawable.beer)))
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
        drawler2.closeDrawer(GravityCompat.START)
        return true
    }





}

