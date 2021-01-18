package com.example.neighborhoodwork.room

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class SingleDataRepository(application : Application) {

    private var SingleDataDao : SingleDataDao

    init{
         val database = SingleDataBase
             .getInstance(application.applicationContext)

        SingleDataDao = database!!.SingleDataDao()
    }

    fun insertData(singleData : SingleData, context : Context){
        CoroutineScope(Dispatchers.IO).launch {
            SingleDataDao.insert(singleData)
            //Toast.makeText(context, "I M Here", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(singleData : SingleData){
        CoroutineScope(Dispatchers.IO).launch {
            SingleDataDao.delate(singleData)
        }
    }



    fun updateData(singleData : SingleData){
        CoroutineScope(Dispatchers.IO).launch {
            SingleDataDao.update(singleData)
        }
    }

    fun getAllDataAsync() : Deferred<LiveData<List<SingleData>>> =
        CoroutineScope(Dispatchers.IO).async {
            SingleDataDao.getAllData()
        }


    fun deleteAllData(){
        CoroutineScope(Dispatchers.IO).launch {
            SingleDataDao.delateAllRows()
        }
    }
}