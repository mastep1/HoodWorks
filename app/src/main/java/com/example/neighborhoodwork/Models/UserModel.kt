package com.example.neighborhoodwork.Models

import android.net.Uri

data class UserModel(
    var rating: Double = 0.0,
    var ukonczone: Int = 0,
    var dni: Int = 0,
    var like: Int = 0,
    var dislike: Int = 0,
    var opis: String = "",
    var url : String = ""
)