package com.example.neighborhoodwork.support.PhotosDatabase

import androidx.room.*


@Dao
interface DaoPhotos {
    @Query("SELECT * FROM photos")
    suspend fun getAll(): List<DataEntityPhotos>

    @Query("SELECT * FROM photos WHERE userid = :userIds2")
    suspend fun getByUid(userIds2: String): DataEntityPhotos

    @Delete
    suspend fun delete(thisOne: DataEntityPhotos)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(thisOne: DataEntityPhotos) : Long
}