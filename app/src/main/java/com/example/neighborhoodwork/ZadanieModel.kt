package com.example.neighborhoodwor

import com.google.firebase.database.IgnoreExtraProperties
import java.sql.Time


@IgnoreExtraProperties
data  class ZadanieModel (
    var x : String = "",
    var y : String = "",
    var opis : String = "",
    var wynagrodzenie : String = 0.toString(),
    var img : String = 0.toString(),
    var length : String = 0.toString(),
    var time : String = 0.toString(),
    var title : String = "",
    var ID : String = ""
)