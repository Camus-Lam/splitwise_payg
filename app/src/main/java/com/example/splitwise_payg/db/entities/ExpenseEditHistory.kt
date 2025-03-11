package com.example.splitwise_payg.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ExpenseEditHistory",
    foreignKeys = [
        ForeignKey(entity = Expense::class, parentColumns = ["id"], childColumns = ["expenseId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class ExpenseEditHistory(
    val expenseId: Long,
    val editTime: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
