package com.example.neighborhoodwork.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [SingleData::class], version = 3, exportSchema = false)
abstract class SingleDataBase : RoomDatabase() {

    abstract fun SingleDataDao() : SingleDataDao

    companion object{
        private var instance : SingleDataBase? = null

        fun getInstance(context : Context) : SingleDataBase?{
             if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    SingleDataBase::class.java,
                    "singleData_table")
                    .fallbackToDestructiveMigration()
                    .build()
             }
            return instance
        }

        fun deleteInstanceOfDatabase(){
             instance = null
        }
    }
}