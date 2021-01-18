package com.example.neighborhoodwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.neighborhoodwork.Models.SingleDataViewModel
import com.example.neighborhoodwork.room.SingleData
import kotlinx.android.synthetic.main.activity_room.*

class Room : AppCompatActivity() {

    private lateinit var viewModel : SingleDataViewModel
    private lateinit var viewModel2 : SingleDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(SingleDataViewModel::class.java)

        viewModel2 = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(SingleDataViewModel::class.java)

        buttonOne.setOnClickListener {
            var number = ETXONE.text.toString().toInt()

            var singleData = SingleData(number)

            viewModel.insertData(singleData, applicationContext)
        }

        buttonTWo.setOnClickListener {
            var sy = viewModel2.getAllSingleData()

            Toast.makeText(applicationContext, "$sy.", Toast.LENGTH_SHORT).show()
        }

        
    }
}