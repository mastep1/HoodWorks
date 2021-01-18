package com.example.neighborhoodwork.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "singleData_table")
data class SingleData(var numberOfMessagesToDownload : Int ){

    @PrimaryKey(autoGenerate = true)
     var id : Int = 0
}

