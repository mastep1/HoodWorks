package com.example.neighborhoodwork

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class test404(private val mContext: Context) :
    GoogleMap.InfoWindowAdapter {
    private val mWindow: View
    private fun renderWiewText(marker: Marker, view: View) {}


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