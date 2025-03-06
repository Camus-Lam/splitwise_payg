package com.example.splitwise_payg.db.domainModel

import com.example.splitwise_payg.db.entities.Expense
import com.example.splitwise_payg.db.entities.relations.UserWithExpenses

data class UserExpenseList(
    val userId: Int,
    val createdExpenses: List<Expense>,
    val targetedExpenses: List<Expense>,
    val allExpenses: List<Expense> = createdExpenses + targetedExpenses
) {
    companion object {
        fun fromUserWithExpenses(userWithExpenses: UserWithExpenses): UserExpenseList {
            return UserExpenseList(
                userId = userWithExpenses.user.id,
                createdExpenses = userWithExpenses.createdExpenses,
                targetedExpenses = userWithExpenses.targetedExpenses
            )
        }
    }

    fun sortedByTime(descending: Boolean = true): UserExpenseList {
        val sortFunc = if (descending) { list: List<Expense> -> list.sortedByDescending { it.createTime } }
        else { list: List<Expense> -> list.sortedBy { it.createTime } }
        return copy(
            createdExpenses = sortFunc(createdExpenses),
            targetedExpenses = sortFunc(targetedExpenses),
            allExpenses = sortFunc(allExpenses)
        )
    }
}