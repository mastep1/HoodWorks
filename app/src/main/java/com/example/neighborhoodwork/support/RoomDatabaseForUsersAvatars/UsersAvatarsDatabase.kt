package com.example.neighborhoodwork.support.RoomDatabaseForUsersAvatars

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


private const val DATABASE = "avatars"

@Database(entities = [DataEntityUsersAvatars::class], version = 5, exportSchema = false)
abstract class UsersAvatarsDatabase : RoomDatabase() {

    abstract fun usersAvatarsDao(): UsersAvatarsDao

    companion object{

        @Volatile
        private var instance : UsersAvatarsDatabase? = null

        fun getInstance(context : Context) : UsersAvatarsDatabase{
                return instance ?: synchronized(this){
                     instance?: buildDatabase(context).also{ instance = it}
                }
        }

        private fun buildDatabase(context: Context): UsersAvatarsDatabase{
            return Room.databaseBuilder(context, UsersAvatarsDatabase::class.java, DATABASE).build()
        }
    }
}