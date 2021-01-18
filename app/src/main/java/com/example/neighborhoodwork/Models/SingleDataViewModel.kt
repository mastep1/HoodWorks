package com.example.neighborhoodwork.Models

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.neighborhoodwork.room.SingleData
import com.example.neighborhoodwork.room.SingleDataRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking


class SingleDataViewModel(application: Application) : AndroidViewModel(application){


    private var singleDataRepository : SingleDataRepository = SingleDataRepository(application)

    private var allSingleData : Deferred<LiveData<List<SingleData>>> = singleDataRepository.getAllDataAsync()

    fun insertData(singleData: SingleData, context: Context){
        singleDataRepository.insertData(singleData, context)
    }

    fun updateData(singleData: SingleData){
        singleDataRepository.updateData(singleData)
    }

    fun deleteData(singleData: SingleData){
        singleDataRepository.deleteData(singleData)
    }

    fun getAllSingleData() : LiveData<List<SingleData>> = runBlocking{
      allSingleData.await()
    }

    fun deleteAllRows(){
        singleDataRepository.deleteAllData()
    }
}