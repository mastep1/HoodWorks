package com.example.neighborhoodwork.Adapters

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.info_window.view.*
import java.util.*

class InfoWindowAdapter(private val mContext: Context) :
    GoogleMap.InfoWindowAdapter {
    private val mWindow: View


    private val tujestes: View


    private fun renderWiewText(marker: Marker, okno: View) {
        var clicked = 0
        var i = 0

            while(i < dane.zadania.size){
                if(marker.snippet.toLong() == dane.zadania[i].ID.toLong()){
                    clicked = i
                    dane.clicked = i
                    break
                }else{
                    i++
                }

            okno.img8Info.setImageResource(R.drawable.boy)

            okno.tx8Info.setText("${dane.zadania[clicked].title}")
            okno.tx8Length.setText("${dane.zadania[clicked].length} min")
            okno.tx8Wynagrodzenie.setText("${dane.zadania[clicked].wynagrodzenie} PLN")
            val geocoder: Geocoder
            val addresses: List<Address>
            geocoder = Geocoder(mContext, Locale.getDefault())
            addresses = geocoder.getFromLocation(dane.zadania[dane.clicked].x.toDouble(), dane.zadania[dane.clicked].y.toDouble(), 1)
            val address = addresses[0].getAddressLine(0)
            okno.tx8Adres.text = address
        }


    }


    override fun getInfoWindow(marker: Marker): View {
        if(marker.snippet == "737F"){
            return tujestes
        }else{
            renderWiewText(marker, mWindow)
            return mWindow
        }

    }

    override fun getInfoContents(marker: Marker): View {
        if(marker.snippet == "737F"){
            return tujestes
        }else{
            renderWiewText(marker, mWindow)
            return mWindow
        }
    }



    init {
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.info_window, null)
    }

    init {
        tujestes = LayoutInflater.from(mContext).inflate(R.layout.tu_jestes, null)
    }
}

