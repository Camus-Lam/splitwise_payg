package com.example.splitwise_payg.errors

import com.example.splitwise_payg.R

sealed class ExpenseError(val messageResId: Int, val args: List<Any> = emptyList()) : Exception() {
    data class UnknownError(val resId: Int = R.string.error_unknown) : ExpenseError(resId)
    data class AddExpenseError(val resId: Int = R.string.error_message_add_expense_failed) : ExpenseError(resId)
}