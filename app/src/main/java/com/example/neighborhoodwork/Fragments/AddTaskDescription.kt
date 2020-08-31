package com.example.neighborhoodwork.Fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import kotlinx.android.synthetic.main.add_task_description.*


class AddTaskDescription : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_description, container, false)
    }

    override fun onResume() {
        super.onResume()

        Etx23Description.addTextChangedListener{
            var howMany = 500 - it.toString().length
            howManyTags(howMany)
        }


       
    }

    




    private fun howManyTags(tags : Int){

            tx23Tags.text = "${tags} znaków pozostało"

    }




}