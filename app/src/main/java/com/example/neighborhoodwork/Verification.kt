package com.example.neighborhoodwork

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.edit_profil.*
import kotlinx.android.synthetic.main.verification.*


class Verification(newEmail: String, context: Context) : Fragment() {

var newEmail2  = newEmail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.verification, container, false)
    }

    override fun onStart() {
        super.onStart()
        BT10Potwierdz.setOnClickListener {
            context?.let { it1 -> fireBaseConnection().ustawEmail("$newEmail2", it1) }
        }
    }


}
