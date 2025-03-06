package com.example.splitwise_payg.event

sealed interface AccountEvent {
    data class Login(val emailAddress: String, val password: String) : AccountEvent
    data class SignUp(val fullName: String, val emailAddress: String, val password: String, val phoneNumber: String = "") :
        AccountEvent
    object Logout : AccountEvent
}