package com.example.neighborhoodwork.support.UsersDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


private const val DATABASE = "avatars"

@Database(entities = [DataEntityUsers::class], version = 24, exportSchema = false)
abstract class DatabaseUsers : RoomDatabase() {

    abstract fun usersAvatarsDao(): DaoUsers

    companion object{

        @Volatile
        private var instance : DatabaseUsers? = null

        fun getInstance(context : Context) : DatabaseUsers{
                return instance ?: synchronized(this){
                     instance?: buildDatabase(context).also{ instance = it}
                }
        }

        private fun buildDatabase(context: Context): DatabaseUsers{
            return Room.databaseBuilder(context, DatabaseUsers::class.java, DATABASE).fallbackToDestructiveMigration().build()
        }
    }
}