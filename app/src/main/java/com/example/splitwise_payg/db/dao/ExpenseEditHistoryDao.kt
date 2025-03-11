package com.example.splitwise_payg.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.splitwise_payg.db.entities.ExpenseEditHistory
import com.example.splitwise_payg.db.entities.ExpenseEditHistoryFieldChange
import com.example.splitwise_payg.db.entities.relations.ExpenseEditHistoryWithFieldChange
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseEditHistoryDao {

    @Upsert
    suspend fun upsertExpenseEditHistory(expenseEditHistory: ExpenseEditHistory): Long

    @Upsert
    suspend fun upsertExpenseEditHistoryFieldChange(expenseEditHistoryFieldChange: ExpenseEditHistoryFieldChange): Long

    @Delete
    suspend fun deleteExpenseEditHistory(expenseEditHistory: ExpenseEditHistory)

    @Delete
    suspend fun deleteExpenseEditHistoryFieldChange(expenseEditHistoryFieldChange: ExpenseEditHistoryFieldChange)

    @Query("SELECT * FROM expenseEditHistory WHERE expenseId = :expenseId")
    fun getExpenseEditHistory(expenseId: Int): Flow<List<ExpenseEditHistory>>

    @Query("SELECT * FROM expenseEditHistory WHERE expenseId = :expenseId")
    fun getExpenseEditHistoryWithFieldChanges(expenseId: Int): Flow<List<ExpenseEditHistoryWithFieldChange>>
}