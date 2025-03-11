package com.example.splitwise_payg.db.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.splitwise_payg.db.entities.ExpenseEditHistory
import com.example.splitwise_payg.db.entities.ExpenseEditHistoryFieldChange

data class ExpenseEditHistoryWithFieldChange(
    @Embedded val expenseEditHistory: ExpenseEditHistory,
    @Relation(
        parentColumn = "id",
        entityColumn = "editHistoryId"
    )
    val expenseEditHistoryFieldChanges: List<ExpenseEditHistoryFieldChange>
)
