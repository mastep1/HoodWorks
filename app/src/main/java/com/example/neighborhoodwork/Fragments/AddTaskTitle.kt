package com.example.neighborhoodwork.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import kotlinx.android.synthetic.main.add_task_title.*


class AddTaskTitle(context : Context) : Fragment() {


  var myContext = context


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_title, container, false)
    }


    override fun onResume(){
        super.onResume()

        if(dane.newTask.title != "taklubnie"){
            Etx20Title.setText(dane.newTask.title)
            howManyTags(40 - dane.newTask.title.length)
        }


        Etx20Title.addTextChangedListener{
            var howMany = 40 - it.toString().length
            howManyTags(howMany)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun howManyTags(tags : Int){


        if(tags < 0){
            Etx20Title.text.delete(40, 41)
            tx20HowManyTags.setTextColor(R.color.fuckingblack)
            tx20HowManyTags.text = "0 znaków pozostało"
        }else{
            tx20HowManyTags.text = "${tags} znaków pozostało"
            tx20HowManyTags.setTextColor(R.color.quantum_googred700)
        }
    }

    override fun onStop() {
        super.onStop()
        dane.newTask.title = Etx20Title.text.toString()
    }

}