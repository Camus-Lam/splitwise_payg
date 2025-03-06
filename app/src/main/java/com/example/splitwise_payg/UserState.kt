package com.example.splitwise_payg

import com.example.splitwise_payg.db.domainModel.UserExpenseList

data class UserState(
    val fullName: String = "",
    val emailAddress: String = "",
    val phoneNumber: String? = "",
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userId: Int? = null,
    val expenses: UserExpenseList? = null
)

