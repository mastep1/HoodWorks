package com.example.neighborhoodwork.Fragments

import android.app.DialogFragment
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.neighborhoodwork.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.add_task_map.*
import java.io.IOException
import java.util.*


class AddTaskMap(context2 : Context) : Fragment(),
    OnMapReadyCallback {
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
        val geocoder: Geocoder
        val addresses: List<Address>
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
        mMap = googleMap
        mMap!!.isMyLocationEnabled = true
        mMap!!.setOnMyLocationChangeListener { location ->
            val ltlng =
                LatLng(location.latitude, location.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                ltlng, 15f
            )
            mMap!!.animateCamera(cameraUpdate)
            tx19LocationAdress.text = getAddress(ltlng)
        }

        mMap!!.setOnMapClickListener { latLng ->
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title(getAddress(latLng))
            mMap!!.clear()
            val location = CameraUpdateFactory.newLatLngZoom(
                latLng, 15f
            )
            mMap!!.animateCamera(location)
            mMap!!.addMarker(markerOptions)
            Etx19Search.setText(getAddress(latLng))
        }
    }
    
    companion object {
        private const val PLACE_PICKER_REQUEST = 999
        private const val LOCATION_REQUEST_CODE = 23
    }
}

private fun DialogFragment.show(ft: FragmentTransaction, s: String) {

}
