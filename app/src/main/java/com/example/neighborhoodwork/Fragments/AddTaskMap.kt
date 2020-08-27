package com.example.neighborhoodwork.Fragments

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import kotlinx.android.synthetic.main.add_task_map.*
import java.io.IOException
import java.util.*


class AddTaskMap(context2 : Context) : Fragment(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    var context3 = context2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.add_task_map, container, false)
        
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView19) as SupportMapFragment?

        mapFragment!!.getMapAsync(this)
        

        return view
    }

    private fun getAddress(latLng: LatLng): String {

        var geocoder: Geocoder
        var addresses: List<Address>
        geocoder = Geocoder(context3, Locale.getDefault())
        return try {
            addresses = geocoder.getFromLocation(
                latLng.latitude,
                latLng.longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName
            val country = addresses[0].countryName


            var toReturn = address.removeSuffix(", Polska")

            return  toReturn
        } catch (e: IOException) {
            e.printStackTrace()
            "No Address Found"
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        var afterUpadate = false
        mMap = googleMap
        mMap!!.isMyLocationEnabled = true
        mMap!!.setOnMyLocationChangeListener { location ->
            val ltlng = LatLng(location.latitude, location.longitude)
            if(afterUpadate == false){
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    ltlng, 15f
                )
                mMap!!.animateCamera(cameraUpdate)
                afterUpadate = true
            }
            tx19LocationAdress.text = getAddress(ltlng)
        }

        if(dane.newTask.x != "taklubnie"){
            var lateLatLng = LatLng(dane.newTask.x.toDouble(), dane.newTask.y.toDouble())
            var markerOptionsLate = MarkerOptions()
            markerOptionsLate.position(lateLatLng)
            markerOptionsLate.title(getAddress(lateLatLng))
            mMap!!.clear()
            googleMap.addMarker(markerOptionsLate)
        }

        mMap!!.setOnMapClickListener { latLng ->
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title(getAddress(latLng))
            mMap!!.clear()
            val location = CameraUpdateFactory.newLatLngZoom(latLng, 15f)

            mMap!!.animateCamera(location)
            mMap!!.addMarker(markerOptions)
            Etx19Search.setText(getAddress(latLng))
            dane.newTask.x = "${latLng.latitude}"
            dane.newTask.y = "${latLng.longitude}"
        }
        Etx19Search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                returnIdeas(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun returnIdeas(string : String){

        Places.initialize(context3, "AIzaSyAdvyos83thw11LPSWKl44nI8csYVCrt50")
        val placesClient = Places.createClient(context3)


        val token = AutocompleteSessionToken.newInstance()

        var query = string
        val request = FindAutocompletePredictionsRequest.builder()
            .setLocationRestriction(RectangularBounds.newInstance(LatLng(52.137254, 20.787576), LatLng(52.261551, 21.038076)))
            .setTypeFilter(TypeFilter.ADDRESS)
            .setSessionToken(token)
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                for (prediction in response.autocompletePredictions) {
                    setAdapter(prediction.getPrimaryText(null).toString())
                }
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    setAdapter("null")
                }
            }

    }

    private fun setAdapter(string: String) {
        if(string==null){

        }else{
            var listOfAdresses = listOf<String>(string)
            var adapter = ArrayAdapter(context3, R.layout.support_simple_spinner_dropdown_item ,listOfAdresses)
            Etx19Search.setAdapter(adapter)
        }


    }


    
    companion object {
        private const val PLACE_PICKER_REQUEST = 999
        private const val LOCATION_REQUEST_CODE = 23
    }

}



