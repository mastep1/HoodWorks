package com.example.neighborhoodwork.Fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import com.example.neighborhoodwork.support.time
import kotlinx.android.synthetic.main.add_task_time.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

class AddTaskTime : Fragment() {

    var minForOnStop = 0
    var hourForonStop = 0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_time, container, false)
    }

    private fun howManyDaysInMonth(intMonth : Int, intYear : Int) : Int{

        when(intMonth){
            1 -> return 31
            2 -> {
                if(     intYear-2020==0 ||
                        intYear-2020==4 ||
                        intYear-2020==8 ||
                        intYear-2020==12 ||
                        intYear-2020==16||
                        intYear-2020==20||
                        intYear-2020==24){
                    return 29
                }
                else{
                    return 28
                }
            }
            3 -> return 31
            4 -> return 30
            5 -> return 31
            6 -> return 30
            7 -> return 31
            8 -> return 31
            9 -> return 30
            10 -> return 31
            11 -> return 30
            12 -> return 31
            else -> {
                return 0
            }
        }
    }

    @ExperimentalTime
    override fun onResume() {

        if(time.timeSet){
            tx2224H.visibility = View.VISIBLE

            if(time.min < 10){
                tx22SetTime.setText("Wybrany czas: ${time.hour}:0${time.min}")
            }else{
                tx22SetTime.setText("Wybrany czas: ${time.hour}:${time.min}")
            }
            
        }

        if(time.dateSet){
            calendarView22.setDate(time.dayMonthYear, false, false)
        }
        
        calendarView22.setOnDateChangeListener { view, year, month, dayOfMonth ->

                time.dateSet = true
                time.dayMonthYear = view.date

            var new = Calendar.getInstance()
            new.set(year, month, dayOfMonth)
            time.dayMonthYear = new.timeInMillis
            Log.e("Tag", "${dane.newTask.time}")
        }

        tx22SetTime.setOnClickListener(View.OnClickListener {

            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR)
            val min = c.get(Calendar.MINUTE)

            val dpd = TimePickerDialog(context!!, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    if(minute < 10){
                        tx22SetTime.setText("Wybrany czas: $hourOfDay:0$minute")
                    }else{
                        tx22SetTime.setText("Wybrany czas: $hourOfDay:$minute")
                    }

                    time.timeSet = true
                    time.min = minute
                    time.hour = hourOfDay

                    tx2224H.visibility = View.VISIBLE

                    minForOnStop = minute
                    hourForonStop = hourOfDay
                }, hour, min, true)
            dpd.show()
        })

        super.onResume()
    }

    override fun onStop() {
        super.onStop()


        if(time.timeSet && time.dateSet){

            dane.newTask.time = (time.min * 60 + time.hour * 60 * 60 + time.dayMonthYear).toString()
        }
        super.onResume()

    }

}

