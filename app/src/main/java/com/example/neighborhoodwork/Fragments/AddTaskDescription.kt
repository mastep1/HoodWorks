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

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        super.onResume()


        plusesAndMinuses()

        Etx23Description.addTextChangedListener{
            var howMany = 500 - it.toString().length
            howManyTags(howMany)
        }


       
    }

    

    fun plusesAndMinuses(){

        img23PlusMonay.setOnClickListener {
            if(Etx23Monay.text.toString() == ""){
                Etx23Monay.setText("25 PLN")
                dane.newTask.wynagrodzenie = 25.toString()
            }else{
                dane.newTask.wynagrodzenie = (returnNumber(Etx23Monay).toInt() + 5).toString()
                Etx23Monay.setText(dane.newTask.wynagrodzenie + " PLN")
            }
        }

        img23MinusMonay.setOnClickListener {
            if(Etx23Monay.text.toString() == ""){
                Etx23Monay.setText("15 PLN")
                dane.newTask.wynagrodzenie = 15.toString()
            }else{
                if(dane.newTask.wynagrodzenie.toInt() > 11){
                    dane.newTask.wynagrodzenie = (returnNumber(Etx23Monay).toInt() - 5).toString()
                    Etx23Monay.setText(dane.newTask.wynagrodzenie + " PLN")
                }else{
                    Toast.makeText(context,"Wynagrodzenie nie może być niższe niż 10 zł", Toast.LENGTH_LONG).show()
                }

            }
        }

        img23PlusLength.setOnClickListener {
            if(Etx23Length.text.toString() == ""){
                Etx23Length.setText("25 min")
                dane.newTask.length = 25.toString()
            }else{
                dane.newTask.length = (returnNumber(Etx23Length).toInt() + 5).toString()
                Etx23Length.setText(dane.newTask.length + " min")
            }
        }

        img23MinusLength.setOnClickListener {
            if(Etx23Length.text.toString() == ""){
                Etx23Length.setText("15 min")
                dane.newTask.length = 15.toString()
            }else{
                if(dane.newTask.length.toInt() > 11){
                    dane.newTask.length = (returnNumber(Etx23Length).toInt() - 5).toString()
                    Etx23Length.setText(dane.newTask.length + " min")
                }else{
                    Toast.makeText(context,"Minimalny czas trwania to 10 min", Toast.LENGTH_LONG).show()
                }

            }
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun howManyTags(tags : Int){

        if(tags < 0){
            Etx23Description.text.delete(500,501)
            tx23Tags.setTextColor(R.color.fuckingblack)
            tx23Tags.text = "0 znaków pozostało"
        }else{
            tx23Tags.text = "${tags} znaków pozostało"
            tx23Tags.setTextColor(R.color.quantum_googred700)
        }
    }


    fun returnNumber(etx: TextView) : String{

        var number = ""
        var index = 0

        while(true){
              if(etx.text.toString()[index].toString() != " "){
                  number = "$number${etx.text.toString()[index]}"
              } else{
                  return number
              }

            index++
        }
        return number
    }


}