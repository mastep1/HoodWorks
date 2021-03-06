package com.example.neighborhoodwork.support.PhotosDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "photos",
    indices =  [Index("userid")]
)
data class DataEntityPhotos(
    @PrimaryKey
    @ColumnInfo(name = "userid")
    var userid: String,

    @ColumnInfo(name = "photo_in_bytes")
   var photoInBytes: ByteArray)
