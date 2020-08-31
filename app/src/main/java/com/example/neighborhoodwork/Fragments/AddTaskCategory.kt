package com.example.neighborhoodwork.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import kotlinx.android.synthetic.main.add_task_category.*


class AddTaskCategory : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_category, container, false)
    }

    override fun onResume(){
        super.onResume()

        if(dane.newTask.img!= "taklubnie"){
            when(dane.newTask.img){
                1.toString() -> tx21SelectCategory.text = "Zwierzęta"
                2.toString() -> tx21SelectCategory.text = "Sprzątanie"
                3.toString() -> tx21SelectCategory.text = "Ogród"
                4.toString() -> tx21SelectCategory.text = "Zakupy"
                5.toString() -> tx21SelectCategory.text = "Prace fizyczne"
                6.toString() -> tx21SelectCategory.text = "Inne"
            }
        }

        tx21SelectCategory.setOnClickListener {
            if(l21Cleaning.visibility == View.GONE){
                l21Cleaning.visibility = View.VISIBLE
                l21Garden.visibility = View.VISIBLE
                l21Shopping.visibility = View.VISIBLE
                l21PhysicalWorks.visibility = View.VISIBLE
                l21Animal.visibility = View.VISIBLE
                l21Other.visibility = View.VISIBLE
            }else{
               setGoneList()
            }
        }

        l21Animal.setOnClickListener {
            tx21SelectCategory.text = "Zwirzęta"
            setGoneList()
            dane.newTask.img = 1.toString()
        };l21Cleaning.setOnClickListener {
            tx21SelectCategory.text = "Sprzątanie"
            setGoneList()
            dane.newTask.img = 2.toString()
        }; l21Garden.setOnClickListener {
            tx21SelectCategory.text = "Ogród"
            setGoneList()
            dane.newTask.img = 3.toString()
        }; l21Shopping.setOnClickListener {
            tx21SelectCategory.text = "Zakupy"
            setGoneList()
            dane.newTask.img = 4.toString()
        }; l21PhysicalWorks.setOnClickListener {
            tx21SelectCategory.text = "Prace fizyczne"
            setGoneList()
            dane.newTask.img = 5.toString()
        }; l21Other.setOnClickListener {
            tx21SelectCategory.text = "Inne"
            setGoneList()
            dane.newTask.img = 6.toString()
        }
    }


    fun setGoneList(){
        l21Cleaning.visibility = View.GONE
        l21Garden.visibility = View.GONE
        l21Shopping.visibility = View.GONE
        l21PhysicalWorks.visibility = View.GONE
        l21Animal.visibility = View.GONE
        l21Other.visibility = View.GONE
    }
}