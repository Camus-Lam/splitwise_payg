package com.example.splitwise_payg.viewModel

import android.icu.util.Currency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splitwise_payg.event.AccountEvent
import com.example.splitwise_payg.event.ExpenseEvent
import com.example.splitwise_payg.UserState
import com.example.splitwise_payg.db.respositories.AccountRepository
import com.example.splitwise_payg.db.respositories.ExpenseRepository
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal

class UserViewModel(
    private val accountRepository: AccountRepository,
    private val expenseRepository: ExpenseRepository
): ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()

    fun onAccountEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.Login -> viewModelScope.launch{
                _state.value = UserState(isLoading = true)
                val result = accountRepository.userLogin(event.emailAddress, event.password)
                _state.value = if (result.isSuccess) {
                    val user = result.getOrNull()!!
                    UserState(
                        fullName = user.fullName,
                        emailAddress = user.emailAddress,
                        isLoggedIn = true,
                        isLoading = false,
                        userId = user.id
                    )
                } else {
                    UserState(
                        errorMessage = result.exceptionOrNull()?.message ?: "Login failed",
                        isLoading = false
                    )
                }
            }
            is AccountEvent.SignUp -> viewModelScope.launch {
                _state.value = UserState(isLoading = true)
                val result = accountRepository.userSignUp(event.fullName, event.emailAddress, event.password, event.phoneNumber)
                _state.value = if(result.isSuccess) {
                    UserState(
                        fullName = event.fullName,
                        emailAddress = event.emailAddress,
                        phoneNumber = event.phoneNumber,
                        isLoggedIn = true,
                        isLoading = false,
                        userId = result.getOrNull()!!.toInt()
                    )
                } else {
                    UserState(
                        errorMessage = result.exceptionOrNull()?.message ?: "Sign up failed",
                        isLoading = false
                    )
                }
            }

            is AccountEvent.Logout -> viewModelScope.launch {
                _state.value = UserState()
            }
        }
    }

    fun onExpenseEvent(event: ExpenseEvent) {
        when (event) {
            is ExpenseEvent.addExpense -> viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true)
                expenseRepository.addExpense(
                    amount = BigDecimal(event.amount) ,
                    currency = Currency.getInstance(event.currency),
                    creatorId = state.value.userId!!,
                    ownershipType = OwnershipType.fromString(event.ownershipType)!!,
                    splitType = SplitType.fromString(event.splitType)!!,
                    targetUserId = event.targetUserId?.toIntOrNull(),
                    targetGroupId = event.targetGroupId?.toIntOrNull()
                ).onFailure { e ->
                    _state.value = _state.value.copy(errorMessage = "Add Expense failed, please make sure you input the correct values", isLoading = false)
                }.onSuccess {
                    onExpenseEvent(ExpenseEvent.showExpenses(state.value.userId!!))
                }
            }
            is ExpenseEvent.deleteExpense -> viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true)
                expenseRepository.deleteExpense(event.expense).onFailure { e ->
                    _state.value = _state.value.copy(errorMessage = e.message, isLoading = false)
                }.onSuccess {
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
            is ExpenseEvent.showExpenses -> viewModelScope.launch {
                if (_state.value.expenses == null) {
                    expenseRepository.getUserExpenses(_state.value.userId!!).collectLatest { expenseList ->
                        _state.value = _state.value.copy(
                            expenses = expenseList,
                            isLoading = false
                        )
                    }
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}