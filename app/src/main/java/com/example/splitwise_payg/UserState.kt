package com.example.splitwise_payg

data class UserState(
    val fullName: String = "",
    val emailAddress: String = "",
    val phoneNumber: String? = "",
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

