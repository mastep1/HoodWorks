package com.example.neighborhoodwork.support

import android.content.Context
import android.widget.TextView
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

fun addMessage(context: Context, message: String, date : String, thisUser : Boolean ){

    lateinit var SQL_MESSAGE : SQL_MESSAGE

    SQL_MESSAGE = SQL_MESSAGE(context)            //// SQL
    SQL_MESSAGE.insert(MessageModel(message, true,"$date", dane.Contasts[dane.openConversation], true ))             /// SQL

    dane.messages.add(MessageModel(message, true, date, dane.Contasts[dane.openConversation], true))         /// RAM

}

fun setCurrentActivity(tx : TextView, activityName : String){
    dane.tx = tx
    dane.currentActivity =  activityName
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




}