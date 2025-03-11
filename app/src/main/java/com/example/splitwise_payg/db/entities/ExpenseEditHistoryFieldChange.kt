package com.example.splitwise_payg.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ExpenseEditHistoryFieldChange",
    foreignKeys = [
        ForeignKey(entity = ExpenseEditHistory::class, parentColumns = ["id"], childColumns = ["editHistoryId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class ExpenseEditHistoryFieldChange(
    val editHistoryId: Long,
    val fieldName: String,
    val oldValue: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
