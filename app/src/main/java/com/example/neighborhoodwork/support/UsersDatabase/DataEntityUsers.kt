package com.example.neighborhoodwork.support.UsersDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "avatars",
    indices =  [Index("uid")]
)
data class DataEntityUsers(

    @PrimaryKey
    @ColumnInfo(name = "uid")
    var uid: String,

    @ColumnInfo(name = "photo_url")
    var photoURL: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "completed")
    var completed: Int,

    @ColumnInfo(name = "days_with_app")
    var daysWithApp: Int,

    @ColumnInfo(name = "likes")
    var likes: Int,

    @ColumnInfo(name = "dislikes")
    var dislikes: Int,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "home_x")
    var homeX: Double,

    @ColumnInfo(name = "home_y")
    var homeY: Double,

    @ColumnInfo(name = "home_type")
    var HomeType: Int,

    @ColumnInfo(name = "home_adress")
    var homeAdress: String,

    @ColumnInfo(name = "version")
    var Version: Int
)