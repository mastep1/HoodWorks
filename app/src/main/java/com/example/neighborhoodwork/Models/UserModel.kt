package com.example.neighborhoodwork.Models

data class UserModel (
    var rating : Double = 0.0,
    var ukonczone : Int = 0,
    var dni : Int = 0,
    var like : Int = 0,
    var dislike: Int = 0,
    var opis : String = ""
)