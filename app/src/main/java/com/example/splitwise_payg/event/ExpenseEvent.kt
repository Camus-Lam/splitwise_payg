package com.example.splitwise_payg.event

import com.example.splitwise_payg.db.entities.Expense
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType

sealed interface ExpenseEvent {
    data class addExpense(
        val amount: String,
        val currency: String,
        val ownershipType: OwnershipType,
        val splitType: SplitType,
        val targetUserId: String? = null,
        val targetGroupId: String? = null
    ) : ExpenseEvent

    data class showExpenses(
        val userId: Int
    ) : ExpenseEvent

    data class deleteExpense(
        val expense: Expense
    ) : ExpenseEvent

    data class editExpense(
        val amount: String,
        val currency: String,
        val ownershipType: OwnershipType,
        val splitType: SplitType,
        val targetUserId: String? = null,
        val targetGroupId: String? = null,
        val expense: Expense
    ) : ExpenseEvent
}