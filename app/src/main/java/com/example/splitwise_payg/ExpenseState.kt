package com.example.splitwise_payg

import com.example.splitwise_payg.db.domainModel.UserExpenseList

data class ExpenseState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val expenses: UserExpenseList? = null
)