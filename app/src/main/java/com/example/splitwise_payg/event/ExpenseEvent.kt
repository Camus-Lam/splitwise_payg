package com.example.splitwise_payg.event

import com.example.splitwise_payg.db.entities.Expense

sealed interface ExpenseEvent {
    data class addExpense(
        val amount: String,
        val currency: String = "CAD",
        val ownershipType: String,
        val splitType: String,
        val targetUserId: String? = null,
        val targetGroupId: String? = null
    ) : ExpenseEvent

    data class showExpenses(
        val userId: Int
    ) : ExpenseEvent

    data class deleteExpense(
        val expense: Expense
    ) : ExpenseEvent
}