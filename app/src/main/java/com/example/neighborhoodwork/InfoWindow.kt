package com.example.neighborhoodwork

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.info_window.view.*

class InfoWindowAdapter(private val mContext: Context) :
    GoogleMap.InfoWindowAdapter {
    private val mWindow: View
    private fun renderWiewText(marker: Marker, okno: View) {
        var clicked = 0
        var i = 0
        while(i < dane.zadania.size){
            if(marker.snippet.toInt() == dane.zadania[i].img.toInt()){
                clicked = i
                break
            }else{
                i++
            }
        }
        mWindow.img8Info.setImageResource(R.drawable.boy)

        mWindow.tx8Length.setText("${dane.zadania[clicked].length} min")
        mWindow.tx8Wynagrodzenie.setText("${dane.zadania[clicked].wynagrodzenie} PLN")
    }
    override fun getInfoWindow(marker: Marker): View {
        renderWiewText(marker, mWindow)
        return mWindow
    }

    override fun getInfoContents(marker: Marker): View {
        renderWiewText(marker, mWindow)
        return mWindow
    }




    init {
        mWindow = LayoutInflater.from(mContext).inflate(R.layout.info_window, null)
    }
}

