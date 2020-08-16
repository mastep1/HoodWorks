package com.example.neighborhoodwork.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neighborhoodwork.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.add_task_map.*


class AddTaskMap : Fragment(),
    OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.add_task_map, container, false)
        
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView19) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //val UCA = LatLng((-34).toDouble(), 151.00)
        //googleMap!!.addMarker(MarkerOptions().position(UCA).title("YOUR TITLE")).showInfoWindow()
        //googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(UCA, 17f))
        //searchView19.adap
    }
}