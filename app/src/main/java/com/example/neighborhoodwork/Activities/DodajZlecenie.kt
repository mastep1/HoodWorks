package com.example.neighborhoodwork.Activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neighborhoodwork.Fragments.AddTaskMap
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import kotlinx.android.synthetic.main.dodaj_zlecenie.*


class DodajZlecenie : AppCompatActivity() {

    var fragOfTheMap = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dodaj_zlecenie)

        img18DotOne.setColorFilter(R.color.fuckingblack)
        dane.addTask = 1

        var addTaskMap = AddTaskMap(this)
        fragOfTheMap.beginTransaction().add(R.id.l18ForFragment, addTaskMap).commit()

        setBottomDot()





    }



    fun setBottomDot(){

        img18RightArrow.setOnClickListener {

            when(dane.addTask){
                1 ->  { img18DotOne.clearColorFilter()
                    img18DotTwo.setColorFilter(R.color.fuckingblack)}

                2 ->  { img18DotTwo.clearColorFilter()
                    img18DotThree.setColorFilter(R.color.fuckingblack)}

                3 ->  { img18DotThree.clearColorFilter()
                    img18DotFour.setColorFilter(R.color.fuckingblack)}

                4 ->  { img18DotFour.clearColorFilter()
                    img18DotFive.setColorFilter(R.color.fuckingblack)}

                5 -> {
                    var intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            dane.addTask ++
        }

        img18LeftArrow.setOnClickListener {

            when(dane.addTask){

                2 ->  { img18DotTwo.clearColorFilter()
                    img18DotOne.setColorFilter(R.color.fuckingblack)}

                3 ->  { img18DotThree.clearColorFilter()
                    img18DotTwo.setColorFilter(R.color.fuckingblack)}

                4 ->  { img18DotFour.clearColorFilter()
                    img18DotThree.setColorFilter(R.color.fuckingblack)}

                5 -> { img18DotFive.clearColorFilter()
                    img18DotFour.setColorFilter(R.color.fuckingblack)}
            }
            dane.addTask = dane.addTask - 1
        }
    }

    
}


