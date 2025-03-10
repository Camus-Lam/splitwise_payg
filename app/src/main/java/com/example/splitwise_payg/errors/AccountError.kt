package com.example.splitwise_payg.errors

import com.example.splitwise_payg.R

sealed class AccountError(val messageResId: Int, val args: List<Any> = emptyList()) : Exception() {
    data class InvalidCredentials(val resId: Int = R.string.error_invalid_credentials) : AccountError(resId)
    data class EmailAlreadyExists(val resId: Int = R.string.error_email_already_exists) : AccountError(resId)
    data class UnknownError(val resId: Int = R.string.error_unknown) : AccountError(resId)
}