package com.example.neighborhoodwork.support

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.neighborhoodwork.Models.MessageModel
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.RoomDatabaseForUsersAvatars.DataEntityUsersAvatars
import com.example.neighborhoodwork.support.RoomDatabaseForUsersAvatars.UsersAvatarsDatabase
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_s_q_l_i_m_a_g_e_a_c_t_i_v_i_t_y.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


fun compressPhotoToBytes(bitmap: Bitmap): ByteArray?{

    try{
        var photoInBytes : ByteArray? = null

        var objectByteArrayOutputStream: ByteArrayOutputStream?

        objectByteArrayOutputStream = ByteArrayOutputStream()

        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream)


        photoInBytes = objectByteArrayOutputStream.toByteArray()

        return photoInBytes
        
    }catch(e : Exception){
       Log.e( "errorKrystian", e.message)

    }
    return null
}

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

