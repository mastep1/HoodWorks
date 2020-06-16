package com.example.neighborhoodwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.info_window_big.*
import kotlinx.android.synthetic.main.row_chat.*

class F_MapWindow : Fragment() {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_window_big, container, false )
    }

    override fun onStart() {
        super.onStart()

        tx6LengthV.text = "${dane.zadania[dane.clicked].length} MIN"
        tx6WynagrodzenieV.text = "${dane.zadania[dane.clicked].wynagrodzenie} PLN"
        tx6StartV.text = dane.zadania[dane.clicked].time
        tx6OpisV.text = dane.zadania[dane.clicked].opis

        ratingBar6.rating = user.rating.toFloat()
        tx6Imie.text = user.imie
    }
}