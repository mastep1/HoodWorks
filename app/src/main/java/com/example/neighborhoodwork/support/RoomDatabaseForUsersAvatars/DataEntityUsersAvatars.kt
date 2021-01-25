package com.example.neighborhoodwork.support.RoomDatabaseForUsersAvatars

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "avatars",
    indices =  [Index("uid")]
)
data class DataEntityUsersAvatars(

    @PrimaryKey
    @ColumnInfo(name = "uid")
    val uid: String,

    @ColumnInfo(name = "photo")
    val photo: ByteArray
)