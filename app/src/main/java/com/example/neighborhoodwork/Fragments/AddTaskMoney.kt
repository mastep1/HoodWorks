package com.example.neighborhoodwork.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import kotlinx.android.synthetic.main.add_task_money.*


class AddTaskMoney : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_money, container, false)
    }

    override fun onResume() {
        super.onResume()
        
        plusesAndMinuses()
    }

    fun plusesAndMinuses(){

        img24PlusMonay.setOnClickListener {
            if(Etx24Monay.text.toString() == ""){
                Etx24Monay.setText("25 PLN")
                dane.newTask.wynagrodzenie = 25.toString()
            }else{
                dane.newTask.wynagrodzenie = (returnNumber(Etx24Monay).toInt() + 5).toString()
                Etx24Monay.setText(dane.newTask.wynagrodzenie + " PLN")
            }
        }

        img24MinusMonay.setOnClickListener {
            if(Etx24Monay.text.toString() == ""){
                Etx24Monay.setText("15 PLN")
                dane.newTask.wynagrodzenie = 15.toString()
            }else{
                if(dane.newTask.wynagrodzenie.toInt() > 11){
                    dane.newTask.wynagrodzenie = (returnNumber(Etx24Monay).toInt() - 5).toString()
                    Etx24Monay.setText(dane.newTask.wynagrodzenie + " PLN")
                }else{
                    Toast.makeText(context,"Wynagrodzenie nie może być niższe niż 10 zł", Toast.LENGTH_LONG).show()
                }

            }
        }

        img24PlusLength.setOnClickListener {
            if(Etx24Length.text.toString() == ""){
                Etx24Length.setText("25 min")
                dane.newTask.length = 25.toString()
            }else{
                dane.newTask.length = (returnNumber(Etx24Length).toInt() + 5).toString()
                Etx24Length.setText(dane.newTask.length + " min")
            }
        }

        img24MinusLength.setOnClickListener {
            if(Etx24Length.text.toString() == ""){
                Etx24Length.setText("15 min")
                dane.newTask.length = 15.toString()
            }else{
                if(dane.newTask.length.toInt() > 11){
                    dane.newTask.length = (returnNumber(Etx24Length).toInt() - 5).toString()
                    Etx24Length.setText(dane.newTask.length + " min")
                }else{
                    Toast.makeText(context,"Minimalny czas trwania to 10 min", Toast.LENGTH_LONG).show()
                }

            }
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