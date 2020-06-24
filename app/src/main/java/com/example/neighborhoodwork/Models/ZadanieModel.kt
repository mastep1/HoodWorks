package com.example.neighborhoodwor

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.IgnoreExtraProperties
import java.sql.Time


@IgnoreExtraProperties
data  class ZadanieModel (
    var x : String = "52.184549",
    var y : String = "20.838927",
    var opis : String = "Pieś jest bardzo niebezpieczny. Poszukiwana bardzo kompetentna osoba." +
            "Pozdrawiam," +
            "Krystian Kubica",
    var wynagrodzenie : String = "35",
    var img : String = "5",
    var length : String = "20",
    var time : String = "15:30",
    var title : String = "Spacer z psem",
    var ID : String = "0",
    var pracodawca : String = "Agnieszka Kubica"
)