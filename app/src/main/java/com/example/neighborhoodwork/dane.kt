package com.example.neighborhoodwork

import com.example.neighborhoodwor.ZadanieModel
import com.google.android.gms.maps.model.LatLng

object dane {
    var zadania = arrayListOf<ZadanieModel>()
    var clicked : Int = 0
    var lokalizacjaAktualna = LatLng(51.0, 21.0)
}

object user {
    var imie = ""
    var email = ""
    var tel = ""
    var rating : Double = 0.0
    var ukonczone : Int = 0
    var dni : Int = 0
    var like : Int = 0
    var dislike : Int = 0
    var opis = ""
}
