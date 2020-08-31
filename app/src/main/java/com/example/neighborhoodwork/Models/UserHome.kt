package com.example.neighborhoodwork.Models

import com.google.android.gms.maps.model.LatLng

data class UserHome (var latLng: LatLng = LatLng(0.0, 0.0), var address: String, var type : Int )

//// type type type
/// 1 - blok
/// 2 - szeregoziec / bliźniak
/// 3 - dom wolno stojący
