package com.example.neighborhoodwork.Activities


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.neighborhoodwork.Fragments.*
import com.example.neighborhoodwork.R
import com.example.neighborhoodwork.support.dane
import kotlinx.android.synthetic.main.add_task_description.*
import kotlinx.android.synthetic.main.dodaj_zlecenie.*


class DodajZlecenie : AppCompatActivity() {

    var fragOfTheMap = supportFragmentManager
    var fragOfTheTitle = supportFragmentManager
    var fragOfTheCategory = supportFragmentManager
    var fragOfTheTime = supportFragmentManager
    var fragOfTheDesciption = supportFragmentManager
    var fragOfTheMoney = supportFragmentManager

    var addTaskMap = AddTaskMap(this)
    var addTaskTitle = AddTaskTitle(this)
    var addTaskCategory = AddTaskCategory()
    var addTaskTime = AddTaskTime()
    var addTaskDescrption = AddTaskDescription()
    var addTaskMoney = AddTaskMoney()

    public override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dodaj_zlecenie)

        img18DotOne.setColorFilter(R.color.fuckingblack)
        dane.addTask = 1


        setBottomDot()

    }



    fun setBottomDot() {

        fragOfTheMap.beginTransaction().add(R.id.l18ForFragment, addTaskMap).commit()

        img18RightArrow.setOnClickListener {

            when(dane.addTask){
                1 ->  { img18DotOne.clearColorFilter()
                        img18DotTwo.setColorFilter(R.color.fuckingblack)
                        fragOfTheMap.beginTransaction().remove(addTaskMap).commit()
                        fragOfTheTitle.beginTransaction().add(R.id.l18ForFragment, addTaskTitle).commit()
                }

                2 ->  { img18DotTwo.clearColorFilter()
                        img18DotThree.setColorFilter(R.color.fuckingblack)
                        fragOfTheTitle.beginTransaction().remove(addTaskTitle).commit()
                        fragOfTheCategory.beginTransaction().add(R.id.l18ForFragment, addTaskCategory).commit()
                }

                3 ->  { img18DotThree.clearColorFilter()
                        img18DotFour.setColorFilter(R.color.fuckingblack)
                        fragOfTheCategory.beginTransaction().remove(addTaskCategory).commit()
                        fragOfTheTime.beginTransaction().add(R.id.l18ForFragment, addTaskTime).commit()
                }

                4 ->  { img18DotFour.clearColorFilter()
                        img18DotFive.setColorFilter(R.color.fuckingblack)
                        fragOfTheTime.beginTransaction().remove(addTaskTime).commit()
                        fragOfTheDesciption.beginTransaction().add(R.id.l18ForFragment, addTaskDescrption).commit()
                }

                5 -> {
                    dane.newTask.opis = Etx23Description.text.toString()
                    img18DotFour.clearColorFilter()
                    img18DotFive.setColorFilter(R.color.fuckingblack)
                    fragOfTheDesciption.beginTransaction().remove(addTaskDescrption).commit()
                    fragOfTheMoney.beginTransaction().add(R.id.l18ForFragment, addTaskMoney).commit()
                }

                6 ->{
                    val timestamp = System.currentTimeMillis()
                    dane.newTask.ID = "${dane.currentUser.providerId}$timestamp"
                    dane.newTask.pracodawca = dane.currentUser.displayName.toString()
                    var intent = Intent(this, ConfirmNewTask::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            dane.addTask ++
        }

        img18LeftArrow.setOnClickListener {

            when(dane.addTask) {

                2 -> {
                    img18DotTwo.clearColorFilter()
                    img18DotOne.setColorFilter(R.color.fuckingblack)
                    fragOfTheMap.beginTransaction().add(R.id.l18ForFragment, addTaskMap).commit()
                    fragOfTheTitle.beginTransaction().remove(addTaskTitle).commit()
                }

                3 -> {
                    img18DotThree.clearColorFilter()
                    img18DotTwo.setColorFilter(R.color.fuckingblack)
                    fragOfTheTitle.beginTransaction().add(R.id.l18ForFragment, addTaskTitle)
                        .commit()
                    fragOfTheCategory.beginTransaction().remove(addTaskCategory).commit()
                }

                4 -> {
                    img18DotFour.clearColorFilter()
                    img18DotThree.setColorFilter(R.color.fuckingblack)
                    fragOfTheCategory.beginTransaction().add(R.id.l18ForFragment, addTaskCategory)
                        .commit()
                    fragOfTheTime.beginTransaction().remove(addTaskTime).commit()
                }

                5 -> {
                    img18DotFive.clearColorFilter()
                    img18DotFour.setColorFilter(R.color.fuckingblack)
                    fragOfTheDesciption.beginTransaction().remove(addTaskDescrption).commit()
                    fragOfTheCategory.beginTransaction().add(R.id.l18ForFragment, addTaskTime)
                        .commit()
                }

                6 -> {
                    fragOfTheMoney.beginTransaction().remove(addTaskMoney).commit()
                    fragOfTheDesciption.beginTransaction().add(R.id.l18ForFragment, addTaskDescrption).commit()
                }
            }
            dane.addTask = dane.addTask - 1
        }



    
    }


}



