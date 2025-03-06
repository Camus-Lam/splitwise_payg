package com.example.splitwise_payg.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.splitwise_payg.db.entities.Expense
import com.example.splitwise_payg.db.entities.relations.UserWithExpenses
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Upsert
    suspend fun upsertExpense(expense: Expense): Long

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expense WHERE id = :id")
    fun getExpenseById(id: Int): Flow<Expense>

    @Transaction
    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserWithExpenses(userId: Int): Flow<UserWithExpenses>
}