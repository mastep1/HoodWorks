package com.example.neighborhoodwork

import android.content.Context
import android.content.Intent
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
            if(marker.snippet.toInt() == dane.zadania[i].ID.toInt()){
                clicked = i
                break
            }else{
                i++
            }
        }
        okno.img8Info.setImageResource(R.drawable.boy)

        okno.tx8Info.setText("${dane.zadania[clicked].title}")
        okno.tx8Length.setText("${dane.zadania[clicked].length} min")
        okno.tx8Wynagrodzenie.setText("${dane.zadania[clicked].wynagrodzenie} PLN")
        okno.setOnClickListener {
            Toast.makeText(mContext, "Intendor", Toast.LENGTH_LONG).show()
        }
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

