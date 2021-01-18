package com.example.neighborhoodwork.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SingleDataDao {

    @Insert
    fun insert(singleData : SingleData) 

    @Update
    fun update(singleData : SingleData)

    @Delete
    fun delate(singleData : SingleData)

    @Query("SELECT * FROM singleData_table")
    fun getAllData() : LiveData<List<SingleData>>

    @Query("DELETE FROM singleData_table")
    fun delateAllRows()
}