package com.example.splitwise_payg.viewModel

import android.icu.util.Currency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splitwise_payg.ExpenseState
import com.example.splitwise_payg.db.respositories.AccountRepository
import com.example.splitwise_payg.db.respositories.ExpenseRepository
import com.example.splitwise_payg.event.ExpenseEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ExpenseViewModel(
    private val expenseRepository: ExpenseRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseState())
    val state: StateFlow<ExpenseState> = _state.asStateFlow()

    private var currentUserId: Int? = null

    init {
        viewModelScope.launch {
            accountRepository.currentUserId.collect { userId ->
                currentUserId = userId
            }
        }
    }

    fun onExpenseEvent(event: ExpenseEvent) {
        when (event) {
            is ExpenseEvent.addExpense -> viewModelScope.launch {
                _state.value = _state.value.copy(isLoading = true)
                getCurrentUserId()
                currentUserId?.let { userId ->
                    expenseRepository.addExpense(
                        amount = BigDecimal(event.amount),
                        currency = Currency.getInstance(event.currency),
                        creatorId = userId,
                        ownershipType = event.ownershipType,
                        splitType = event.splitType,
                        targetUserId = event.targetUserId?.toIntOrNull(),
                        targetGroupId = event.targetGroupId?.toIntOrNull()
                    ).onFailure { e ->
                        _state.value = _state.value.copy(errorMessage = e.message, isLoading = false)
                    }.onSuccess {
                        onExpenseEvent(ExpenseEvent.showExpenses(userId))
                    }
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
                getCurrentUserId()
                _state.value = _state.value.copy(isLoading = true)
                currentUserId?.let { userId ->
                    if (_state.value.expenses == null) {
                        expenseRepository.getUserExpenses(userId)
                            .collectLatest { expenseList ->
                                _state.value = _state.value.copy(
                                    expenses = expenseList,
                                    isLoading = false
                                )
                            }
                    } else {
                        _state.value = _state.value.copy(isLoading = false)
                    }
                }
                _state.value = _state.value.copy(isLoading = false)
            }

            is ExpenseEvent.editExpense -> viewModelScope.launch {
                getCurrentUserId()
                _state.value = _state.value.copy(isLoading = true)
                currentUserId?.let {
                    expenseRepository.editExpense(
                        amount = BigDecimal(event.amount),
                        currency = Currency.getInstance(event.currency),
                        ownershipType = event.ownershipType,
                        splitType = event.splitType,
                        targetUserId = event.targetUserId?.toIntOrNull(),
                        targetGroupId = event.targetGroupId?.toIntOrNull(),
                        expense = event.expense
                    ).onFailure { e ->
                        _state.value = _state.value.copy(errorMessage = e.message, isLoading = false)
                    }.onSuccess {
                        _state.value = _state.value.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun getCurrentUserId() {
        viewModelScope.launch {
            accountRepository.currentUserId.collect { userId ->
                currentUserId = userId
            }
        }
    }
}