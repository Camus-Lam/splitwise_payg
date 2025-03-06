package com.example.splitwise_payg.db.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.splitwise_payg.db.entities.Expense
import com.example.splitwise_payg.db.entities.User

data class UserWithExpenses(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "creatorId",
        entity = Expense::class
    )
    val createdExpenses: List<Expense>,
    @Relation(
        parentColumn = "id",
        entityColumn = "targetUserId",
        entity = Expense::class
    )
    val targetedExpenses: List<Expense>
)
