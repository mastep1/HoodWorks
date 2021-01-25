package com.example.neighborhoodwork.support.RoomDatabaseForUsersAvatars

import androidx.room.*
import com.firebase.ui.auth.data.model.User


@Dao
interface UsersAvatarsDao {

    @Query("SELECT * FROM avatars")
    suspend fun getAll(): List<DataEntityUsersAvatars>

    @Query("SELECT * FROM avatars WHERE uid = :userIds")
    suspend fun getByUid(userIds: String): DataEntityUsersAvatars

    @Delete
    suspend fun delete(thisOne: DataEntityUsersAvatars)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(thisOne: DataEntityUsersAvatars) : Long


}