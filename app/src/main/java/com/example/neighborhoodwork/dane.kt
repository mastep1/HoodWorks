package com.example.neighborhoodwork

import com.example.neighborhoodwor.ZadanieModel

object dane {
    var zadania = arrayListOf<ZadanieModel>()
    var id : String = "M421"
}
object user {
    var imie = ""
    lateinit var email : String
    lateinit var tel : String
    var rating : Double = 0.0
    var ukonczone : Int = 0
    var dni : Int = 0
    var like : Int = 0
    var dislike : Int = 0
    lateinit var opis : String
}
