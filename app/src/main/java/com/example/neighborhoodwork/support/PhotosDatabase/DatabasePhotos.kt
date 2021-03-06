package com.example.neighborhoodwork.support.PhotosDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


private const val DATABASE = "photos"


@Database(entities = [DataEntityPhotos::class], version = 2, exportSchema = false)
abstract class DatabasePhotos : RoomDatabase() {

    abstract fun daoPhotos(): DaoPhotos

    companion object{


        @Volatile
        private var instance : DatabasePhotos? = null

        fun getInstance(context : Context) : DatabasePhotos{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also{ instance = it}
            }
        }

        private fun buildDatabase(context: Context): DatabasePhotos{
            return Room.databaseBuilder(context, DatabasePhotos::class.java, DATABASE).fallbackToDestructiveMigration().build()
        }
    }
}