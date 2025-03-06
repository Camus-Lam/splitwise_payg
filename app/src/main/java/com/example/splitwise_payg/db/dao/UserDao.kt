package com.example.splitwise_payg.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.splitwise_payg.db.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Int): Flow<User>

    @Query("SELECT * FROM user WHERE emailAddress = :emailAddress AND password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(emailAddress: String, password: String): User?

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE emailAddress = :emailAddress LIMIT 1)")
    suspend fun doesEmailExist(emailAddress: String): Boolean
}