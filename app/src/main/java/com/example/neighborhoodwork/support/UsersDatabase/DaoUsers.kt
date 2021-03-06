package com.example.neighborhoodwork.support.UsersDatabase

import androidx.room.*


@Dao
interface DaoUsers {

    @Query("SELECT * FROM avatars")
    suspend fun getAll(): List<DataEntityUsers>

    @Query("SELECT * FROM avatars WHERE uid = :userIds")
    suspend fun getByUid(userIds: String): DataEntityUsers

    @Delete
    suspend fun delete(thisOne: DataEntityUsers)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(thisOne: DataEntityUsers) : Long
}