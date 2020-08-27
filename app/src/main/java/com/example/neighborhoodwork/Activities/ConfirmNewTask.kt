package com.example.neighborhoodwork.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.neighborhoodwor.ZadanieModel
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.time
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.commit_new_task.*
import java.util.*

class ConfirmNewTask : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.commit_new_task)

        tx24YourTask.text = dane.newTask.toString()

        lateinit var myRef : DatabaseReference
        val fireBase = FirebaseDatabase.getInstance()
        myRef = fireBase.getReference("Zadania")

        BT24PUSH.setOnClickListener {
            val fireBaseInput = dane.newTask
            myRef.child("${Date().time}").setValue(fireBaseInput)

            var home = Intent(applicationContext, Home::class.java)
            startActivity(home)
        }
            
    }
}